package org.registrator.community.service.implementation;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Restrictions;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.DiscreteParameterDTO;
import org.registrator.community.dto.LinearParameterDTO;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceDiscreteDTO;
import org.registrator.community.dto.ResourceLinearDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.SegmentLinearDTO;
import org.registrator.community.entity.Area;
import org.registrator.community.entity.DiscreteValue;
import org.registrator.community.entity.LineSize;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.StoreOfDiscreteValues;
import org.registrator.community.entity.StoreOfLineSizes;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.service.interfaces.RegistratorService;

public class RegistratorServiceImpl implements RegistratorService {

	@Override
	public void addResourseType(ResourceTypeDTO resourceTypeDTO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		ResourceType resourceEntity = new ResourceType();
		resourceEntity.setTypeName(resourceTypeDTO.getTypeName());
		DaoFactory.get().getResourceTypeDao().add(resourceEntity);

		List<LinearParameterDTO> linearParameters = resourceTypeDTO
				.getLinearParameters();
		List<DiscreteParameterDTO> discreteParameters = resourceTypeDTO
				.getDiscreteParameters();

		for (int i = 0; i < linearParameters.size(); i++) {
			LineSize lineSizeEntyty = new LineSize();
			LinearParameterDTO linearParameterDTO = linearParameters.get(i);
			lineSizeEntyty.setResourceType(resourceEntity);
			lineSizeEntyty.setDescription(linearParameterDTO.getDescription());
			lineSizeEntyty.setUnitName(linearParameterDTO.getUnitName());
			DaoFactory.get().getLineSizeDao().add(lineSizeEntyty);
		}

		for (int i = 0; i < discreteParameters.size(); i++) {
			DiscreteValue discreteValueEntity = new DiscreteValue();
			DiscreteParameterDTO discreteParameterDTO = discreteParameters
					.get(i);
			discreteValueEntity.setResourceType(resourceEntity);
			discreteValueEntity.setDescription(discreteParameterDTO
					.getDescription());
			discreteValueEntity.setUnitName(discreteParameterDTO.getUnitName());
			DaoFactory.get().getDiscreteValueDao().add(discreteValueEntity);
		}

		/*
		 * for (String linearParameterDTO : linearParameters) { LineSize
		 * lineSizeEntyty = new LineSize();
		 * lineSizeEntyty.setResourceType(resourceEntity);
		 * lineSizeEntyty.setDescription(linearParameterDTO.getDescription());
		 * DaoFactory.get().getLineSizeDao().add(lineSizeEntyty);
		 * 
		 * }
		 */

		transaction.commit();
		session.close();

	}

