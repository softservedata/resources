package org.registrator.community.service;

import org.mockito.*;
import org.powermock.api.support.membermodification.MemberModifier;
import org.registrator.community.dao.*;
import org.registrator.community.dto.JSON.PolygonJSON;
import org.registrator.community.dto.*;
import org.registrator.community.dto.JSON.ResourceSearchJSON;
import org.registrator.community.entity.*;
import org.registrator.community.service.impl.ResourceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Pageable;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.*;

import static java.util.Arrays.asList;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.*;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * Unit test for Resource service methods
 */

public class ResourceServiceTest   {
    // testing data
    private final String testTypeResource = "testTypeResource";
	private String validID = "0001";
    private String nonValidID = "0002";
    private Resource validResource;
    private ResourceDTO validResourceDTO;
    private ResourceAreaDTO validResourceAreaDTO;
    private User registrator;
    private String registratorLogin = "registrator";
    private Polygon validPolygon;
    private Random random = new Random(47);

    private static final double MIN_LAT = -90.0;
    private static final double MAX_LAT = 90.0;
    private static final double MIN_LON = -180.0;
    private static final double MAX_LON = 180.0;

    private static final String BEGIN = "begin test";
    private static final String END = "end test";


    @InjectMocks
    private ResourceService resourceService;
    
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
    private ResourceNumberRepository resourceNumberRepository;

    @Mock
    private PoligonAreaDTO validPolygonAreaDTO;

    @Mock
    private PointAreaDTO validPointAreaDTO;

    @Mock
    private ResourceFindByParams resourceFindByParams;

    private Logger logger;
	private String ownerLogin = "user";
    private LinearParameter linearParameter;
    private ResourceLinearValue linearValue;
    private ResourceDiscreteValue discreteValue;
    private DiscreteParameter discreteParameter;
    private ResourceType resourceTypeValidResource;

    private static final double MIN_LAT_TEST = 49.14506496754859;
    private static final double MAX_LAT_TEST = 49.91754184754319;
    private static final double MIN_LON_TEST = 24.21084593050182;
    private static final double MAX_LON_TEST = 26.249633822590113;
    private static final String COORDINATES_TEST =
            "[{'lat':49.91754184754319,'lng':24.21084593050182}," +
            "{'lat':49.9024188855169,'lng':26.249633822590113}," +
            "{'lat':49.158718087297515,'lng':26.2177737057209}," +
            "{'lat':49.14506496754859,'lng':24.739013705402613}]";

