package org.registrator.community.service.impl;


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
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
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
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceDiscreteValue;
import org.registrator.community.entity.ResourceLinearValue;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ResourceServiceImpl implements ResourceService {

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
   
    @Override
    @Transactional
    public ResourceDTO addNewResource(ResourceDTO resourceDTO, ResourceStatus resourceStatus) {
        Resource resourceEntity = new Resource();
        resourceEntity.setIdentifier(resourceDTO.getIdentifier());
        resourceEntity.setDescription(resourceDTO.getDescription());
        resourceEntity.setReasonInclusion(resourceDTO.getReasonInclusion());
        resourceEntity.setTome(tomeRepository.findTomeByIdentifier(resourceDTO.getTomeIdentifier()));
        resourceEntity.setRegistrator(tomeRepository.findTomeByIdentifier(resourceDTO.getTomeIdentifier()).getRegistrator());
        resourceEntity.setStatus(resourceStatus);
        ResourceType resourceType = resourceTypeRepository.findByName(resourceDTO.getResourceType().getTypeName());
        resourceEntity.setType(resourceType);
        resourceEntity.setDate(resourceDTO.getDate());
        resourceEntity = resourceRepository.save(resourceEntity);

//        Setter for resourceDTO???
        resourceDTO.setRegistratorName(resourceEntity.getRegistrator().getFirstName() +
                resourceEntity.getRegistrator().getMiddleName() +
                resourceEntity.getRegistrator().getLastName());

        List<Area> areas = new ArrayList<Area>();
        for (PoligonAreaDTO poligonAreaDTO : resourceDTO.getResourceArea().getPoligons()) {

            Polygon polygonEntity = new Polygon();
            Double minLat = 90.0;
            Double maxLat = -90.0;
            Double minLng = 180.0;
            Double maxLng = -180.0;

            for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
                if(minLat > point.getDecimalLatitude()) {
                    minLat = point.getDecimalLatitude();
                }
                if(maxLat < point.getDecimalLatitude()) {
                    maxLat = point.getDecimalLatitude();
                }
                if(minLng > point.getDecimalLongitude()) {
                    minLng = point.getDecimalLongitude();
                }
                if(maxLng < point.getDecimalLongitude()) {
                    maxLng = point.getDecimalLongitude();
                }
            }

            polygonEntity.setMinLat(minLat);
            polygonEntity.setMaxLat(maxLat);
            polygonEntity.setMinLng(minLng);
            polygonEntity.setMaxLng(maxLng);
            polygonEntity.setResource(resourceEntity);

            polygonEntity = polygonRepository.save(polygonEntity);

            for (PointAreaDTO point : poligonAreaDTO.getPoints()) {
                Area area = new Area();
                area.setPolygon(polygonEntity);
                area.setOrderNumber(point.getOrderNumber());
                area.setLatitude(point.getDecimalLatitude());
                area.setLongitude(point.getDecimalLongitude());
                areas.add(area);
            }
        }
        areaRepository.save(areas);

        List<ResourceLinearValue> resourceLinearValues = new ArrayList<ResourceLinearValue>();
        List<ResourceDiscreteValue> resourceDiscreteValues = new ArrayList<ResourceDiscreteValue>();
        if (!resourceDTO.getResourceLinear().isEmpty()) {
            for (ResourceLinearValueDTO linValueDTO : resourceDTO.getResourceLinear()) {
                LinearParameter linParameter = linearParameterRepository
                        .findByResourceAndName(linValueDTO.getLinearParameterDescription(), resourceType);
                for (SegmentLinearDTO segment : linValueDTO.getSegments()) {
                    ResourceLinearValue linearValue = new ResourceLinearValue();
                    linearValue.setResource(resourceEntity);
                    linearValue.setLinearParameter(linParameter);
                    linearValue.setMinValue(segment.getBegin());
                    linearValue.setMaxValue(segment.getEnd());
                    resourceLinearValues.add(linearValue);
                }
            }
            linearValueRepository.save(resourceLinearValues);
        }

        if (!resourceDTO.getResourceDiscrete().isEmpty()) {
            for (ResourceDiscreteValueDTO discValueDTO : resourceDTO.getResourceDiscrete()) {
                DiscreteParameter discreteParameter = discreteParameterRepository
                        .findByResourceAndName(discValueDTO.getDiscreteParameterDescription(), resourceType);

                for (ValueDiscreteDTO valueDiscrete : discValueDTO.getValueDiscretes()) {
                    ResourceDiscreteValue discreteValue = new ResourceDiscreteValue();
                    discreteValue.setResource(resourceEntity);
                    discreteValue.setDiscreteParameter(discreteParameter);
                    discreteValue.setValue(valueDiscrete.getValue());
                    discreteValue.setComment(valueDiscrete.getComment());
                    resourceDiscreteValues.add(discreteValue);
                }
            }
            discreteValueRepository.save(resourceDiscreteValues);
        }
        return resourceDTO;
    }

    @Override
    public ResourceDTO findByIdentifier(String identifier) {
        Resource resourceEntity = resourceRepository.findByIdentifier(identifier);
        List<LinearParameter> linearParameters = linearParameterRepository.
                findByResourceType(resourceEntity.getType());
        List<DiscreteParameter> discreteParameters = discreteParameterRepository.
                findAllByResourceType(resourceEntity.getType());
        List<ResourceLinearValue> linearValues = linearValueRepository.
                findByResource(resourceEntity);
        List<ResourceDiscreteValue> discreteValues = discreteValueRepository.
                findByResource(resourceEntity);
        List<Polygon> polygons = polygonRepository.findByResource(resourceEntity);
        List<Area> areas = new ArrayList<>();
        for (Polygon polygon : polygons) {
            areas.addAll(areaRepository.findByPolygon(polygon));
        }

        ResourceDTO resourceDTO = new ResourceDTO();
        resourceDTO.setDescription(resourceEntity.getDescription());
        resourceDTO.setDate(resourceEntity.getDate());
        resourceDTO.setIdentifier(resourceEntity.getIdentifier());
        resourceDTO.setReasonInclusion(resourceEntity.getReasonInclusion());
        resourceDTO.setRegistratorName(resourceEntity.getRegistrator().getFirstName() +
                resourceEntity.getRegistrator().getMiddleName() +resourceEntity.getRegistrator().getLastName());
        resourceDTO.setTomeIdentifier(resourceEntity.getTome().getIdentifier());
        resourceDTO.setResourceType(resourceEntity.getType());

        List<ResourceLinearValueDTO> resLinDTOs = new ArrayList<ResourceLinearValueDTO>();
        List<ResourceDiscreteValueDTO> resDiscDTOs = new ArrayList<ResourceDiscreteValueDTO>();
        ResourceAreaDTO resourceArea = new ResourceAreaDTO();
        List<PoligonAreaDTO> poligonsDTO = new ArrayList<PoligonAreaDTO>();

        for(LinearParameter lp :linearParameters) {
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
                if(dv.getDiscreteParameter().getDiscreteParameterId().equals(dp.getDiscreteParameterId())) {
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
//        List<ResourceDTO> resourcesDTO = new ArrayList<>();
        Set<String> identifiers = new HashSet<>();
        List<Polygon> polygons = polygonRepository.findByLimits(minLat, maxLat, minLng, maxLng);
        for (Polygon polygon : polygons) {
            if(resType.equals(polygon.getResource().getType().getTypeName())){
                identifiers.add(polygon.getResource().getIdentifier());
            }
        }
//        for (Resource resource : resources) {
//            resourcesDTO.add(findByIdentifier(resource.getIdentifier()));
//        }
        return identifiers;
    }

    @Override
    public Set<String> getAllByPoint(Double lat, Double lng) {
        Set<String> identifiers = new HashSet<>();
//        Set<Resource> resources = new HashSet<>();
        List<Polygon> polygons = polygonRepository.findByPoint(lat, lng);
        for (Polygon polygon : polygons) {
            identifiers.add(polygon.getResource().getIdentifier());
        }
//        for (Resource resource : resources) {
//            resourcesDTO.add(findByIdentifier(resource.getIdentifier()));
//        }
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
}

