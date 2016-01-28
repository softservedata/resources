package org.registrator.community.controller;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.registrator.community.dao.RoleRepository;
import org.registrator.community.dto.PointAreaDTO;
import org.registrator.community.dto.PoligonAreaDTO;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.ResourceDiscreteValueDTO;
import org.registrator.community.dto.ResourceLinearValueDTO;
import org.registrator.community.dto.ResourceTypeDTO;
import org.registrator.community.dto.SegmentLinearDTO;
import org.registrator.community.dto.ValueDiscreteDTO;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.ResourceStatus;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.ResourceService;
import org.registrator.community.service.ResourceTypeService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.impl.DiscreteParameterServiceImpl;
import org.registrator.community.service.impl.LinearParameterServiceImpl;
import org.registrator.community.service.impl.ResourceDiscreteValueServiceImpl;
import org.registrator.community.service.impl.ResourceLinearValueServiceImpl;
import org.registrator.community.validator.ResTypeDTOValidator;
import org.registrator.community.validator.ResourceDTOValidator;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "/stresstest/")
public class StressController {
    @Autowired
    Logger logger;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ResourceTypeService resourceTypeService;

    @Autowired
    private DiscreteParameterServiceImpl discreteParameterService;

    @Autowired
    private LinearParameterServiceImpl linearParameterService;

    @Autowired
    private ResourceDiscreteValueServiceImpl resourceDiscreteValueService;

    @Autowired
    private ResourceLinearValueServiceImpl resourceLinearValueService;

    @Autowired
    private UserService userService;
    
    @Autowired
    private RoleRepository roleRepository;

    /**
     * 
     */
    
    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String addStressForm(Model model) {
        return "stress";
    }

    /**
     * 
     */
    @RequestMapping(value = "/post", method = RequestMethod.POST)
    public String startTesting(Model model) {
        Role registrator = roleRepository.findRoleByType(RoleType.REGISTRATOR);  
        User regStress = userService.getUserByLogin("registrator");
        ResourceDTO rDTO = new ResourceDTO();
        rDTO.setDate(new Date());
        rDTO.setDescription("новий");
        rDTO.setIdentifier("11143");
        rDTO.setReasonInclusion("Паспорт");
        rDTO.setRegistratorName("reg1");
        rDTO.setStatus(ResourceStatus.ACTIVE);
        rDTO.setTomeIdentifier("123456");
        rDTO.setResourceType("земельний");
        ResourceAreaDTO resArea = new ResourceAreaDTO(); 
        List<PoligonAreaDTO> listPoligon = new ArrayList<PoligonAreaDTO>();
        PoligonAreaDTO pol = new PoligonAreaDTO();
        List<PointAreaDTO> listpointarea = new ArrayList<PointAreaDTO>();
        PointAreaDTO pointarDTO = new PointAreaDTO();
        
      //BEGIN COUNTER!!!!
        pointarDTO.setOrderNumber(1);
        pointarDTO.setLatitudeDegrees(10);
        pointarDTO.setLatitudeMinutes(10);
        pointarDTO.setLatitudeSeconds(10);
        pointarDTO.setLatitudeValues(10.0);
        pointarDTO.setLongitudeDegrees(10);
        pointarDTO.setLongitudeMinutes(10);
        pointarDTO.setLongitudeSeconds(10);
        pointarDTO.setLongitudeValues(20.0);
        listpointarea.add(pointarDTO);
      //END COUNTER!!!! 
        listPoligon.add(pol);
        resArea.setPoligons(listPoligon);
        System.out.println("GOOD");
        List<ResourceDiscreteValueDTO> rdiscrete = new ArrayList<ResourceDiscreteValueDTO>();
        ResourceDiscreteValueDTO rdvDTO = new ResourceDiscreteValueDTO();
        rdvDTO.setDiscreteParameterDescription("площа");
        rdvDTO.setDiscreteParameterUnit("га");
        System.out.println("GOOD1");
        ValueDiscreteDTO valueDiscrete = new ValueDiscreteDTO();
        valueDiscrete.setComment("value");
        System.out.println("GOOD");
        valueDiscrete.setValue(11.0);
        List<ValueDiscreteDTO> listValue = new ArrayList<ValueDiscreteDTO>();
        listValue.add(valueDiscrete);
        rdvDTO.setValueDiscretes(listValue);
        rdiscrete.add(rdvDTO);
        List<ResourceLinearValueDTO> rlinear = new ArrayList<ResourceLinearValueDTO>();
        ResourceLinearValueDTO rlDTO = new ResourceLinearValueDTO(); 
        List<SegmentLinearDTO> listSegment = new ArrayList<SegmentLinearDTO>();
        SegmentLinearDTO seg = new SegmentLinearDTO();
        seg.setBegin(1.0);
        seg.setEnd(2.0); 
        listSegment.add(seg);
        rlDTO.setLinearParameterDescription("периметр");
        rlDTO.setLinearParameterUnit("м");
        rlDTO.setSegments(listSegment);
        rDTO.setResourceArea(resArea);
        rDTO.setResourceDiscrete(rdiscrete);
        rDTO.setResourceLinear(rlinear);
        ResourceDTO r = resourceService.addNewResource(rDTO, "registrator", regStress);
        return "stress";
     
    }
}