    @BeforeMethod
    public void initMocks () throws IllegalAccessException {
        resourceService = new ResourceServiceImpl();

        MockitoAnnotations.initMocks(this);

        // inject logger into tested service
        logger = LoggerFactory.getLogger("");
        MemberModifier
        	.field(resourceService.getClass(), "logger")
            .set(resourceService, logger);

        // mock dependencies of ResourceService
    	validResource = mock(Resource.class);
        validResourceDTO = mock(ResourceDTO.class, Mockito.RETURNS_SMART_NULLS);;
        validResourceAreaDTO = mock(ResourceAreaDTO.class, Mockito.RETURNS_SMART_NULLS);
        registrator = mock(User.class);

        validPolygon = new Polygon(
                MIN_LAT_TEST, MAX_LAT_TEST,
                MIN_LON_TEST, MAX_LON_TEST,
                validResource);
        // polygon with three dots
        validPolygon.setCoordinates(COORDINATES_TEST);

        resourceTypeValidResource = mock(ResourceType.class);
        ResourceDiscreteValueDTO discreteValueDTO = mock(ResourceDiscreteValueDTO.class);
        ResourceLinearValueDTO linearValueDTO = mock(ResourceLinearValueDTO.class);

    	when(validResource.getIdentifier()).thenReturn(validID);
        when(validResource.getRegistrator()).thenReturn(mock(User.class));
        when(validResource.getTome()).thenReturn(mock(Tome.class));

        when(validResource.getType()).thenReturn(resourceTypeValidResource);
        when(validResource.getDate()).thenReturn(new Date());

        when(resourceTypeValidResource.getTypeName()).thenReturn(testTypeResource);

        when(validResourceAreaDTO.getPoligons()).thenReturn(asList(validPolygonAreaDTO));

        when(validResourceDTO.getIdentifier()).thenReturn(validID);
        when(validResourceDTO.getResourceArea()).thenReturn(validResourceAreaDTO);
        when(validResourceDTO.getIdentifier()).thenReturn(validID);

        when(validResourceDTO.getResourceLinear()).thenReturn(asList(linearValueDTO));

        when(validResourceDTO.getResourceDiscrete()).thenReturn(asList(discreteValueDTO));

        when(registrator.getLogin()).thenReturn(registratorLogin);
        when(resourceRepository.save(validResource)).thenReturn(validResource);
        when(userRepository.findUserByLogin(registratorLogin)).thenReturn(registrator);
        when(polygonRepository.findByResource(validResource)).thenReturn(asList(validPolygon));

        linearParameter = mock(LinearParameter.class);
        when(linearParameterRepository.findByResourceType(same(resourceTypeValidResource)))
                .thenReturn(asList(linearParameter));
        when(linearParameter.getDescription()).thenReturn("linearParameterDescr");

        discreteParameter = mock(DiscreteParameter.class);
        when(discreteParameterRepository.findAllByResourceType(same(resourceTypeValidResource)))
                .thenReturn(asList(discreteParameter));
        when(discreteParameter.getDescription()).thenReturn("discreteParameterDescr");

        when(discreteValueDTO.getValueDiscretes()).thenReturn(asList(mock(ValueDiscreteDTO.class)));
        when(linearValueDTO.getSegments()).thenReturn(asList(mock(SegmentLinearDTO.class)));

        linearValue = mock(ResourceLinearValue.class);
        when(linearValue.getLinearParameter()).thenReturn(linearParameter);

        discreteValue = mock(ResourceDiscreteValue.class);
        when(discreteValue.getDiscreteParameter()).thenReturn(discreteParameter);
    }
    
    @Test
    public void findResourceByIdentifierValidId() {
        logger.debug(BEGIN);
        when(resourceRepository.findByIdentifier(validID)).thenReturn(validResource);

        ResourceLinearValue excludedLinear = mock(ResourceLinearValue.class, RETURNS_MOCKS);
        ResourceDiscreteValue excludedDiscreteValue = mock(ResourceDiscreteValue.class, RETURNS_MOCKS);
        DiscreteParameter excludedDiscreteParameter = mock(DiscreteParameter.class);
        when(excludedDiscreteParameter.getDiscreteParameterId()).thenReturn(1);
        when(discreteParameterRepository.findAllByResourceType(same(resourceTypeValidResource)))
                .thenReturn(asList(discreteParameter, excludedDiscreteParameter));

        when(linearValueRepository.findByResource(validResource)).thenReturn(asList(linearValue, excludedLinear));
        when(discreteValueRepository.findByResource(validResource)).thenReturn(asList(discreteValue, excludedDiscreteValue));

        ResourceDTO resourceDTO = resourceService.findByIdentifier(validID);
        assertEquals(resourceDTO.getIdentifier(), validID, "Find by valid ID should return valid ResourceDTO");
        logger.debug(END);
    }

    @Test
    public void findResourceByIdentifierNonValidId() {
        logger.debug(BEGIN);
        ResourceDTO resourceDTO = resourceService.findByIdentifier(nonValidID);

        assertTrue(resourceDTO == null, "Find by non valid ID should return null");
        logger.debug(END);
    }

    @Test
    public void findResourceByIdentifierNullId() {
        logger.debug(BEGIN);
        ResourceDTO resourceDTO = resourceService.findByIdentifier(null);
        assertTrue(resourceDTO == null, "Find by null should return null");
        logger.debug(END);
    }

