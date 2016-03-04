package org.registrator.community.service;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;

import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.registrator.community.config.root.SpringRootConfig;
import org.registrator.community.config.root.TestingConfiguration;
import org.registrator.community.dao.DiscreteParameterRepository;
import org.registrator.community.dao.LinearParameterRepository;
import org.registrator.community.dao.PolygonRepository;
import org.registrator.community.dao.ResourceDiscreteValueRepository;
import org.registrator.community.dao.ResourceLinearValueRepository;
import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.ResourceRepository;
import org.registrator.community.dao.ResourceTypeRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.ResourceAreaDTO;
import org.registrator.community.dto.ResourceDTO;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.entity.Polygon;
import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.ResourceType;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.service.impl.ResourceServiceImpl;
import org.slf4j.Logger;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Unit test for Resource service methods
 */

@ActiveProfiles("testing")
@Transactional
@ContextConfiguration(classes={TestingConfiguration.class,SpringRootConfig.class})
public class ResourceServiceTest {
	private String validID = "1";
    private String nonValidID = "2";
    private Resource validResource;
    private ResourceDTO validResourceDTO;
    private ResourceAreaDTO validResourceAreaDTO;
    private User registrator;
    private String registratorLogin = "registrator";
    private Polygon validPolygon;

    @InjectMocks
    private ResourceService serviceUnderTest = new ResourceServiceImpl();
    
    @Mock
    private ResourceRepository resourceRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private LinearParameterRepository linearParameterRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ResourceLinearValueRepository linearValueRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private DiscreteParameterRepository discreteParameterRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ResourceDiscreteValueRepository discreteValueRepository;

    @Mock
    private PolygonRepository polygonRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private TomeRepository tomeRepository;

    @Mock(answer = Answers.RETURNS_SMART_NULLS)
    private ResourceTypeRepository resourceTypeRepository;

    @Mock
    private InquiryService inquiryService;

    @Mock
    private UserRepository userRepository;

    @Mock
    ResourceNumberRepository resourceNumberRepository;

    @Mock
    private Logger logger;
    
    @BeforeMethod
    public void initMocks() {
        
    	MockitoAnnotations.initMocks(this);
    	validResource = mock(Resource.class);
        validResourceDTO = mock(ResourceDTO.class, Mockito.RETURNS_SMART_NULLS);;
        validResourceAreaDTO = mock(ResourceAreaDTO.class, Mockito.RETURNS_SMART_NULLS);
        registrator = mock(User.class);

        validPolygon = new Polygon(24.0, 49.0, 24.0, 49.0, validResource);
        validPolygon.setCoordinates("[{\"lat\":49,\"lng\":24},{\"lat\":49,\"lng\":26}]");

    	when(validResource.getIdentifier()).thenReturn(validID);
        when(validResource.getRegistrator()).thenReturn(mock(User.class));
        when(validResource.getTome()).thenReturn(mock(Tome.class));
        when(validResource.getType()).thenReturn(mock(ResourceType.class));
        when(validResource.getDate()).thenReturn(new Date());
        when(validResourceDTO.getIdentifier()).thenReturn(validID);
        when(validResourceDTO.getResourceArea()).thenReturn(validResourceAreaDTO);
        when(validResourceDTO.getIdentifier()).thenReturn(validID);

        when(registrator.getLogin()).thenReturn(registratorLogin);

     
    	
        when(resourceRepository.save(validResource)).thenReturn(validResource);
        when(userRepository.findUserByLogin(registratorLogin)).thenReturn(registrator);

        when(polygonRepository.findByResource(validResource)).thenReturn(Arrays.asList(validPolygon));

    }
    
    @Test
    public void findResourceByIdentifierValidId() {
        when(resourceRepository.findByIdentifier(validID)).thenReturn(validResource);
        ResourceDTO resourceDTO = serviceUnderTest.findByIdentifier(validID);
        assertEquals(resourceDTO.getIdentifier(), validID, "Find by valid ID should return valid ResourceDTO");
      
    }

    @Test
    public void findResourceByIdentifierNonValidId() {

        ResourceDTO resourceDTO = serviceUnderTest.findByIdentifier(nonValidID);
        assertTrue(resourceDTO == null, "Find by non valid ID should return null");

    }

    @Test
    public void findResourceByIdentifierNullId() {

        ResourceDTO resourceDTO = serviceUnderTest.findByIdentifier(null);
        assertTrue(resourceDTO == null, "Find by null should return null");

    }

    @Test(dependsOnMethods = {"findResourceByIdentifierValidId"})
    public void addNewResourceValid() {

    	// ResourceServiceImpl use findByIdentifier internally to return created resource
    	// stubbing this behavior
        serviceUnderTest = spy(serviceUnderTest);
        doReturn(validResourceDTO).when(serviceUnderTest).findByIdentifier(validID);
        
        when(resourceRepository.save(any(Resource.class))).thenReturn(validResource);

        String ownerLogin = "user";

        ResourceDTO result = serviceUnderTest.addNewResource(validResourceDTO, ownerLogin, registrator);
        assertEquals(result.getIdentifier(), validResourceDTO.getIdentifier(), "Creation of valid Resource should be succesful");
        verify(inquiryService, times(1)).addInputInquiry(same(ownerLogin), any(), same(registrator));
    }

