package org.registrator.community.service;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;


import org.registrator.community.dao.LinearParameterRepository;

import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.ResourceType;

import org.registrator.community.service.LinearParameterService;

import org.registrator.community.service.impl.LinearParameterServiceImpl;


import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.transaction.Transactional;

//import static java.util.Arrays.asList;
import static org.mockito.Mockito.*;

/**
 * Created by  on 03.03.2016.
 * Test class for LinearParameterService interface implementation
 * */
@ActiveProfiles("testing")
@Transactional
@ContextConfiguration(classes={TestingConfiguration.class,SpringRootConfig.class})
public class LinearParameterServiceTest {
	 
	


    @InjectMocks
    private LinearParameterService serviceUnderTest;

    @Mock
    private ResourceType type;
    
    @Mock
    private LinearParameter line;
    
    private Integer idParameter;
    
    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private LinearParameterRepository parametrLineRepository;
    
    @Mock
    private Logger logger;
    
    @BeforeMethod
    public void initMocks() throws Exception {
        serviceUnderTest = new LinearParameterServiceImpl();
        MockitoAnnotations.initMocks(this);

        idParameter = 7;
        when(parametrLineRepository.findByLinearParameterId(idParameter)).thenReturn(line);
    }
    @Test
    public void findAllByResourceType() throws Exception {
        serviceUnderTest.findAllByResourceType(type);

        verify(parametrLineRepository, times(1)).findByResourceType(type);
    }
    
    @Test
    public void findById() throws Exception {
    	idParameter = 7;
        serviceUnderTest.findById(idParameter);

        verify(parametrLineRepository, times(1)).findByLinearParameterId(idParameter);
    }
    

    
}
