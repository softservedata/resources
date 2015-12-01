package org.registrator.community.service.implementation;


import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dao.utils.HibernateUtil;
import org.registrator.community.dto.*;
import org.registrator.community.entity.*;
import org.registrator.community.service.interfaces.SearchService;
import org.registrator.community.service.interfaces.UserService;

import java.util.ArrayList;
import java.util.List;

public class UserServiceImpl implements UserService, SearchService {

	/**
	 * A method for registered user to make inquiry for getting sertificate 
	 * about the resource with known resource id in the database.
	 */
	public void InquiryGetSertificate(InquiryListDTO inquiryListDTO){
		Session session = null;
		Transaction tr = null;
		
		try{
			session = HibernateUtil.getSessionFactory().openSession();
			tr = session.beginTransaction();
			
			Inquiry inquiryEntity = new Inquiry();
			inquiryEntity.setInquiryType(inquiryListDTO.getInquiryType());
			inquiryEntity.setDate(inquiryListDTO.getDate());
			//inquiryEntity.setUser(inquiryListDTO.getFromUserId());
			Integer userId = inquiryListDTO.getFromUserId();
			User user = DaoFactory.get().getUserDao().findById(userId);
			inquiryEntity.setUser(user);
			Integer registratorId = inquiryListDTO.getToUserId();
			User registrator = DaoFactory.get().getUserDao().findById(registratorId);
			inquiryEntity.setRegistrator(registrator);
			Integer resourceId = inquiryListDTO.getResourceId();
			Resource resource = DaoFactory.get().getResourceDao().findById(resourceId);
			inquiryEntity.setResource(resource);
			tr.commit();
			
		} catch(HibernateException he){
			if (tr != null){
				tr.rollback();
			}
		} finally {
			if ((session != null) && (session.isOpen())){
				session.close();
			}
		}		
	}
	
	
	
	
	public void InquiryInputResource(InquiryListDTO inquiryListDTO){
		Session session = null;
		Transaction tr = null;
		
		try{
			
			
			
		} catch(HibernateException he){
			if (tr != null){
				tr.rollback();
			}
		} finally {
			if ((session != null) && (session.isOpen())){
				session.close();
			}
		}		
	}


