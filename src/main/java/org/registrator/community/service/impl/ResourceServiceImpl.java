package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.transaction.Transactional;

import org.registrator.community.dao.AreaRepository;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.InquiryRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.dao.ResourceLinearValueRepository;
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
import org.registrator.community.entity.Area;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.Inquiry;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.ResourceService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

@Service
public class ResourceServiceImpl implements ResourceService {

    @Autowired
    Logger logger;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    TomeRepository tomeRepository;

    @Autowired
    ResourceTypeRepository resourceTypeRepository;

    @Autowired
    PolygonRepository polygonRepository;

    @Autowired
    AreaRepository areaRepository;

    @Autowired
    ResourceLinearValueRepository linearValueRepository;

    @Autowired
    ResourceDiscreteValueRepository discreteValueRepository;

    @Autowired
    LinearParameterRepository linearParameterRepository;

    @Autowired
    DiscreteParameterRepository discreteParameterRepository;
    
    @Autowired
    UserRepository userRepository;
    @Autowired
	InquiryRepository inquiryRepository;

    /**
     * Method parse the resourceDTO into entity objects and save them into
     * database
     * @param resourceDTO
     * @param resourceStatus
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

        /* save list of area points */
        for (PoligonAreaDTO poligonAreaDTO : resourceDTO.getResourceArea().getPoligons()) {

            Polygon polygonEntity = getPolygonEntity(resourceEntity, poligonAreaDTO);

            polygonEntity = polygonRepository.save(polygonEntity);

            List<Area> areas = parseToAreaList(poligonAreaDTO, polygonEntity);
            areaRepository.save(areas);
        }
       
        /* save list of resource linear values if exist*/ 
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
        
        //save data in the table inquiry_list
        User user = userRepository.findUserByLogin(ownerLogin);
        Inquiry inquiry = new Inquiry("INPUT", resourceDTO.getDate(), user, registrator, resourceEntity);
		inquiryRepository.saveAndFlush(inquiry);

        return findByIdentifier(resourceEntity.getIdentifier());
    }



    /**
     * Find the resource with given identifier and form resourceDTO object
     * @param identifier
     * @return resourseDTO if exist
     */
    @Override
    public ResourceDTO findByIdentifier(String identifier) {

        logger.info("Method findByIdentifier");
        ResourceDTO resourceDTO = new ResourceDTO();
        Resource resourceEntity = resourceRepository.findByIdentifier(identifier);

        if (resourceEntity != null) {
            logger.info("Resource with identifier " + identifier + " was found");

            /*find parameters of resource type*/
            List<LinearParameter> linearParameters = linearParameterRepository
                    .findByResourceType(resourceEntity.getType());
            List<DiscreteParameter> discreteParameters = discreteParameterRepository
                    .findAllByResourceType(resourceEntity.getType());
            
            /*find resource values  of given resource*/
            List<ResourceLinearValue> linearValues = linearValueRepository.findByResource(resourceEntity);
            List<ResourceDiscreteValue> discreteValues = discreteValueRepository.findByResource(resourceEntity);
            List<Polygon> polygons = polygonRepository.findByResource(resourceEntity);
            List<Area> areas = new ArrayList<>();
            for (Polygon polygon : polygons) {
                areas.addAll(areaRepository.findByPolygon(polygon));
            }

            /*fill standard resource fields */
            resourceDTO.setDescription(resourceEntity.getDescription());
            resourceDTO.setDate(resourceEntity.getDate());
            resourceDTO.setIdentifier(resourceEntity.getIdentifier());
            resourceDTO.setReasonInclusion(resourceEntity.getReasonInclusion());
            resourceDTO.setRegistratorName(resourceEntity.getRegistrator().getFirstName() + " "
                    + resourceEntity.getRegistrator().getMiddleName() + " "
                    + resourceEntity.getRegistrator().getLastName());
            resourceDTO.setTomeIdentifier(resourceEntity.getTome().getIdentifier());
            resourceDTO.setResourceType(resourceEntity.getType().getTypeName());

            
            /*fill additional resource fields*/
            List<ResourceLinearValueDTO> resLinDTOs = new ArrayList<ResourceLinearValueDTO>();
            List<ResourceDiscreteValueDTO> resDiscDTOs = new ArrayList<ResourceDiscreteValueDTO>();
            ResourceAreaDTO resourceArea = new ResourceAreaDTO();
            List<PoligonAreaDTO> poligonsDTO = new ArrayList<PoligonAreaDTO>();

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

            PoligonAreaDTO poligon = new PoligonAreaDTO();
            List<PointAreaDTO> pointDTOs = new ArrayList<PointAreaDTO>();
            for (Area area : areas) {
                // TODO write logic for polygon analysis
                PointAreaDTO pointDTO = new PointAreaDTO();
                pointDTO.setOrderNumber(area.getOrderNumber());
                pointDTO.setLatitudeValues(area.getLatitude());
                pointDTO.setLongitudeValues(area.getLongitude());
                pointDTOs.add(pointDTO);
                poligon.setPoints(pointDTOs);
            }
            poligonsDTO.add(poligon);
            resourceArea.setPoligons(poligonsDTO);
            resourceDTO.setResourceArea(resourceArea);
            
            return resourceDTO;

        }
        else {
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
        // List<ResourceDTO> resourcesDTO = new ArrayList<>();
        Set<String> identifiers = new HashSet<>();
        List<Polygon> polygons = polygonRepository.findByLimits(minLat, maxLat, minLng, maxLng);
        for (Polygon polygon : polygons) {
            if ("all".equals(resType)) {
                identifiers.add(polygon.getResource().getIdentifier());
            } else if (resType.equals(polygon.getResource().getType().getTypeName())) {
                identifiers.add(polygon.getResource().getIdentifier());
            }
        }
        // for (Resource resource : resources) {
        // resourcesDTO.add(findByIdentifier(resource.getIdentifier()));
        // }
        return identifiers;
    }

    @Override
    public Set<String> getAllByPoint(Double lat, Double lng) {
        Set<String> identifiers = new HashSet<>();
        // Set<Resource> resources = new HashSet<>();
        List<Polygon> polygons = polygonRepository.findByPoint(lat, lng);
        for (Polygon polygon : polygons) {
            identifiers.add(polygon.getResource().getIdentifier());
        }
        // for (Resource resource : resources) {
        // resourcesDTO.add(findByIdentifier(resource.getIdentifier()));
        // }
        return identifiers;
    }

    @Override
    public List<PolygonJSON> createPolygonJSON(String identifier) {
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
            polygonJSON.setPoints(points);

            polygonsJSON.add(polygonJSON);

        }
        return polygonsJSON;
    }
    
    private List<Area> parseToAreaList(PoligonAreaDTO poligonAreaDTO, Polygon polygonEntity) {
        List<Area> areas = new ArrayList<Area>();
        for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
            Area area = new Area();
            area.setPolygon(polygonEntity);
            area.setOrderNumber(point.getOrderNumber());
            area.setLatitude(point.getDecimalLatitude());
            area.setLongitude(point.getDecimalLongitude());
            areas.add(area);
        }
        return areas;
    }


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

    
}