	@Override
	public void addResource(ResourceDTO resourceDTO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		// Ann addResourceNoTransaction(resourceDTO);

		// list for table area
		List<PoligonAreaDTO> poligonAreaDTOs = resourceDTO.getResourceArea()
				.getPoligons();

		// list for table store of line sizes
		List<ResourceLinearDTO> resourceLinearDTOs = resourceDTO
				.getResourceLinear();

		// list for table store of discrete values
		List<ResourceDiscreteDTO> resourceDiscreteDTOs = resourceDTO
				.getResourceDiscrete();

		// filling table list_of_resources
		ResourceType resourceType = (ResourceType) session
				.createCriteria(ResourceType.class)
				.add(Restrictions.eq("typeName", resourceDTO.getResourceType()
						.getTypeName())).uniqueResult();

		User registrator = (User) session
				.createCriteria(User.class)
				.add(Restrictions.eq("firstName",
						resourceDTO.getRegistratorName())).uniqueResult();

		Tome tome = (Tome) session
				.createCriteria(Tome.class)
				.add(Restrictions.eq("identifier",
						resourceDTO.getTomeIdentifier())).uniqueResult();

		Resource resourceEntity = new Resource(resourceType,
				resourceDTO.getIdentifier(), resourceDTO.getDescription(),
				registrator, resourceDTO.getDate(), resourceDTO.getStatus()
						.toString(), tome, resourceDTO.getReasonInclusion());

		DaoFactory.get().getResourceDao().add(resourceEntity);

		// filling table area
		for (int i = 0; i < poligonAreaDTOs.size(); i++) {
			PoligonAreaDTO poligonAreaDTO = poligonAreaDTOs.get(i);
			List<PointAreaDTO> pointAreaDTOs = poligonAreaDTO.getPoints();
			for (int j = 0; j < pointAreaDTOs.size(); j++) {
				Area area = new Area();
				PointAreaDTO point = pointAreaDTOs.get(j);
				Double latitude = point.getLatitudeDegrees()
						+ point.getLatitudeMinutes() / 60d
						+ point.getLatitudeSeconds() / 3600d;
				Double longitude = point.getLongitudeDegrees()
						+ point.getLongitudeMinutes() / 60d
						+ point.getLongitudeSeconds() / 3600d;
				area.setResource(resourceEntity);
				area.setNumberOfPoint(point.getOrderNumber());
				area.setLatitude(latitude);
				area.setLongitude(longitude);
				DaoFactory.get().getAreaDao().add(area);
			}
		}

		// filling table store of line sizes
		for (int i = 0; i < resourceLinearDTOs.size(); i++) {
			ResourceLinearDTO resourceLinearDTO = resourceLinearDTOs.get(i);
			LinearParameterDTO linearParameterDTO = resourceLinearDTO
					.getLinearParameterDTO();

			Criteria cr = session.createCriteria(LineSize.class);
			Criterion resource = Restrictions.eq("resourceType", resourceType);
			Criterion name = Restrictions.eq("description",
					linearParameterDTO.getDescription());
			LogicalExpression andExp = Restrictions.and(resource, name);
			cr.add(andExp);
			LineSize lineSize = (LineSize) cr.uniqueResult();
			List<SegmentLinearDTO> segments = resourceLinearDTO.getSegments();

			for (int j = 0; j < segments.size(); j++) {
				StoreOfLineSizes storeOfLineSizes = new StoreOfLineSizes();
				SegmentLinearDTO segment = segments.get(j);
				storeOfLineSizes.setResource(resourceEntity);
				storeOfLineSizes.setLineSize(lineSize);
				storeOfLineSizes.setMinValue(segment.getBegin());
				storeOfLineSizes.setMaxValue(segment.getEnd());
				DaoFactory.get().getStoreOfLineSizesDao().add(storeOfLineSizes);
			}
		}

		// filling table store of discrete values
		for (int i = 0; i < resourceDiscreteDTOs.size(); i++) {
			ResourceDiscreteDTO resourceDiscreteDTO = resourceDiscreteDTOs
					.get(i);
			DiscreteParameterDTO discreteParameterDTO = resourceDiscreteDTO
					.getDiscreteParameterDTO();

			Criteria cr = session.createCriteria(DiscreteValue.class);
			Criterion resource = Restrictions.eq("resourceType", resourceType);
			Criterion name = Restrictions.eq("description",
					discreteParameterDTO.getDescription());
			LogicalExpression andExp = Restrictions.and(resource, name);
			cr.add(andExp);
			DiscreteValue discreteValue = (DiscreteValue) cr.uniqueResult();

			List<Double> values = resourceDiscreteDTO.getValues();
			for (int j = 0; j < values.size(); j++) {
				StoreOfDiscreteValues storeOfDiscreteValues = new StoreOfDiscreteValues();
				storeOfDiscreteValues.setResource(resourceEntity);
				storeOfDiscreteValues.setDiscreteValue(discreteValue);
				storeOfDiscreteValues.setValue(values.get(j));
				DaoFactory.get().getStoreOfDiscreteValuesDao()
						.add(storeOfDiscreteValues);
			}
		}

		transaction.commit();
		session.close();
	}