	@Override
	public ResourceDTO getResource(Integer resourceId) {
		Session session = null;
		Transaction tr = null;

        ResourceDTO resourceDTO = new ResourceDTO();

		try {
			session = HibernateUtil.getSessionFactory().openSession();
			tr = session.beginTransaction();

			ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();

			LinearParameterDTO linearParameterDTO = new LinearParameterDTO();
            List<LinearParameterDTO> listLinearParameterDTO = new ArrayList<>();

			DiscreteParameterDTO discreteParameterDTO = new DiscreteParameterDTO();
            List<DiscreteParameterDTO> listDiscreteParameterDTO = new ArrayList<>();

			ResourceLinearDTO resourceLinearDTO = new ResourceLinearDTO();
            List<ResourceLinearDTO> listResourceLinearDTO = new ArrayList<>();
            SegmentLinearDTO segmentLinearDTO = new SegmentLinearDTO();
            List<SegmentLinearDTO> listSegmentLinearDTO = new ArrayList<>();

			ResourceDiscreteDTO resourceDiscreteDTO = new ResourceDiscreteDTO();
            List<ResourceDiscreteDTO> listResourceDiscreteDTO = new ArrayList<>();
            List<Double> listDiscreteValues = new ArrayList<>();

            PointAreaDTO pointAreaDTO = new PointAreaDTO();
			List<PointAreaDTO> listPointAreaDTO = new ArrayList<>();
			PoligonAreaDTO poligonAreaDTO = new PoligonAreaDTO();
            List<PoligonAreaDTO> listPoligonAreaDTO = new ArrayList<>();
			ResourceAreaDTO resourceAreaDTO = new ResourceAreaDTO();

			Resource resource = DaoFactory.get().getResourceDao().findById(resourceId);

            //Creating of the ResourceTypeDTO
            Integer resourceTypeId = resource.getType().getTypeId();
            List<LineSize> lineSizes = DaoFactory.get().getLineSizeDao().getAllByResourceTypeId(resourceTypeId);
            for (LineSize lineSize : lineSizes) {
                linearParameterDTO.setDescription(lineSize.getDescription());
                linearParameterDTO.setUnitName(lineSize.getUnitName());
                listLinearParameterDTO.add(linearParameterDTO);

                // Creating of the list of the ResourceLinearDTO

                List<StoreOfLineSizes> storeOfLineSizes = DaoFactory.get().getStoreOfLineSizesDao().getAllbyLineSizeId(lineSize.getLinesSizeId());
                for (StoreOfLineSizes storeOfLineSize : storeOfLineSizes) {
                    segmentLinearDTO.setBegin(storeOfLineSize.getMinValue());
                    segmentLinearDTO.setEnd(storeOfLineSize.getMaxValue());

                    listSegmentLinearDTO.add(segmentLinearDTO);
                }

                resourceLinearDTO.setLinearParameterDTO(linearParameterDTO);
                resourceLinearDTO.setSegments(listSegmentLinearDTO);

                listResourceLinearDTO.add(resourceLinearDTO);
            }

            List<DiscreteValue> discreteValues = DaoFactory.get().getDiscreteValueDao().getAllByResourceTypeId(resourceTypeId);
            for (DiscreteValue discreteValue : discreteValues) {
                discreteParameterDTO.setDescription(discreteValue.getDescription());
                discreteParameterDTO.setUnitName(discreteValue.getUnitName());
                listDiscreteParameterDTO.add(discreteParameterDTO);

                // Creating of the list of the ResourceDiscreteDTO

                List<StoreOfDiscreteValues> storeOfDiscreteValues = DaoFactory.get().getStoreOfDiscreteValuesDao().getAllBydiscreteValuesId(discreteValue.getDiscreteValueId());
                for (StoreOfDiscreteValues storeOfDiscreteValue : storeOfDiscreteValues) {
                    listDiscreteValues.add(storeOfDiscreteValue.getValue());
                }
                resourceDiscreteDTO.setDiscreteParameterDTO(discreteParameterDTO);
                resourceDiscreteDTO.setValues(listDiscreteValues);

                listResourceDiscreteDTO.add(resourceDiscreteDTO);
            }

            resourceTypeDTO.setTypeName(resource.getType().getTypeName());
            resourceTypeDTO.setLinearParameters(listLinearParameterDTO);
            resourceTypeDTO.setDiscreteParameters(listDiscreteParameterDTO);

            //Creating of the ResourceAreaDTO object

			List<Area> areas = DaoFactory.get().getAreaDao().getAllByResourceId(resourceId);
            boolean firstIteration = true;
            for (Area area : areas) {
                int pointNumber = area.getNumberOfPoint();
                pointAreaDTO.setOrderNumber(pointNumber);
                pointAreaDTO.setLatitudeValues(area.getLatitude());
                pointAreaDTO.setLongitudeValues(area.getLongitude());
                listPointAreaDTO.add(pointAreaDTO);

                /* Створення полігонів базується на ствредженні, що
                *  точки заносяться в базу і виводяться з бази згідно
                *  порядковому номеру. Відповідно, коли номер точки
                *  знову рівний 1, ми записуємо ліст точок в полігон
                *  і цей полігон додаємо в ліст полігонів.
                * */
                if ((pointNumber == 1)&&(!firstIteration)) {
                    poligonAreaDTO.setPoints(listPointAreaDTO);
                    listPoligonAreaDTO.add(poligonAreaDTO);
                    listPointAreaDTO.clear();
                }
                firstIteration = false;
            }

            resourceAreaDTO.setPoligons(listPoligonAreaDTO);

            // Creating of the resourceDTO

            resourceDTO.setResourceType(resourceTypeDTO);
            resourceDTO.setIdentifier(resource.getIdentifier());
            resourceDTO.setDate(resource.getDate());
            // Потрібно визначитись яку інформацію по реєстратору виводити в інформації про ресурс.
            String registratorName = resource.getUser().getLastName() + resource.getUser().getFirstName() + resource.getUser().getMiddleName();
            resourceDTO.setRegistratorName(registratorName);
            resourceDTO.setResourceArea(resourceAreaDTO);
            resourceDTO.setResourceDiscrete(listResourceDiscreteDTO);
            resourceDTO.setResourceLinear(listResourceLinearDTO);
            resourceDTO.setTomeIdentifier(resource.getTome().getIdentifier());
            resourceDTO.setReasonInclusion(resource.getReasonInclusion());
            resourceDTO.setStatus(resource.getStatus());
		}
        catch(HibernateException he){
            if (tr != null){
                tr.rollback();
            }
        } finally {
            if ((session != null) && (session.isOpen())){
                session.close();
            }
        }

		return resourceDTO;
	}

}
