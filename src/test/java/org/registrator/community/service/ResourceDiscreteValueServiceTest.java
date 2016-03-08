package org.registrator.community.service;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.spy;
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
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.Resource;
import org.registrator.community.service.impl.ResourceDiscreteValueServiceImpl;
import org.slf4j.Logger;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Created by roman.golyuk on 02.03.2016.
 * Test class for ResourceDiscreteValueService interface implementation
 * */

@ActiveProfiles("testing")
@Transactional
@ContextConfiguration(classes={TestingConfiguration.class,SpringRootConfig.class})
public class ResourceDiscreteValueServiceTest {

    @InjectMocks
    private ResourceDiscreteValueService serviceUnderTest;

    @Mock
    private Resource resource;

    @Mock
    private DiscreteParameter parameter;

    private Integer idParameter;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ResourceDiscreteValueRepository valueRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private DiscreteParameterRepository parameterRepository;

    @Mock
    private Logger logger;

    @BeforeMethod
    public void initMocks() throws Exception {
        serviceUnderTest = new ResourceDiscreteValueServiceImpl();
        MockitoAnnotations.initMocks(this);

        idParameter = 47;
        when(parameterRepository.findByDiscreteParameterId(idParameter)).thenReturn(parameter);

    }


    @Test
    public void findByResource() throws Exception {
        serviceUnderTest.findByResource(resource);

        verify(valueRepository, times(1)).findByResource(resource);
    }

    @Test
    public void findAllByDiscreteParameter() throws Exception {
        serviceUnderTest.findAllByDiscreteParameter(parameter);

        verify(valueRepository, times(1)).findAllByDiscreteParameter(parameter);
    }

    @Test
    public void findAllByValueAndDiscreteParameter() throws Exception {
        double value = 47.0;

        serviceUnderTest.findAllByValueAndDiscreteParameter(value, parameter);
        verify(valueRepository, times(1)).findAllByValueAndDiscreteParameter(value, parameter);
    }

    @Test
    public void findAllByBiggerValueAndDiscreteParameter() throws Exception {
        double value = 47.0;

        serviceUnderTest.findAllByBiggerValueAndDiscreteParameter(value, parameter);
        verify(valueRepository, times(1)).findAllByBiggerValueAndDiscreteParameter(value, parameter);
    }

    @Test
    public void findAllBySmallerValueAndDiscreteParameter() throws Exception {
        double value = 47.0;

        serviceUnderTest.findAllBySmallerValueAndDiscreteParameter(value, parameter);
        verify(valueRepository, times(1)).findAllBySmallerValueAndDiscreteParameter(value, parameter);
    }

    @Test
    public void findResourcesbyDiscreteParamLess() throws Exception {
        serviceUnderTest = spy(serviceUnderTest);


        double value = 47.0;

        serviceUnderTest.findResourcesByDiscreteParam(idParameter, "less", value);
        verify(parameterRepository, times(1)).findByDiscreteParameterId(idParameter);
        verify(valueRepository, times(1)).findAllBySmallerValueAndDiscreteParameter(eq(value), any());

    }

    @Test
    public void findResourcesbyDiscreteParamGreater() throws Exception {
        serviceUnderTest = spy(serviceUnderTest);


        double value = 47.0;

        serviceUnderTest.findResourcesByDiscreteParam(idParameter, "greater", value);
        verify(parameterRepository, times(1)).findByDiscreteParameterId(idParameter);
        verify(valueRepository, times(1)).findAllByBiggerValueAndDiscreteParameter(eq(value), any());

    }

    @Test
    public void findResourcesbyDiscreteParamEqual() throws Exception {
        serviceUnderTest = spy(serviceUnderTest);


        double value = 47.0;

        serviceUnderTest.findResourcesByDiscreteParam(idParameter, "equal", value);
        verify(parameterRepository, times(1)).findByDiscreteParameterId(idParameter);
        verify(valueRepository, times(1)).findAllByValueAndDiscreteParameter(eq(value), any());

    }


    @Test
    public void findResourcesByParamsList() throws Exception {
        serviceUnderTest = spy(serviceUnderTest);


        double value = 47.0;

        serviceUnderTest.findResourcesByParamsList(
                asList(47, 47, 47),
                asList("equal", "greater", "less"),
                asList(value, value, value));
        verify(parameterRepository, times(3)).findByDiscreteParameterId(idParameter);
        verify(valueRepository, times(1)).findAllBySmallerValueAndDiscreteParameter(eq(value), any());
        verify(valueRepository, times(1)).findAllByBiggerValueAndDiscreteParameter(eq(value), any());
        verify(valueRepository, times(1)).findAllByValueAndDiscreteParameter(eq(value), any());

    }


}