	public Resource addResourceNoTransaction(ResourceDTO resourceDTO) {
		Session session = HibernateUtil.getSessionFactory().getCurrentSession();
		// list for table area
		List<PoligonAreaDTO> poligonAreaDTOs = resourceDTO.getResourceArea()
				.getPoligons();

		// list for table store of line sizes
		List<ResourceLinearDTO> resourceLinearDTOs = resourceDTO
				.getResourceLinear();

		// list for table store of discrete values
		List<ResourceDiscreteDTO> resourceDiscreteDTOs = resourceDTO
				.getResourceDiscrete();

		// filling table list_of_resources
		ResourceType resourceType = (ResourceType) session
				.createCriteria(ResourceType.class)
				.add(Restrictions.eq("typeName", resourceDTO.getResourceType()
						.getTypeName())).uniqueResult();

		User registrator = (User) session
				.createCriteria(User.class)
				.add(Restrictions.eq("firstName",
						resourceDTO.getRegistratorName())).uniqueResult();

		Tome tome = (Tome) session
				.createCriteria(Tome.class)
				.add(Restrictions.eq("identifier",
						resourceDTO.getTomeIdentifier())).uniqueResult();

		Resource resourceEntity = new Resource(resourceType,
				resourceDTO.getIdentifier(), resourceDTO.getDescription(),
				registrator, resourceDTO.getDate(), resourceDTO.getStatus()
						.toString(), tome, resourceDTO.getReasonInclusion());

		DaoFactory.get().getResourceDao().add(resourceEntity);

		// filling table area
		for (int i = 0; i < poligonAreaDTOs.size(); i++) {
			PoligonAreaDTO poligonAreaDTO = poligonAreaDTOs.get(i);
			List<PointAreaDTO> pointAreaDTOs = poligonAreaDTO.getPoints();
			for (int j = 0; j < pointAreaDTOs.size(); j++) {
				Area area = new Area();
				PointAreaDTO point = pointAreaDTOs.get(j);
				Double latitude = point.getLatitudeDegrees()
						+ point.getLatitudeMinutes() / 60d
						+ point.getLatitudeSeconds() / 3600d;
				Double longitude = point.getLongitudeDegrees()
						+ point.getLongitudeMinutes() / 60d
						+ point.getLongitudeSeconds() / 3600d;
				area.setResource(resourceEntity);
				area.setNumberOfPoint(point.getOrderNumber());
				area.setLatitude(latitude);
				area.setLongitude(longitude);
				DaoFactory.get().getAreaDao().add(area);
			}
		}

		// filling table store of line sizes
		for (int i = 0; i < resourceLinearDTOs.size(); i++) {
			ResourceLinearDTO resourceLinearDTO = resourceLinearDTOs.get(i);
			LinearParameterDTO linearParameterDTO = resourceLinearDTO
					.getLinearParameterDTO();

			Criteria cr = session.createCriteria(LineSize.class);
			Criterion resource = Restrictions.eq("resourceType", resourceType);
			Criterion name = Restrictions.eq("description",
					linearParameterDTO.getDescription());
			LogicalExpression andExp = Restrictions.and(resource, name);
			cr.add(andExp);
			LineSize lineSize = (LineSize) cr.uniqueResult();
			List<SegmentLinearDTO> segments = resourceLinearDTO.getSegments();

			for (int j = 0; j < segments.size(); j++) {
				StoreOfLineSizes storeOfLineSizes = new StoreOfLineSizes();
				SegmentLinearDTO segment = segments.get(j);
				storeOfLineSizes.setResource(resourceEntity);
				storeOfLineSizes.setLineSize(lineSize);
				storeOfLineSizes.setMinValue(segment.getBegin());
				storeOfLineSizes.setMaxValue(segment.getEnd());
				DaoFactory.get().getStoreOfLineSizesDao().add(storeOfLineSizes);
			}
		}

		// filling table store of discrete values
		for (int i = 0; i < resourceDiscreteDTOs.size(); i++) {
			ResourceDiscreteDTO resourceDiscreteDTO = resourceDiscreteDTOs
					.get(i);
			DiscreteParameterDTO discreteParameterDTO = resourceDiscreteDTO
					.getDiscreteParameterDTO();

			Criteria cr = session.createCriteria(DiscreteValue.class);
			Criterion resource = Restrictions.eq("resourceType", resourceType);
			Criterion name = Restrictions.eq("description",
					discreteParameterDTO.getDescription());
			LogicalExpression andExp = Restrictions.and(resource, name);
			cr.add(andExp);
			DiscreteValue discreteValue = (DiscreteValue) cr.uniqueResult();

			List<Double> values = resourceDiscreteDTO.getValues();
			for (int j = 0; j < values.size(); j++) {
				StoreOfDiscreteValues storeOfDiscreteValues = new StoreOfDiscreteValues();
				storeOfDiscreteValues.setResource(resourceEntity);
				storeOfDiscreteValues.setDiscreteValue(discreteValue);
				storeOfDiscreteValues.setValue(values.get(j));
				DaoFactory.get().getStoreOfDiscreteValuesDao()
						.add(storeOfDiscreteValues);
			}
		}
		return resourceEntity;
	}

	//
	@Override
	public List<ResourceTypeDTO> showAllTypeOfResources() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		List<ResourceTypeDTO> resourceTypeDTO = new ArrayList<ResourceTypeDTO>();

		List<ResourceType> resourceType = DaoFactory.get().getResourceTypeDao()
				.getAll();

		List<LineSize> lineSizeList = DaoFactory.get().getLineSizeDao()
				.getAll();
		List<LinearParameterDTO> linearParameterDTOList = new ArrayList<LinearParameterDTO>();
		List<DiscreteParameterDTO> discreteParameterDTOList = new ArrayList<DiscreteParameterDTO>();
		List<DiscreteValue> discreteValueList = DaoFactory.get()
				.getDiscreteValueDao().getAll();

