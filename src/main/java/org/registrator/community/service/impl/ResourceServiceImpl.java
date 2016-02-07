package org.registrator.community.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.registrator.community.dao.AreaRepository;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.dao.ResourceLinearValueRepository;
import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceDiscreteValueDTO;
import org.registrator.community.dto.ResourceLinearValueDTO;
import org.registrator.community.dto.SegmentLinearDTO;
import org.registrator.community.dto.ValueDiscreteDTO;
import org.registrator.community.dto.JSON.PointJSON;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.dto.JSON.ResourseSearchJson;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.InquiryService;
import org.registrator.community.service.ResourceDiscreteValueService;
import org.registrator.community.service.ResourceLinearValueService;
import org.registrator.community.service.ResourceService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    private Logger logger;

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private TomeRepository tomeRepository;

    @Autowired
    private ResourceTypeRepository resourceTypeRepository;

    @Autowired
    private PolygonRepository polygonRepository;

    @Autowired
    private AreaRepository areaRepository;

    @Autowired
    private ResourceLinearValueRepository linearValueRepository;

    @Autowired
    private ResourceDiscreteValueRepository discreteValueRepository;

    @Autowired
    private LinearParameterRepository linearParameterRepository;

    @Autowired
    private DiscreteParameterRepository discreteParameterRepository;

    @Autowired
    private ResourceDiscreteValueService resourceDiscreteValueService;

    @Autowired
    private ResourceLinearValueService resourceLinearValueService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private InquiryService inquiryService;

    @Autowired
    private ResourceNumberRepository resourceNumberRepository;

    /**
     * Method parse the resourceDTO into entity objects and save them into
     * database
     * 
     * @param resourceDTO
     * @param registrator
     * @return
     */
    @Override
    @Transactional
    public ResourceDTO addNewResource(ResourceDTO resourceDTO, String ownerLogin, User registrator) {
        logger.info("Method addNewResource");
        logger.info("Owner Login -" + ownerLogin);
        logger.info("Registrator Login -" + registrator.getLogin());

        /* form the resource entity and save in into database */
        Resource resourceEntity = parseToResourseEntity(resourceDTO, registrator);
        resourceEntity = resourceRepository.save(resourceEntity);

        /* sava all additional parameters of given resource */
        saveResourceParameters(resourceDTO, resourceEntity);

        // save data in the table inquiry_list
        if (!ownerLogin.isEmpty()) {
            inquiryService.addInputInquiry(ownerLogin, resourceEntity, registrator);
        }

        /*
         * increment registration number of resource for authenticated registrar
         */
        incrementRegistrationNumber(registrator.getLogin());
        return findByIdentifier(resourceEntity.getIdentifier());
    }

    /**
     * Find the resource with given identifier and form resourceDTO object
     * 
     * @param identifier
     * @return resourseDTO if exist
     */
    @Override
    public ResourceDTO findByIdentifier(String identifier) {

        logger.info("Method findByIdentifier");
        Resource resourceEntity = resourceRepository.findByIdentifier(identifier);

        if (resourceEntity != null) {
            logger.info("Resource with identifier " + identifier + " was found");

            ResourceDTO resourceDTO = formResourceDTO(resourceEntity);

            return resourceDTO;

        } else {
            logger.info("Resource with identifier " + identifier + " was not found in database");
            return null;
        }

    }

    @Override
    public List<Resource> findByType(ResourceType type) {
        return resourceRepository.findByType(type);
    }

    @Override
    public long count() {
        return resourceRepository.count();
    }

    @Override
    public Set<String> getDescriptionBySearchTag(String searchTag) {
        return resourceRepository.findDescriptionsLikeProposed(searchTag);
    }

    @Override
    public Set<String> getAllByAreaLimits(Double minLat, Double maxLat, Double minLng, Double maxLng, String resType) {
        Set<String> identifiers = new HashSet<>();
        List<Polygon> polygons = polygonRepository.findByLimits(minLat, maxLat, minLng, maxLng);
        for (Polygon polygon : polygons) {
            if ("all".equals(resType)) {
                identifiers.add(polygon.getResource().getIdentifier());
            } else if (resType.equals(polygon.getResource().getType().getTypeName())) {
                identifiers.add(polygon.getResource().getIdentifier());
            }
        }
        return identifiers;
    }

    @Override
    public Set<String> getAllByPoint(Double lat, Double lng) {
        Set<String> identifiers = new HashSet<>();
        List<Polygon> polygons = polygonRepository.findByPoint(lat, lng);
        for (Polygon polygon : polygons) {
            identifiers.add(polygon.getResource().getIdentifier());
        }
        return identifiers;
    }

    @Override
    public Set<String> getAllByParameters(ResourseSearchJson parameters) {
        Set<String> identifiers = new HashSet<>();
        Set<String> identifiersDiscrete = new HashSet<>();
        Set<String> identifiersLinear = new HashSet<>();

        if (parameters.getDiscreteParamsIds().size() > 0) {
            identifiersDiscrete
                    .addAll(resourceDiscreteValueService.findResourcesByParamsList(parameters.getDiscreteParamsIds(),
                            parameters.getDiscreteParamsCompares(), parameters.getDiscreteParamsValues()));
            if (identifiers.size() > 0) {
                identifiers.retainAll(identifiersDiscrete);
            } else {
                identifiers.addAll(identifiersDiscrete);
            }
        }
        if (parameters.getLinearParamsIds().size() > 0) {
            identifiersLinear.addAll(resourceLinearValueService
                    .findResourcesByLinParamList(parameters.getLinearParamsIds(), parameters.getLinearParamsValues()));
            if (identifiers.size() > 0) {
                identifiers.retainAll(identifiersLinear);
            } else {
                identifiers.addAll(identifiersLinear);
            }
        }

        return identifiers;
    }

    @Override
    public List<PolygonJSON> createPolygonJSON(String identifier, int i) {
        List<PolygonJSON> polygonsJSON = new ArrayList<>();

        Resource resource = resourceRepository.findByIdentifier(identifier);
        List<Polygon> polygons = polygonRepository.findByResource(resource);

        for (Polygon polygon : polygons) {
            PolygonJSON polygonJSON = new PolygonJSON();
            List<PointJSON> points = new ArrayList<>();
            List<Area> areas = areaRepository.findByPolygon(polygon);

            for (Area area : areas) {
                PointJSON point = new PointJSON();
                point.setLatitude(area.getLatitude());
                point.setLongitude(area.getLongitude());
                point.setPoint_order(area.getOrderNumber());
                points.add(point);
            }

            polygonJSON.setResourceDescription(resource.getDescription());
            polygonJSON.setIdentifier(resource.getIdentifier());
            polygonJSON.setResourceType(resource.getType().getTypeName());
            polygonJSON.setDate(new SimpleDateFormat("dd.MM.yyyy").format(resource.getDate()));
            polygonJSON.setDT_RowId("row" + i);
            polygonJSON.setPoints(points);

            polygonsJSON.add(polygonJSON);
            i++;
        }
        return polygonsJSON;
    }

    @Override
    public String getRegistrationNumber(String login) {
        final int MAXIMAL_NUMBER_LENGTH = 4;
        User user = userRepository.findUserByLogin(login);
        ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);
        if (resourceNumber != null) {
            int numberOfDigits = resourceNumber.getNumber().toString().length();
            StringBuilder resourceNumberPattern = new StringBuilder();
            for (int i = numberOfDigits; i < MAXIMAL_NUMBER_LENGTH; i++) {
                resourceNumberPattern.append("0");
            }
            resourceNumberPattern.append(resourceNumber.getNumber());

            return resourceNumber.getRegistratorNumber() + resourceNumberPattern.toString();
        }

        return null;
    }

    /**
     * Method create the Resource object from given resourceDTO
     * 
     * @param resourceDTO
     * @param registrator
     * @return
     */

    private Resource parseToResourseEntity(ResourceDTO resourceDTO, User registrator) {
        Resource resourceEntity = new Resource();
        resourceEntity.setIdentifier(resourceDTO.getIdentifier());
        resourceEntity.setDescription(resourceDTO.getDescription());
        resourceEntity.setReasonInclusion(resourceDTO.getReasonInclusion());
        resourceEntity.setTome(tomeRepository.findTomeByRegistrator(registrator));
        resourceEntity.setRegistrator(registrator);
        resourceEntity.setStatus(ResourceStatus.ACTIVE);
        ResourceType resourceType = resourceTypeRepository.findByName(resourceDTO.getResourceType());
        resourceEntity.setType(resourceType);
        resourceEntity.setDate(resourceDTO.getDate());
        return resourceEntity;
    }

    /**
     * Save additional parameters of resource entity in database
     * 
     * @param resourceDTO
     * @param resourceEntity
     */
    private void saveResourceParameters(ResourceDTO resourceDTO, Resource resourceEntity) {
        /* save list of area points */
        for (PoligonAreaDTO poligonAreaDTO : resourceDTO.getResourceArea().getPoligons()) {

            Polygon polygonEntity = getPolygonEntity(resourceEntity, poligonAreaDTO);
            polygonEntity = polygonRepository.save(polygonEntity);
            List<Area> areas = parseToAreaList(poligonAreaDTO, polygonEntity);
            areaRepository.save(areas);
        }

        /* save list of resource linear values if exist */
        if (!resourceDTO.getResourceLinear().isEmpty()) {
            logger.info("save linear values of resource");
            List<ResourceLinearValue> resourceLinearValues = parseToLinearValueList(resourceDTO, resourceEntity);
            linearValueRepository.save(resourceLinearValues);
        }

        /* save list of resource discrete values if exist */
        if (!resourceDTO.getResourceDiscrete().isEmpty()) {
            logger.info("save discrete values of resource");
            List<ResourceDiscreteValue> resourceDiscreteValues = parseToDiscreteValueList(resourceDTO, resourceEntity);
            discreteValueRepository.save(resourceDiscreteValues);
        }
    }

    /**
     * Create Polygon object from poligonAreaDTO
     * 
     * @param resourceEntity
     * @param poligonAreaDTO
     * @return Polygon
     */
    private Polygon getPolygonEntity(Resource resourceEntity, PoligonAreaDTO poligonAreaDTO) {
        Polygon polygonEntity = new Polygon();
        Double minLat = 90.0;
        Double maxLat = -90.0;
        Double minLng = 180.0;
        Double maxLng = -180.0;

        for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
            if (minLat > point.getDecimalLatitude()) {
                minLat = point.getDecimalLatitude();
            }
            if (maxLat < point.getDecimalLatitude()) {
                maxLat = point.getDecimalLatitude();
            }
            if (minLng > point.getDecimalLongitude()) {
                minLng = point.getDecimalLongitude();
            }
            if (maxLng < point.getDecimalLongitude()) {
                maxLng = point.getDecimalLongitude();
            }
        }

        polygonEntity.setMinLat(minLat);
        polygonEntity.setMaxLat(maxLat);
        polygonEntity.setMinLng(minLng);
        polygonEntity.setMaxLng(maxLng);
        polygonEntity.setResource(resourceEntity);

        return polygonEntity;
    }

    /**
     * Create list of Area objects from poligonAreaDTO
     * 
     * @param poligonAreaDTO
     * @param polygonEntity
     * @return List<Area>
     */

    private List<Area> parseToAreaList(PoligonAreaDTO poligonAreaDTO, Polygon polygonEntity) {
        List<Area> areas = new ArrayList<Area>();
        for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
            if (point.getOrderNumber() != 0) {
                Area area = new Area();
                area.setPolygon(polygonEntity);
                area.setOrderNumber(point.getOrderNumber());
                area.setLatitude(point.getDecimalLatitude());
                area.setLongitude(point.getDecimalLongitude());
                areas.add(area);
            }
        }
        return areas;
    }

    /**
     * Create list of ResourceDiscreteValue objects given in resourceDTO
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return List<ResourceDiscreteValue>
     */
    private List<ResourceDiscreteValue> parseToDiscreteValueList(ResourceDTO resourceDTO, Resource resourceEntity) {
        List<ResourceDiscreteValue> resourceDiscreteValues = new ArrayList<ResourceDiscreteValue>();
        for (ResourceDiscreteValueDTO discValueDTO : resourceDTO.getResourceDiscrete()) {
            DiscreteParameter discreteParameter = discreteParameterRepository
                    .findByResourceAndName(discValueDTO.getDiscreteParameterDescription(), resourceEntity.getType());
            for (ValueDiscreteDTO valueDiscrete : discValueDTO.getValueDiscretes()) {
                ResourceDiscreteValue discreteValue = new ResourceDiscreteValue();
                discreteValue.setResource(resourceEntity);
                discreteValue.setDiscreteParameter(discreteParameter);
                discreteValue.setValue(valueDiscrete.getValue());
                discreteValue.setComment(valueDiscrete.getComment());
                resourceDiscreteValues.add(discreteValue);
            }
        }
        return resourceDiscreteValues;
    }

    /**
     * Create list of ResourceLinearValue objects given in resourceDTO
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return List<ResourceLinearValue>
     */
    private List<ResourceLinearValue> parseToLinearValueList(ResourceDTO resourceDTO, Resource resourceEntity) {
        List<ResourceLinearValue> resourceLinearValues = new ArrayList<ResourceLinearValue>();
        for (ResourceLinearValueDTO linValueDTO : resourceDTO.getResourceLinear()) {
            LinearParameter linParameter = linearParameterRepository
                    .findByResourceAndName(linValueDTO.getLinearParameterDescription(), resourceEntity.getType());
            for (SegmentLinearDTO segment : linValueDTO.getSegments()) {
                ResourceLinearValue linearValue = new ResourceLinearValue();
                linearValue.setResource(resourceEntity);
                linearValue.setLinearParameter(linParameter);
                linearValue.setMinValue(segment.getBegin());
                linearValue.setMaxValue(segment.getEnd());
                resourceLinearValues.add(linearValue);
            }
        }
        return resourceLinearValues;
    }

    /**
     * Method return ResourseDTO object with all fields based on resourceEntity
     * 
     * @param resourceEntity
     * @return resourceDTO
     */
    private ResourceDTO formResourceDTO(Resource resourceEntity) {
        /* fill standard resource fields */
        ResourceDTO resourceDTO = fillBasicParametersResourceDTO(resourceEntity);

        /* fill linear values of resource */
        resourceDTO = fillLinearValuesDTO(resourceDTO, resourceEntity);

        /* fill discrete values of resource */
        resourceDTO = fillDiscreteValuesDTO(resourceDTO, resourceEntity);

        /* fill area values of resource */
        resourceDTO = fillAreaDTO(resourceDTO, resourceEntity);
        return resourceDTO;
    }

    /**
     * Method create ResourceDTO with basics parameters from resoureEntity
     * 
     * @param resourceEntity
     * @return ResourceDTO
     */
    private ResourceDTO fillBasicParametersResourceDTO(Resource resourceEntity) {
        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setDescription(resourceEntity.getDescription());
        resourceDTO.setDate(resourceEntity.getDate());
        resourceDTO.setIdentifier(resourceEntity.getIdentifier());
        resourceDTO.setReasonInclusion(resourceEntity.getReasonInclusion());
        resourceDTO.setRegistratorName(
                resourceEntity.getRegistrator().getFirstName() + " " + resourceEntity.getRegistrator().getMiddleName()
                        + " " + resourceEntity.getRegistrator().getLastName());
        resourceDTO.setTomeIdentifier(resourceEntity.getTome().getIdentifier());
        resourceDTO.setResourceType(resourceEntity.getType().getTypeName());
        return resourceDTO;
    }

    /**
     * Fill LinearValues list in ResourceDTO object
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return ResourceDTO
     */
    private ResourceDTO fillLinearValuesDTO(ResourceDTO resourceDTO, Resource resourceEntity) {
        /* find linear parameters of resource type */
        List<LinearParameter> linearParameters = linearParameterRepository.findByResourceType(resourceEntity.getType());
        /* find linear values of given resource */
        List<ResourceLinearValue> linearValues = linearValueRepository.findByResource(resourceEntity);
        List<ResourceLinearValueDTO> resLinDTOs = new ArrayList<ResourceLinearValueDTO>();
        for (LinearParameter lp : linearParameters) {
            ResourceLinearValueDTO resLinDTO = new ResourceLinearValueDTO();
            resLinDTO.setLinearParameterDescription(lp.getDescription());
            resLinDTO.setLinearParameterUnit(lp.getUnitName());
            List<SegmentLinearDTO> segments = new ArrayList<SegmentLinearDTO>();
            for (ResourceLinearValue linearValue : linearValues) {
                if (linearValue.getLinearParameter().getDescription().equals(lp.getDescription())) {
                    SegmentLinearDTO segment = new SegmentLinearDTO();
                    segment.setBegin(linearValue.getMinValue());
                    segment.setEnd(linearValue.getMaxValue());
                    segments.add(segment);
                }
            }
            resLinDTO.setSegments(segments);
            resLinDTOs.add(resLinDTO);
        }
        resourceDTO.setResourceLinear(resLinDTOs);
        return resourceDTO;
    }

    /**
     * Fill DiscreteValues list in ResourceDTO object
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return ResourceDTO
     */
    private ResourceDTO fillDiscreteValuesDTO(ResourceDTO resourceDTO, Resource resourceEntity) {
        /* find discrete parameters of resource type */
        List<DiscreteParameter> discreteParameters = discreteParameterRepository
                .findAllByResourceType(resourceEntity.getType());
        /* find discrete values of given resource */
        List<ResourceDiscreteValue> discreteValues = discreteValueRepository.findByResource(resourceEntity);
        List<ResourceDiscreteValueDTO> resDiscDTOs = new ArrayList<ResourceDiscreteValueDTO>();
        for (DiscreteParameter dp : discreteParameters) {
            ResourceDiscreteValueDTO resDisDTO = new ResourceDiscreteValueDTO();
            resDisDTO.setDiscreteParameterDescription(dp.getDescription());
            resDisDTO.setDiscreteParameterUnit(dp.getUnitName());

            List<ValueDiscreteDTO> valuediscretes = new ArrayList<>();
            for (ResourceDiscreteValue dv : discreteValues) {
                if (dv.getDiscreteParameter().getDiscreteParameterId().equals(dp.getDiscreteParameterId())) {
                    ValueDiscreteDTO valueDiscrete = new ValueDiscreteDTO();
                    valueDiscrete.setValue(dv.getValue());
                    valueDiscrete.setComment(dv.getComment());
                    valuediscretes.add(valueDiscrete);
                }

            }
            resDisDTO.setValueDiscretes(valuediscretes);
            resDiscDTOs.add(resDisDTO);
        }
        resourceDTO.setResourceDiscrete(resDiscDTOs);
        return resourceDTO;
    }

    /**
     * Fill Area in ResourceDTO object
     * 
     * @param resourceDTO
     * @param resourceEntity
     * @return ResourceDTO
     */
    private ResourceDTO fillAreaDTO(ResourceDTO resourceDTO, Resource resourceEntity) {
        List<Polygon> polygons = polygonRepository.findByResource(resourceEntity);
        /* fill additional resource fields */
        ResourceAreaDTO resourceArea = new ResourceAreaDTO();
        List<PoligonAreaDTO> poligonsDTO = new ArrayList<PoligonAreaDTO>();

        for (Polygon polygon : polygons) {
            List<Area> areas = new ArrayList<>();
            areas.addAll(areaRepository.findByPolygon(polygon));

            PoligonAreaDTO poligon = new PoligonAreaDTO();
            List<PointAreaDTO> pointDTOs = new ArrayList<PointAreaDTO>();

            for (Area area : areas) {
                PointAreaDTO pointDTO = new PointAreaDTO();
                pointDTO.setOrderNumber(area.getOrderNumber());
                pointDTO.setLatitudeValues(area.getLatitude());
                pointDTO.setLongitudeValues(area.getLongitude());
                pointDTOs.add(pointDTO);
            }
            poligon.setPoints(pointDTOs);
            poligonsDTO.add(poligon);
        }
        resourceArea.setPoligons(poligonsDTO);
        resourceDTO.setResourceArea(resourceArea);
        return resourceDTO;
    }

    /**
     * Method increment registration number of resource for registrator which
     * add new resource
     * 
     * @param login
     *            - login of registrator authorized
     */
    private void incrementRegistrationNumber(String login) {
        User user = userRepository.findUserByLogin(login);
        ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);
        Integer incrementedNumber = resourceNumber.getNumber() + 1;
        resourceNumber.setNumber(incrementedNumber);
        resourceNumberRepository.save(resourceNumber);
    }

}