    @Test(dependsOnMethods = {"findResourceByIdentifierValidId"})
    public void addNewResourceValid() {
        logger.debug(BEGIN);
    	// ResourceServiceImpl use findByIdentifier internally to return created resource
    	// stubbing this behavior
        resourceService = spy(resourceService);
        doReturn(validResourceDTO).when(resourceService).findByIdentifier(validID);
        
        when(resourceRepository.save(any(Resource.class))).thenReturn(validResource);
        when(validResourceDTO.getResourceLinear()).thenReturn(Collections.emptyList());
        when(validResourceDTO.getResourceDiscrete()).thenReturn(Collections.emptyList());

        String ownerLogin = "user";

        ResourceDTO result = resourceService.addNewResource(validResourceDTO, ownerLogin, registrator);
        assertEquals(result.getIdentifier(), validResourceDTO.getIdentifier(), "Creation of valid Resource should be successful");
        verify(inquiryService, times(1)).addInputInquiry(same(ownerLogin), any(), same(registrator));
        logger.debug(END);
    }

    @Test(dependsOnMethods = {"findResourceByIdentifierValidId", "addNewResourceValid"})
    @SuppressWarnings("unchecked")
    public void addNewResourceCheckSaveResourceParameters() {
        logger.debug(BEGIN);
        resourceService = spy(resourceService);
        doReturn(validResourceDTO).when(resourceService).findByIdentifier(validID);
        when(resourceRepository.save(any(Resource.class))).thenReturn(validResource);

        List<PointAreaDTO> listPoints = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            PointAreaDTO p = new PointAreaDTO();
            p.setOrderNumber(i);
            p.setLatitudeValues(randomDouble(MIN_LAT, MAX_LAT));
            p.setLongitudeValues(randomDouble(MIN_LON, MAX_LON));
            listPoints.add(p);
        }
        when(validPolygonAreaDTO.getPoints()).thenReturn(listPoints);