		// Add linear parameters to DTO class
		for (LineSize ls : lineSizeList) {
			LinearParameterDTO lpDTO = new LinearParameterDTO();
			lpDTO.setDescription(ls.getDescription());
			lpDTO.setUnitName(ls.getUnitName());
			linearParameterDTOList.add(lpDTO);
		}

		// Add discrete parameters to DTO class
		for (DiscreteValue dv : discreteValueList) {
			DiscreteParameterDTO dpDTO = new DiscreteParameterDTO();
			dpDTO.setDescription(dv.getDescription());
			dpDTO.setUnitName(dv.getUnitName());
			discreteParameterDTOList.add(dpDTO);
		}
		// Add ResourceType to DTO class

		
		//List<ResourceType> resourceType = DaoFactory.get().getResourceTypeDao().getAll();
		

		for (ResourceType rt : resourceType) {
			//List<LineSize> lineSizeList = DaoFactory.get().getLineSizeDao().getAll();
			
			List<LinearParameterDTO> linearParameterDTOList1 = new ArrayList<LinearParameterDTO>();
			List<DiscreteParameterDTO> discreteParameterDTOList1 = new ArrayList<DiscreteParameterDTO>();
			
			 		
			// Add linear parameters to DTO class
			for (LineSize ls: lineSizeList) {
				LinearParameterDTO lpDTO = new LinearParameterDTO();
				lpDTO.setDescription(ls.getDescription());
				lpDTO.setUnitName(ls.getUnitName());
				linearParameterDTOList.add(lpDTO);
			}
								
			
			// Add discrete parameters to DTO class
			for (DiscreteValue dv: discreteValueList) {
				DiscreteParameterDTO dpDTO = new DiscreteParameterDTO();
				dpDTO.setDescription(dv.getDescription());
				dpDTO.setUnitName(dv.getUnitName());
				discreteParameterDTOList.add(dpDTO);
			}
			
			// Add ResourceType to DTO class
			ResourceTypeDTO rtDTO = new ResourceTypeDTO();
			rtDTO.setDiscreteParameters(discreteParameterDTOList);
			rtDTO.setLinearParameters(linearParameterDTOList);
			rtDTO.setTypeName(rt.getTypeName());
			resourceTypeDTO.add(rtDTO);
		}
		return resourceTypeDTO;
	}

	//
	@Override
	public ResourceDTO showResourceByIdentifier(String identifier){
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		// getting resource from database with identifier
		Resource resource = DaoFactory.get().getResourceDao().findByIdentifier(identifier);
		
		ResourceAreaDTO areaDTO = new ResourceAreaDTO();
		List<ResourceLinearDTO> resLinDTOs = new ArrayList<ResourceLinearDTO>();
		List<ResourceDiscreteDTO> resDiscDTOs = new ArrayList<ResourceDiscreteDTO>();
		
		List<LineSize> lineSizeList = (List<LineSize>) session.createCriteria(LineSize.class).
				add(Restrictions.eq("resourceType", resource.getType())).list();
			
        for (LineSize lineSize : lineSizeList) {
        	Criteria cr = session.createCriteria(StoreOfLineSizes.class);
    		Criterion res = Restrictions.eq("resource", resource);
    		Criterion linesizeParam = Restrictions.eq("lineSize", lineSize);
    		LogicalExpression andExp = Restrictions.and(res, linesizeParam);
    		cr.add( andExp );
    		List<StoreOfLineSizes> resourcelineParameters = (List<StoreOfLineSizes>)cr.list();
    		List<SegmentLinearDTO> segments = new ArrayList<SegmentLinearDTO>();
    		for (StoreOfLineSizes resourcelineParameter : resourcelineParameters) {
    			SegmentLinearDTO segment = new SegmentLinearDTO();
    			segment.setBegin(resourcelineParameter.getMinValue());
    			segment.setEnd(resourcelineParameter.getMaxValue());
    			segments.add(segment);
            }
    		ResourceLinearDTO resLinDTO = new ResourceLinearDTO();
    		resLinDTO.setLinearParameterDTO(new LinearParameterDTO(lineSize.getDescription(), lineSize.getUnitName()));
    		resLinDTO.setSegments(segments);
    		resLinDTOs.add(resLinDTO);
        }
				
        List<Area> areas = (List<Area>) session.createCriteria(Area.class).
				add(Restrictions.eq("resource", resource)).list();
        List<PoligonAreaDTO> poligons = new ArrayList<PoligonAreaDTO>();
		for (Area area : areas) {
		//	if (area.getNumberOfPoint() == 1) {
				PoligonAreaDTO poligon = new PoligonAreaDTO();
				List<PointAreaDTO> pointDTOs = new ArrayList<PointAreaDTO>();
				PointAreaDTO pointDTO = new PointAreaDTO();
				pointDTO.setOrderNumber(area.getNumberOfPoint());
				pointDTO.setLatitudeValues(area.getLatitude());
				pointDTO.setLongitudeValues(area.getLongitude());
				pointDTOs.add(pointDTO);
				poligon.setPoints(pointDTOs);
				poligons.add(poligon);
		//	}

			
		}
		areaDTO.setPoligons(poligons);
		
        List<DiscreteValue> discreteValueList = (List<DiscreteValue>) session.createCriteria(DiscreteValue.class).
				add(Restrictions.eq("resourceType", resource.getType())).list();
        
        for (DiscreteValue discreteValue : discreteValueList) {
        	Criteria cr = session.createCriteria(StoreOfDiscreteValues.class);
    		Criterion res = Restrictions.eq("resource", resource);
    		Criterion discValueParam = Restrictions.eq("discreteValue", discreteValue);
    		LogicalExpression andExp = Restrictions.and(res, discValueParam);
    		cr.add( andExp );
    		List<StoreOfDiscreteValues> resourceDiscreteParameters = (List<StoreOfDiscreteValues>)cr.list();
    		List<Double> values =  new ArrayList<Double>();
    		for (StoreOfDiscreteValues resourceDiscreteParameter : resourceDiscreteParameters) {
    			values.add(resourceDiscreteParameter.getValue());
            }
			ResourceDiscreteDTO resDiscDTO = new ResourceDiscreteDTO();
			resDiscDTO.setDiscreteParameterDTO(new DiscreteParameterDTO(discreteValue.getDescription(), 
					discreteValue.getUnitName()));
			resDiscDTO.setValues(values);
    		resDiscDTOs.add(resDiscDTO);
        }
        

       
		
        ResourceTypeDTO resTypeDTO = new ResourceTypeDTO();
        resTypeDTO.setTypeName(resource.getType().getTypeName());
        String registratorName = resource.getUser().getLastName() + 
        		resource.getUser().getFirstName() + resource.getUser().getMiddleName();
		
		// adding all information in ResourceDTO object
		ResourceDTO resourceDTO = new ResourceDTO();
		resourceDTO.setResourceType(new ResourceTypeDTO());
        resourceDTO.setIdentifier(resource.getIdentifier());
        resourceDTO.setDescription(resource.getDesctiption());
        resourceDTO.setDate(resource.getDate());
        resourceDTO.setRegistratorName(registratorName);
        resourceDTO.setResourceArea(areaDTO);        	
        resourceDTO.setResourceDiscrete(resDiscDTOs);
        resourceDTO.setResourceLinear(resLinDTOs);
        resourceDTO.setTomeIdentifier(resource.getTome().getIdentifier());
        resourceDTO.setReasonInclusion(resource.getReasonInclusion());
        resourceDTO.setStatus(resource.getStatus());
        
		session.close();
		return resourceDTO;

	}

	// the implementation of this method is not finished yet. Anyone of us can
	// make changes here
	@Override
	public List<ResourceDTO> showAllResources() {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();

		List<ResourceDTO> resourceDTO = new ArrayList<ResourceDTO>();

		List<Resource> resource = DaoFactory.get().getResourceDao().getAll();

		for (Resource rs : resource) {

			ResourceTypeDTO rtDTO = new ResourceTypeDTO();
			rtDTO.setTypeName(rs.getType().getTypeName());

			ResourceDTO rDTO = new ResourceDTO();
			rDTO.setDate(rs.getDate());
			rDTO.setDescription(rs.getDesctiption());
			rDTO.setIdentifier(rs.getIdentifier());
			rDTO.setReasonInclusion(rs.getReasonInclusion());
			rDTO.setRegistratorName(rs.getUser().getFirstName() + "   "
					+ rs.getUser().getLastName());
			rDTO.setTomeIdentifier(rs.getTome().getIdentifier());
			rDTO.setResourceType(rtDTO);
			rDTO.setStatus(rs.getStatus());
			resourceDTO.add(rDTO);
		}

		transaction.commit();
		session.close();

		return resourceDTO;
	}

}