    @Test(dependsOnMethods = {"findResourceByIdentifierValidId", "addNewResourceValid"})
    public void addNewResourceEmptyOwner() {

        doReturn(validResourceDTO).when(serviceUnderTest).findByIdentifier(validID);
        when(resourceRepository.save(any(Resource.class))).thenReturn(validResource);

        String ownerLogin = "";

        ResourceDTO result = serviceUnderTest.addNewResource(validResourceDTO, ownerLogin, registrator);
        assertEquals(result.getIdentifier(), validResourceDTO.getIdentifier(), "Creation Resource with empy owner should be succesful");
        verify(inquiryService, times(0)).addInputInquiry(same(ownerLogin), any(), same(registrator));

    }


    @Test(dataProvider = "numbersToString")
    public void getRegistrationNumber(Integer number, String numberRepresentaion) {
        String registarionNumber = "test";
        ResourceNumber resourceNumber = mock(ResourceNumber.class);
        when(resourceNumber.getNumber()).thenReturn(number);
        when(resourceNumber.getRegistratorNumber()).thenReturn(registarionNumber);

        when(resourceNumberRepository.findResourceNumberByUser(registrator)).thenReturn(resourceNumber);

        String result = serviceUnderTest.getRegistrationNumber(registratorLogin);

        assertEquals(result, registarionNumber + numberRepresentaion);
    }


    @Test
    public void createPolygonJSON() {
        List<PolygonJSON> result = serviceUnderTest.createPolygonJSON(validResource, 0);

        assertEquals(result.size(), 1);
    }

    @Test
    void findByTypeUseRepository() {

        serviceUnderTest.findByType(mock(ResourceType.class));
        verify(resourceRepository, times(1)).findByType(any());

    }

    @Test
    void getDescriptionBySearchTagUseRepository() {

        String queryParam = "testString";
        serviceUnderTest.getDescriptionBySearchTag(queryParam);
        verify(resourceRepository, times(1)).findDescriptionsLikeProposed(queryParam);

    }


    @Test
    void getAllByAreaLimitsUseRepository() {
        Random r = new Random(42);
        
        double latMaxValue = 90;
        double lonMaxValue = 180;
        
        double minLat = r.nextDouble() * latMaxValue;
        double maxLat = r.nextDouble() * latMaxValue;
        double minLon = r.nextDouble() * lonMaxValue;
        double maxLon = r.nextDouble() * lonMaxValue;

        serviceUnderTest.getAllByAreaLimits(minLat, maxLat, minLon, maxLon, "", 1);
        verify(polygonRepository, times(1)).
        	findByLimits(eq(minLat), eq(maxLat), eq(minLon), eq(maxLon), any(Pageable.class));

    }


    @Test
    void getAllByPointUseRepository() {
    	
        double lat = 34;
        double lon = 40;

        serviceUnderTest.getAllByPoint(lat,lon, 0);
        verify(polygonRepository, times(1)).findByPoint(eq(lat), eq(lon), any(Pageable.class));
        
    }

    @Test
    void countAllByAreaLimitsUseRepository() {
        Random r = new Random(42);

        double latMaxValue = 90;
        double lonMaxValue = 180;


        double minLat = r.nextDouble() * latMaxValue;
        double maxLat = r.nextDouble() * latMaxValue;
        double minLon = r.nextDouble() * lonMaxValue;
        double maxLon = r.nextDouble() * lonMaxValue;


        serviceUnderTest.countAllByAreaLimits(minLat, maxLat, minLon, maxLon);
        verify(polygonRepository, times(1)).countByLimits(minLat, maxLat, minLon, maxLon);

    }

    @Test
    void countAllByPointUseRepository() {
        Random r = new Random(42);

        double latMaxValue = 90;
        double lonMaxValue = 180;

        double lat = r.nextDouble() * latMaxValue;
        double lon = r.nextDouble() * lonMaxValue;

        serviceUnderTest.countAllByPoint(lat,lon);
        verify(polygonRepository, times(1)).countByPoint(lat, lon);

    }

    @DataProvider
    private Object[][] numbersToString() {
        return new Object[][]{
                {1, "0001"},
                {1234, "1234"},
                {12345, "12345"},
        };
    }


    @Test
    public void getAllByParametersWithoutLinearParameters() throws Exception {
        // TODO refactor getAllByParameters in ResourceService and rewrite this method

//        JPAQuery query = mock(JPAQuery.class, Mockito.RETURNS_DEEP_STUBS);
//
//        PowerMockito.whenNew(JPAQuery.class).withAnyArguments().thenReturn(query);
//        ResourseSearchJson parameters = mock(ResourseSearchJson.class, Mockito.RETURNS_SMART_NULLS);
//
//        serviceUnderTest.getAllByParameters(parameters);
//
//        verify(parameters, times(0)).getLinearParamsValues();


    }


}