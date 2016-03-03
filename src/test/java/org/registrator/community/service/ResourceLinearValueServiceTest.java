package org.registrator.community.service;

import static java.util.Arrays.asList;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import javax.transaction.Transactional;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.ResourceLinearValueRepository;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.service.impl.ResourceLinearValueServiceImpl;
import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by roman.golyuk on 02.03.2016.
 * Test class for ResourceLinearValueService interface implementation
 * */

@ActiveProfiles("testing")
@Transactional
@ContextConfiguration(classes={TestingConfiguration.class,SpringRootConfig.class})
public class ResourceLinearValueServiceTest{
    @InjectMocks
    private ResourceLinearValueService serviceUnderTest;

    @Mock
    private Resource resource;

    @Mock
    private LinearParameter parameter;

    private Integer idParameter;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ResourceLinearValueRepository valueRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private LinearParameterRepository parameterRepository;

    @Mock
    private Logger logger;

    @BeforeMethod
    public void initMocks() throws Exception {
        serviceUnderTest = new ResourceLinearValueServiceImpl();
        MockitoAnnotations.initMocks(this);

        idParameter = 47;
        when(parameterRepository.findByLinearParameterId(idParameter)).thenReturn(parameter);

    }


    @Test
    public void findByResource() throws Exception {
        serviceUnderTest.findByResource(resource);

        verify(valueRepository, times(1)).findByResource(resource);
    }

    @Test
    public void findAllByLinearParameter() throws Exception {
        serviceUnderTest.findAllByLinearParameter(parameter);

        verify(valueRepository, times(1)).findAllByLinearParameter(parameter);
    }

    @Test
    public void findAllByValueAndLinearParameter() throws Exception {
        double value = 47.0;

        serviceUnderTest.findAllByValueAndLinearParameter(value, parameter);
        verify(valueRepository, times(1)).findByValueAndLinearParameterId(value, parameter);
    }

    @Test
    public void findResourcesbyLinearParam() throws Exception {
        double value = 47.0;

        serviceUnderTest.findResourcesbyLinearParam(idParameter, value);
        verify(parameterRepository, times(1)).findByLinearParameterId(idParameter);

    }

    @Test
    public void findResourcesByLinParamList() {
        double value = 47.0;

        serviceUnderTest.findResourcesByLinParamList(asList(idParameter, idParameter), asList(value, value));
        verify(parameterRepository, times(2)).findByLinearParameterId(idParameter);
    }


}