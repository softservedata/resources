package org.registrator.community.application;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.registrator.community.dto.DiscreteParameterDTO;
import org.registrator.community.dto.LinearParameterDTO;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceDiscreteValueDTO;
import org.registrator.community.dto.ResourceLinearValueDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.SegmentLinearDTO;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.service.implementation.RegistratorServiceImpl;
import org.registrator.community.service.interfaces.RegistratorService;

public class RegistratorServiceApp {
    
	public static void main(String[] args) {
		RegistratorService registratorService = new RegistratorServiceImpl();
		
			

		/* saving new resource type in database */		
//		ResourceTypeDTO resourceTypeDTO = createNewResourceTypeDTO();	
//		registratorService.addResourseType(resourceTypeDTO);							
		
			
		/* saving new resource type in database */
//		ResourceDTO resourceDTO = createNewResourceDTO();
//		registratorService.addResource(resourceDTO);
		
		
		
	}

	static ResourceTypeDTO createNewResourceTypeDTO() {
		// creating new resourceTypeDTO object
		ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
		resourceTypeDTO.setTypeName("водний");
		List<LinearParameterDTO> linearParameterDTOs = new ArrayList<LinearParameterDTO>();
		linearParameterDTOs.add(new LinearParameterDTO("глибина", "км"));
		linearParameterDTOs.add(new LinearParameterDTO("протяжність", "км"));
		List<DiscreteParameterDTO> discreteParameterDTOs = new ArrayList<DiscreteParameterDTO>();
		discreteParameterDTOs.add(new DiscreteParameterDTO("площа", "га"));
		discreteParameterDTOs.add(new DiscreteParameterDTO("периметер", "км"));
		discreteParameterDTOs.add(new DiscreteParameterDTO("обєм", "км"));
		resourceTypeDTO.setLinearParameters(linearParameterDTOs);
		resourceTypeDTO.setDiscreteParameters(discreteParameterDTOs);
		return resourceTypeDTO;
	}
	
	static ResourceDTO createNewResourceDTO() {
		ResourceTypeDTO resourceTypeDTO = new ResourceTypeDTO();
		resourceTypeDTO.setTypeName("водний");

		// creatina AreaDTO
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

		// creating ResourceLinearDTO  		
		List<ResourceLinearValueDTO> resourceLinearDTOs = new ArrayList<ResourceLinearValueDTO>();
		LinearParameterDTO linearParameterDTO1 = new LinearParameterDTO();
		linearParameterDTO1.setDescription("глибина");		
		List<SegmentLinearDTO> segments1 = new ArrayList<SegmentLinearDTO>() {{
			add(new SegmentLinearDTO(124,183.5));
			add(new SegmentLinearDTO(54.0,58.));
		}};
		ResourceLinearValueDTO resourceLinearDTO1 = new ResourceLinearValueDTO();
		resourceLinearDTO1.setLinearParameterDTO(linearParameterDTO1);
		resourceLinearDTO1.setSegments(segments1);
		resourceLinearDTOs.add(resourceLinearDTO1);
		
		LinearParameterDTO linearParameterDTO2 = new LinearParameterDTO();
		linearParameterDTO2.setDescription("протяжність");		
		List<SegmentLinearDTO> segments2 = new ArrayList<SegmentLinearDTO>() {{
		    add(new SegmentLinearDTO(1234,1583.5));
		}};
		ResourceLinearValueDTO resourceLinearDTO2 = new ResourceLinearValueDTO();
		resourceLinearDTO2.setLinearParameterDTO(linearParameterDTO2);
		resourceLinearDTO2.setSegments(segments2);
		resourceLinearDTOs.add(resourceLinearDTO2);
		
		
		List<DiscreteParameterDTO> discreteParameterDTOs = new ArrayList<DiscreteParameterDTO>();
		discreteParameterDTOs.add(new DiscreteParameterDTO("площа", "га"));
		discreteParameterDTOs.add(new DiscreteParameterDTO("периметер", "км"));
		discreteParameterDTOs.add(new DiscreteParameterDTO("обєм", "км"));

		// creating ResourceDiscreteDTOs  		
		List<ResourceDiscreteValueDTO> resourceDiscreteDTOs = new ArrayList<ResourceDiscreteValueDTO>();
		DiscreteParameterDTO disDTO1 = new DiscreteParameterDTO();
		disDTO1.setDescription("площа");
		List<Double> values1 = new ArrayList<Double>() {{
		    add(2804d);
		}};
		ResourceDiscreteValueDTO resourceDiscreteDTO1 = new ResourceDiscreteValueDTO();
		resourceDiscreteDTO1.setDiscreteParameterDTO(disDTO1);
		resourceDiscreteDTO1.setValues(values1);
		
		DiscreteParameterDTO disDTO2 = new DiscreteParameterDTO();
		disDTO2.setDescription("периметер");
		List<Double> values2 = new ArrayList<Double>() {{
		    add(1206d);
		}};
		ResourceDiscreteValueDTO resourceDiscreteDTO2 = new ResourceDiscreteValueDTO();
		resourceDiscreteDTO2.setDiscreteParameterDTO(disDTO2);
		resourceDiscreteDTO2.setValues(values2);
		

		DiscreteParameterDTO disDTO3 = new DiscreteParameterDTO();
		disDTO3.setDescription("обєм");
		List<Double> values3 = new ArrayList<Double>();
			values3.add(4536.35);
		ResourceDiscreteValueDTO resourceDiscreteDTO3 = new ResourceDiscreteValueDTO();
		resourceDiscreteDTO3.setDiscreteParameterDTO(disDTO3);
		resourceDiscreteDTO3.setValues(values3);

		resourceDiscreteDTOs.add(resourceDiscreteDTO1);
		resourceDiscreteDTOs.add(resourceDiscreteDTO2);
		resourceDiscreteDTOs.add(resourceDiscreteDTO3);
		
		
		//creatint resourceDTO with concrete parameters
		ResourceDTO resourceDTO = new ResourceDTO();
		resourceDTO.setResourceType(resourceTypeDTO);
		resourceDTO.setDescription("річка");
		resourceDTO.setIdentifier("115678");
		resourceDTO.setRegistratorName("Петро");
		resourceDTO.setTomeIdentifier("12345");
		resourceDTO.setDate(new Date());
		resourceDTO.setStatus(ResourceStatus.ACTIVE);
		resourceDTO.setReasonInclusion("Паспорт громадянина україни...");
		resourceDTO.setResourceArea(resourceAreaDTO);
		resourceDTO.setResourceDiscrete(resourceDiscreteDTOs);
		resourceDTO.setResourceLinear(resourceLinearDTOs);
		
		return resourceDTO;
	}

}