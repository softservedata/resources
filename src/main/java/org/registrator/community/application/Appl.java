package org.registrator.community.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.dao.RoleDao;
import org.registrator.community.dao.UserDao;
import org.registrator.community.dao.daofactory.DaoFactory;
import org.registrator.community.dto.DiscreteParameterDTO;
import org.registrator.community.dto.LinearParameterDTO;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.entity.ResourceStatus;
import org.registrator.community.dto.ResourceDiscreteDTO;
import org.registrator.community.dto.ResourceLinearDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.SegmentLinearDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.entity.UserStatus;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class Appl {

	public static void main(String[] args) {
		/*Tome tome = new Tome();
			tome.setIdentifier("12345");
				tome.setName("name??");
				tome.setUser(DaoFactory.get().getUserDao().findById(1));
				DaoFactory.get().getTomeDao().add(tome);;
				
				UserDTO registrator = new UserDTO();
				registrator.setFirstName("FirstName");
			
				ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
				resourceTypeDTO.setTypeName("радіочастота");
				
				
				List<PointAreaDTO> PointAreaDTOs = new ArrayList<PointAreaDTO>();
				PointAreaDTOs.add(new PointAreaDTO(1, 49, 51, 49.62, 24, 01, 31.19));
				PointAreaDTOs.add(new PointAreaDTO(2, 49, 51, 50.01, 24, 01, 33.27));
				PointAreaDTOs.add(new PointAreaDTO(3, 49, 51, 49.40, 24, 01, 33.56));
				PointAreaDTOs.add(new PointAreaDTO(4, 49, 51, 49.03, 24, 01, 31.48));
			
				PoligonAreaDTO poligonAreaDTO = new PoligonAreaDTO();
				poligonAreaDTO.setPoints(PointAreaDTOs);
				ResourceAreaDTO resourceAreaDTO = new ResourceAreaDTO();
				List<PoligonAreaDTO> poligonAreaDTOs = new ArrayList<PoligonAreaDTO>();
				poligonAreaDTOs.add(poligonAreaDTO);
				resourceAreaDTO.setPoligons(poligonAreaDTOs);
				
			List<ResourceLinearDTO> resourceLinearDTOs = new ArrayList<ResourceLinearDTO>();
				LinearParameterDTO linearParameterDTO = new LinearParameterDTO();
				linearParameterDTO.setDescription("cмуга радіочастот");		
				List<SegmentLinearDTO> segments = new ArrayList<SegmentLinearDTO>() {{
				    add(new SegmentLinearDTO(2400,2483.5));
				    add(new SegmentLinearDTO(5150,5350));
				    add(new SegmentLinearDTO(2500,2700));
				}};
				
				ResourceLinearDTO resourceLinearDTO = new ResourceLinearDTO();
				resourceLinearDTO.setLinearParameterDTO(linearParameterDTO);
				resourceLinearDTO.setSegments(segments);
				resourceLinearDTOs.add(resourceLinearDTO);
				
				List<ResourceDiscreteDTO> resourceDiscreteDTOs = new ArrayList<ResourceDiscreteDTO>();
				
				DiscreteParameterDTO disDTO1 = new DiscreteParameterDTO();
				disDTO1.setDescription("потужність");
				List<Double> values1 = new ArrayList<Double>() {{
				    add(100d);
				    add(500.55);
				    add(23.54);
				}};
				ResourceDiscreteDTO resourceDiscreteDTO1 = new ResourceDiscreteDTO();
				resourceDiscreteDTO1.setDiscreteParameterDTO(disDTO1);
				resourceDiscreteDTO1.setValues(values1);
				
			DiscreteParameterDTO disDTO2 = new DiscreteParameterDTO();
				disDTO2.setDescription("напруженість");
				List<Double> values2 = new ArrayList<Double>() {{
				    add(200d);
				}};
				ResourceDiscreteDTO resourceDiscreteDTO2 = new ResourceDiscreteDTO();
				resourceDiscreteDTO2.setDiscreteParameterDTO(disDTO2);
				resourceDiscreteDTO2.setValues(values2);
				
				
				resourceDiscreteDTOs.add(resourceDiscreteDTO1);
				resourceDiscreteDTOs.add(resourceDiscreteDTO2);
			
				ResourceDTO resourceDTO = new ResourceDTO();
				resourceDTO.setResourceType(resourceTypeDTO);
				resourceDTO.setIdentifier("identifier");
				resourceDTO.setRegistratorName("FirstName");
				resourceDTO.setTomeIdentifier("12345");
				resourceDTO.setDate(new Date());
				resourceDTO.setStatus(ResourceStatus.ACTIVE);
				resourceDTO.setReasonInclusion("Паспорт громадянина україни...");
				resourceDTO.setResourceArea(resourceAreaDTO);
				resourceDTO.setResourceDiscrete(resourceDiscreteDTOs);
				resourceDTO.setResourceLinear(resourceLinearDTOs);
					
				RegistratorService registratorService = new RegistratorServiceImpl();
				registratorService.addResource(resourceDTO);
			
		 	 		
	*/}

}