        ResourceDTO result = resourceService.addNewResource(validResourceDTO, ownerLogin, registrator);
        assertEquals(result.getIdentifier(), validResourceDTO.getIdentifier(), "Creation of valid Resource should be succesful");
        verify(validPolygonAreaDTO, atLeast(1)).getPoints();
        verify(polygonRepository, atLeast(1)).save(any(Polygon.class));
        verify(linearValueRepository, times(1)).save(any(List.class));
        verify(discreteValueRepository, times(1)).save(any(List.class));
        logger.debug(END);
    }

    @Test(dependsOnMethods = {"findResourceByIdentifierValidId", "addNewResourceValid"})
    public void addNewResourceEmptyOwner() {
        logger.debug(BEGIN);
        resourceService = spy(resourceService);
        doReturn(validResourceDTO).when(resourceService).findByIdentifier(validID);
        when(resourceRepository.save(any(Resource.class))).thenReturn(validResource);
        ownerLogin = "";
        ResourceDTO result = resourceService.addNewResource(validResourceDTO, ownerLogin, registrator);
        assertEquals(result.getIdentifier(), validResourceDTO.getIdentifier(), "Creation Resource with empty owner should be successful");
        verify(inquiryService, times(0)).addInputInquiry(same(ownerLogin), any(), same(registrator));
        logger.debug(END);
    }
    
    
    @Test(dependsOnMethods = {"findResourceByIdentifierValidId", "addNewResourceValid"})
    public void addNewResourceIncrementRegistrationNumber() {
        logger.debug(BEGIN);
        resourceService = spy(resourceService);
        doReturn(validResourceDTO).when(resourceService).findByIdentifier(validID);
        when(resourceRepository.save(any(Resource.class))).thenReturn(validResource);

        String ownerLogin = "";
        int number = 1;
        String registarionNumber = "";
        ResourceNumber resourceNumber = mock(ResourceNumber.class);
        when(resourceNumber.getNumber()).thenReturn(number);
        when(resourceNumber.getRegistratorNumber()).thenReturn(registarionNumber);

        when(resourceNumberRepository.findResourceNumberByUser(registrator)).thenReturn(resourceNumber);

        ResourceDTO result = resourceService.addNewResource(validResourceDTO, ownerLogin, registrator);
        assertEquals(result.getIdentifier(), validResourceDTO.getIdentifier(), "Creation Resource with empty owner should be successful");
        verify(inquiryService, times(0)).addInputInquiry(same(ownerLogin), any(), same(registrator));
        verify(resourceNumber, times(1)).setNumber(same(number + 1));
        logger.debug(END);
    }

    @Test
    public void count() {
        logger.debug(BEGIN);
    	long expected = 100L;
    	when (resourceRepository.count()).thenAnswer(invocation -> expected);
    	long actual = resourceService.count();
    	assertEquals(actual, expected);
        logger.debug(END);
    }

    @Test(dataProvider = "numbersToString")
    public void getRegistrationNumber(Integer number, String numberRepresentaion) {
        logger.debug(BEGIN);
        String registarionNumber = "test";
        ResourceNumber resourceNumber = mock(ResourceNumber.class);
        when(resourceNumber.getNumber()).thenReturn(number);
        when(resourceNumber.getRegistratorNumber()).thenReturn(registarionNumber);

        when(resourceNumberRepository.findResourceNumberByUser(registrator)).thenReturn(resourceNumber);

        String result = resourceService.getRegistrationNumber(registratorLogin);

        assertEquals(result, registarionNumber + numberRepresentaion);
        logger.debug(END);
    }


    @Test
    public void createPolygonJSON() {
        logger.debug(BEGIN);
        List<PolygonJSON> result = resourceService.createPolygonJSON(validResource, 0);

        assertEquals(result.size(), 1);
        PolygonJSON polygonJSON = result.get(0);
        assertEquals(polygonJSON.getResourceType(), testTypeResource);
        assertEquals(polygonJSON.getIdentifier(), validID);

        logger.debug(END);
    }

    @Test
    void findByTypeUseRepository() {
        logger.debug(BEGIN);
        ResourceType resourceType = mock(ResourceType.class);
        List<Resource> expected = asList(validResource);
        when(resourceRepository.findByType(resourceType)).thenReturn(expected);
        List<Resource> actual = resourceService.findByType(resourceType);
        assertEquals(actual, expected);
        verify(resourceRepository, times(1)).findByType(any());
        logger.debug(END);
    }

    @Test
    void getDescriptionBySearchTagUseRepository() {
        logger.debug(BEGIN);
        String queryParam = "testString";
        Set<String> expected = Collections.emptySet();
        when(resourceRepository.findDescriptionsLikeProposed(queryParam)).thenReturn(expected);
        Set<String> actual = resourceService.getDescriptionBySearchTag(queryParam);
        verify(resourceRepository, times(1)).findDescriptionsLikeProposed(queryParam);
        assertEquals(actual, expected);
        logger.debug(END);
    }


    @Test
    void getAllByAreaLimitsAllResources() {
        logger.debug(BEGIN);

        double minLat = randomDouble(MIN_LAT, MAX_LAT);
        double maxLat = randomDouble(minLat, MAX_LAT);
        double minLon = randomDouble(MIN_LON, MAX_LON);
        double maxLon = randomDouble(minLon, MAX_LON);

        when(polygonRepository.findByLimits(eq(minLat), eq(maxLat), eq(minLon), eq(maxLon), any()))
                .thenReturn(Arrays.asList(validPolygon));

        Set<Resource> expected = new HashSet<>();
        expected.add(validResource);

        Set<Resource> actual = resourceService.getAllByAreaLimits(minLat, maxLat, minLon, maxLon, "all", 1);

        verify(polygonRepository, times(1)).
        	findByLimits(eq(minLat), eq(maxLat), eq(minLon), eq(maxLon), any(Pageable.class));
        assertEquals(actual, expected);

        logger.debug(END);
    }

    @Test
    void getAllByAreaLimitsByResourceType() {

        logger.debug(BEGIN);
        double minLat = randomDouble(MIN_LAT, MAX_LAT);
        double maxLat = randomDouble(minLat, MAX_LAT);
        double minLon = randomDouble(MIN_LON, MAX_LON);
        double maxLon = randomDouble(minLon, MAX_LON);

        Polygon excludedPolygon = mock(Polygon.class, Mockito.RETURNS_DEEP_STUBS);
        
        when(polygonRepository.findByLimits(eq(minLat), eq(maxLat), eq(minLon), eq(maxLon), any()))
                .thenReturn(asList(validPolygon, excludedPolygon));

        Set<Resource> expected = new HashSet<>();
        expected.add(validResource);

        Set<Resource> actual =
                resourceService.getAllByAreaLimits(minLat, maxLat, minLon, maxLon, testTypeResource, 1);

        assertEquals(actual, expected);
        verify(polygonRepository, times(1)).
                findByLimits(eq(minLat), eq(maxLat), eq(minLon), eq(maxLon), any(Pageable.class));
        logger.debug(END);
    }


    @Test
    void getAllByPointUseRepository() {
        logger.debug(BEGIN);
        double lat = randomDouble(MIN_LAT, MAX_LAT);
        double lon = randomDouble(MIN_LON, MAX_LON);

        when(polygonRepository.findByPoint(eq(lat), eq(lon), any()))
                .thenReturn(asList(validPolygon));
        Set<Resource> expected = new HashSet<>();
        expected.add(validResource);

        Set<Resource> actual = resourceService.getAllByPoint(lat,lon, 0);

        assertEquals(actual, expected);
        verify(polygonRepository, times(1)).findByPoint(eq(lat), eq(lon), any(Pageable.class));
        logger.debug(END);
    }

    @Test
    void countAllByAreaLimitsUseRepository() {
        logger.debug(BEGIN);

        double minLat = randomDouble(MIN_LAT, MAX_LAT);
        double maxLat = randomDouble(minLat, MAX_LAT);
        double minLon = randomDouble(MIN_LON, MAX_LON);
        double maxLon = randomDouble(minLon, MAX_LON);

        Integer expected = 100;
        when(polygonRepository.countByLimits(minLat, maxLat, minLon, maxLon)).thenReturn(expected);
        Integer actual = resourceService.countAllByAreaLimits(minLat, maxLat, minLon, maxLon);
        assertEquals(actual, expected);
        verify(polygonRepository, times(1)).countByLimits(minLat, maxLat, minLon, maxLon);

        logger.debug(END);
    }

    @Test
    void countAllByPointUseRepository() {
        logger.debug(BEGIN);
        double lat = randomDouble(MIN_LAT, MAX_LAT);
        double lon = randomDouble(MIN_LON, MAX_LON);

        Integer expected = 100;
        when(polygonRepository.countByPoint(lat, lon)).thenReturn(expected);

        Integer actual = resourceService.countAllByPoint(lat,lon);

        assertEquals(actual, expected);
        verify(polygonRepository, times(1)).countByPoint(lat, lon);
        logger.debug(END);
    }

    @DataProvider
    private Object[][] numbersToString() {
        return new Object[][]{
                {1, "0001"},
                {1234, "1234"},
                {12345, "12345"},
        };
    }

    // generate bounded pseudorandom int, generated number include range bounds
    // one or two range bounds may be negative
    private int randomInt(int min, int max) {
        assert max > min;
        int maxNumber = max - min + 1;
        int number = random.nextInt(maxNumber);
        return number + min - 1;
    }

    // generate bounded pseudorandom double, generated number include range bounds
    // one or two range bounds may be negative
    private double randomDouble(double min, double max) {
        assert max > min;
        double maxNumber = max - min + 1;
        double number = random.nextDouble()*maxNumber;
        return number + min - 1.0;
    }


    @Test
    public void getAllByParameters() throws Exception {
        ResourceSearchJSON parameters = mock(ResourceSearchJSON.class);
        List<Resource> resources = mock(List.class);
        when(resourceFindByParams.findByParams(parameters)).thenReturn(resources);
        ParameterSearchResultDTO result = resourceService.getAllByParameters(parameters);

        assertEquals(result.getResources(), resources);

    }


}