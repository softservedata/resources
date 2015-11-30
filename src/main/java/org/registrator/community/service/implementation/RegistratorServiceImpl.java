package org.registrator.community.service.implementation;


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

public class RegistratorServiceImpl implements RegistratorService{

	@Override
	public void addResourseType(ResourceTypeDTO resourceTypeDTO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		ResourceType resourceEntity = new ResourceType();
		resourceEntity.setTypeName(resourceTypeDTO.getTypeName());
		DaoFactory.get().getResourceTypeDao().add(resourceEntity);
		
		List<LinearParameterDTO> linearParameters = resourceTypeDTO.getLinearParameters();
		List<DiscreteParameterDTO> discreteParameters = resourceTypeDTO.getDiscreteParameters();
		

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
			DiscreteParameterDTO discreteParameterDTO = discreteParameters.get(i);
			discreteValueEntity.setResourceType(resourceEntity);
			discreteValueEntity.setDescription(discreteParameterDTO.getDescription());
			discreteValueEntity.setUnitName(discreteParameterDTO.getUnitName());
			DaoFactory.get().getDiscreteValueDao().add(discreteValueEntity);			
		}
 
		/*	for (String linearParameterDTO : linearParameters) {
			LineSize lineSizeEntyty = new LineSize();
			lineSizeEntyty.setResourceType(resourceEntity);
			lineSizeEntyty.setDescription(linearParameterDTO.getDescription());
			DaoFactory.get().getLineSizeDao().add(lineSizeEntyty);	
			
		}*/
				
		transaction.commit();
		session.close();
				
	}

	@Override
	public void addResource(ResourceDTO resourceDTO) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		Transaction transaction = session.beginTransaction();
		
		// list for table area
		List<PoligonAreaDTO> poligonAreaDTOs = resourceDTO.getResourceArea().getPoligons();
		
		// list for table store of line sizes
		List<ResourceLinearDTO> resourceLinearDTOs = resourceDTO.getResourceLinear();
		
		// list for table store of discrete values
		List<ResourceDiscreteDTO> resourceDiscreteDTOs = resourceDTO.getResourceDiscrete();
		

		// filling table list_of_resources
		ResourceType resourceType = (ResourceType) session.createCriteria(ResourceType.class)
                .add(Restrictions.eq("typeName", resourceDTO.getResourceType().getTypeName())).uniqueResult();
		
		User registrator = (User) session.createCriteria(User.class)
                .add(Restrictions.eq("firstName", resourceDTO.getRegistratorName())).uniqueResult();
		
		Tome tome = (Tome) session.createCriteria(Tome.class)
                .add(Restrictions.eq("identifier", resourceDTO.getTomeIdentifier())).uniqueResult();
		
		Resource resourceEntity = new Resource(resourceType, resourceDTO.getIdentifier(), registrator, 
				resourceDTO.getDate(), resourceDTO.getStatus().toString(),tome, resourceDTO.getReasonInclusion());
		DaoFactory.get().getResourceDao().add(resourceEntity);
		
			
		// filling table area
		for (int i = 0; i < poligonAreaDTOs.size(); i++) {
			PoligonAreaDTO poligonAreaDTO =  poligonAreaDTOs.get(i);
			List<PointAreaDTO> pointAreaDTOs = poligonAreaDTO.getPoints();
			for (int j = 0; j < pointAreaDTOs.size(); j++) {
				Area area = new Area();
				PointAreaDTO point = pointAreaDTOs.get(j);
				Double latitude = point.getLatitudeDegrees() + point.getLatitudeMinutes()/60d +
						point.getLatitudeSeconds()/3600d;
				Double longitude = point.getLongitudeDegrees() + point.getLongitudeMinutes()/60d +
						point.getLongitudeSeconds()/3600d;
				area.setResource(resourceEntity);
				area.setNumberOfPoint(point.getOrderNumber());
				area.setLatitude(latitude);
				area.setLongitude(longitude);	
				DaoFactory.get().getAreaDao().add(area);
			}
		}	
		
		// filling table store of line sizes
		for (int i = 0; i < resourceLinearDTOs.size(); i++) {
			ResourceLinearDTO resourceLinearDTO =  resourceLinearDTOs.get(i);
			LinearParameterDTO linearParameterDTO =  resourceLinearDTO.getLinearParameterDTO();
			
			Criteria cr = session.createCriteria(LineSize.class);
			Criterion resource = Restrictions.eq("resourceType", resourceType);
			Criterion name = Restrictions.eq("description",linearParameterDTO.getDescription());
			LogicalExpression andExp = Restrictions.and(resource, name);
			cr.add( andExp );
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
			ResourceDiscreteDTO resourceDiscreteDTO =  resourceDiscreteDTOs.get(i);
			DiscreteParameterDTO discreteParameterDTO =  resourceDiscreteDTO.getDiscreteParameterDTO();
			
			Criteria cr = session.createCriteria(DiscreteValue.class);
			Criterion resource = Restrictions.eq("resourceType", resourceType);
			Criterion name = Restrictions.eq("description",discreteParameterDTO.getDescription());
			LogicalExpression andExp = Restrictions.and(resource, name);
			cr.add( andExp );
			DiscreteValue discreteValue = (DiscreteValue) cr.uniqueResult();
	
			List<Double> values = resourceDiscreteDTO.getValues();
			for (int j = 0; j < values.size(); j++) {	
				StoreOfDiscreteValues storeOfDiscreteValues = new StoreOfDiscreteValues();
				storeOfDiscreteValues.setResource(resourceEntity);
				storeOfDiscreteValues.setDiscreteValue(discreteValue);
				storeOfDiscreteValues.setValue(values.get(j));		
				DaoFactory.get().getStoreOfDiscreteValuesDao().add(storeOfDiscreteValues);
				}
			}
			
			
		transaction.commit();
		session.close();		
	}

}
