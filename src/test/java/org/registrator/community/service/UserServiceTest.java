package org.registrator.community.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.registrator.community.dao.AddressRepository;
import org.registrator.community.dao.PassportRepository;
import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.RoleRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.UserRegistrationDTO;
import org.registrator.community.dto.json.ResourceNumberJson;
import org.registrator.community.dto.json.UserStatusJson;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.entity.WillDocument;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.service.impl.UserServiceImpl;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class UserServiceTest {
	private static final Logger log = LoggerFactory.getLogger(UserServiceTest.class);

	@InjectMocks
	private static UserService userService = new UserServiceImpl();
	@Mock
	private static UserRepository userRepository;
	@Mock
	private static Logger logger;
	@Mock
	private static RoleRepository roleRepository;
	@Mock
	private static CommunityService communityService;
	@Mock
	private static PassportRepository passportRepository;
	@Mock
	private static AddressRepository addressRepository;
	@Mock
	private static ResourceNumberRepository resourceNumberRepository;
	@Mock
	private static TomeRepository tomeRepository;
	@Mock
	private static PasswordEncoder userPasswordEncoder;

	// TestData
	static TerritorialCommunity communityUkraine, communityLviv;
	static final int REGISTERED_USER_COUNT = 4;
	static User user, registrator, commissioner, admin;

	@BeforeMethod
	public void init() {
		MockitoAnnotations.initMocks(this);
		// initialize test data before each method
		communityUkraine = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		communityLviv = new TerritorialCommunity();
		communityLviv.setName("Львів");

		user = new User("user", "user", new Role(RoleType.USER, "description"), "Василь", "Труба", "Георгійович",
				"vasul.tryba@gmail.com", UserStatus.ACTIVE.toString(), "09800000002");
		user.setTerritorialCommunity(communityUkraine);
		user.setAddress(Arrays
				.asList(new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31")));
		user.setPassport(Arrays.asList(new PassportInfo(user, "AA", "00000", "Народом України")));

		registrator = new User("registrator", "registrator", new Role(RoleType.REGISTRATOR, "description"), "Володимир",
				"Сидор", "Тарасович", "sydor@gmail.com", UserStatus.ACTIVE.toString(), "09500000003");
		registrator.setTerritorialCommunity(communityUkraine);
		registrator.setAddress(Arrays.asList(
				new Address(registrator, "11111", "Львівська", "Личаківський", "Львів", "Княгині Ольги", "21", "12")));
		registrator.setPassport(Arrays.asList(new PassportInfo(registrator, "ББ", "11111", "Народом України")));

		commissioner = new User("commissioner", "commissioner", new Role(RoleType.COMMISSIONER, "description"),
				"Віталій", "Гонгадзе", "Юрійович", "gongadze@gmail.com", UserStatus.ACTIVE.toString(), "09300000004");
		commissioner.setTerritorialCommunity(communityUkraine);
		commissioner.setAddress(Arrays.asList(
				new Address(commissioner, "22222", "Київська", "Шевченківський", "Київ", "Т.Г.Шевченка", "13", "31")));
		commissioner.setPassport(Arrays.asList(new PassportInfo(commissioner, "ВВ", "22222", "Народом України")));

		admin = new User("admin", "admin", new Role(RoleType.ADMIN, "description"), "Назар", "Вітер", "Романович",
				"viter@gmail.com", UserStatus.ACTIVE.toString(), "06700000005");
		admin.setTerritorialCommunity(communityUkraine);
		admin.setAddress(Arrays
				.asList(new Address(admin, "33333", "Київська", "Шевченківський", "Київ", "Т.Г.Шевченка", "44", "66")));
		admin.setPassport(Arrays.asList(new PassportInfo(admin, "ГГ", "33333", "Народом України")));
	}
	

	/**
	 * Unit Test of login functionality
	 */
	@Test(dataProvider="providerTestLogin")
	public void login(String login, String password, boolean isPositive) {
		log.info(String.format("Test login, with next credentials: L:'%s' P:'%s'",login,password));
		// mocks
		mockUserRepository();
		// test action
		Assert.assertEquals(userService.login(login, password), isPositive);// correct
	}

	/**
	 * Unit Test of getting user by login functionality
	 */
	@Test(dataProvider="providerUserLogin")
	public void getUserByLogin(String login, boolean isPositive) {
		// mocks
		mockUserRepository();
		// test action
		log.info(String.format("login: %s %s", login, isPositive));
		if(isPositive)
			Assert.assertNotNull(userService.getUserByLogin(login));// correct data
		else
			Assert.assertNull(userService.getUserByLogin(login));// incorrect data
	}

	/**
	 * Unit Test of finding user by login functionality (duplicate method of
	 * previous one 'getUserByLogin')
	 */
	@Test
	public void findUserByLogin() {
		// mocks
		mockUserRepository();
		// test action
		log.info("login: user");
		Assert.assertNotNull(userService.findUserByLogin("user"));// correct  data
		log.info("login: badlogin");
		Assert.assertNull(userService.findUserByLogin("badlogin"));// incorrect data
	}

	/**
	 * Unit Test of changing user status functionality
	 */
	@Test(dataProvider = "providerUserStatus")
	public void changeUserStatus(UserStatus status) {
		// test data
		UserStatusJson userStatusDto = new UserStatusJson(user.getLogin(), status.toString());
		// mocks
		mockUserRepository();
		// test action
		log.info(String.format("login: '%s', status: '%s'", user.getLogin(), status.toString()));
		userService.changeUserStatus(userStatusDto);
		Assert.assertEquals(user.getStatus(), status);// correct data
	}

	/**
	 * Unit Test of changing user role functionality NOTE: [1]
	 * expected.ordinal()+1 : because here are used ID (NOT NULL)
	 */
	@Test(dependsOnMethods = { "getUserByLogin" }, dataProvider = "providerUserRole")
	public void changeUserRole(String login, RoleType roleType) {
		// test data
		Role role = new Role(roleType, "description");
		// mocks
		mockRoleRepository(role);
		mockUserRepository();
		// test action
		userService.changeUserRole("user", roleType.ordinal() + 1);// [1]
		Assert.assertEquals(user.getRole().getType(), roleType);
	}

	/**
	 * Unit Test of editing user information functionality
	 */
	@Test(dependsOnMethods = { "getUserByLogin" })
	public void editUserInformation() {
		// test data
		final UserDTO expected = new UserDTO("Петро", "Замоканий", "Афанасійович", RoleType.REGISTRATOR.toString(),
				"user",
				"petro@gmail.com", UserStatus.BLOCK.toString(), new AddressDTO("11111", "Івано-Франківська",
						"Шевченківський", "Івано-Франківськ", "Зелена", "21", "12"),
				new PassportDTO("ББ", "22222", "Народом України2"));// login
																	// 'user'
																	// important!
		Address address = new Address(user, expected.getAddress().getPostcode(), expected.getAddress().getRegion(),
				expected.getAddress().getDistrict(), expected.getAddress().getCity(), expected.getAddress().getStreet(),
				expected.getAddress().getBuilding(), expected.getAddress().getFlat());
		PassportInfo passport = new PassportInfo(user, expected.getPassport().getSeria(),
				expected.getPassport().getNumber(), expected.getPassport().getPublished_by_data());
		passport.setComment("comment");
		expected.setTerritorialCommunity(communityLviv.getName());
		// mocks
		mockRoleRepository(null);
		mockCommunityService();
		Mockito.when(addressRepository.save(address)).thenReturn(address);
		Mockito.when(passportRepository.save(passport)).thenReturn(passport);
		mockUserRepository();
		// test action
		UserDTO userDto = userService.editUserInformation(expected);
		assertEqualsUsers(userDto, expected);//compares
		Mockito.when(userRepository.findUserByLogin("user")).thenReturn(null);
		Assert.assertNull(userService.editUserInformation(expected));//

	}

	/**
	 * Unit Test of getting all statuses for registered users functionality
	 */
	@Test
	public void fillInUserStatusforRegistratedUsers() {
		// test data
		final List<UserStatus> expected = Arrays.asList(UserStatus.ACTIVE, UserStatus.BLOCK);
		// test action
		List<UserStatus> actual = userService.fillInUserStatusforRegistratedUsers();
		Assert.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
	}

	/**
	 * Unit Test of getting all statuses for inactive users functionality
	 */
	@Test
	public void fillInUserStatusforInactiveUsers() {
		// test data
		final List<UserStatus> expected = Arrays.asList(UserStatus.ACTIVE, UserStatus.BLOCK, UserStatus.INACTIVE);
		// test action
		List<UserStatus> actual = userService.fillInUserStatusforInactiveUsers();
		Assert.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
	}

	/**
	 * Unit Test of getting complex information about all users functionality
	 */
	@Test
	public void getUserDtoList() {
		// mocks
		mockUserRepository();
		// test action
		List<UserDTO> actual = userService.getUserDtoList();
		Assert.assertEquals(actual.size(), REGISTERED_USER_COUNT, "Bad size of List<UserDTO>");
		for (UserDTO userDto : actual) {
			Assert.assertNotNull(userDto, "One of returned user is null");
		}

	}

	/**
	 * Unit Test of getting complex information about user functionality
	 */
	@Test(dependsOnMethods = { "getUserByLogin" })
	public void getUserDto() {
		// test data
		PassportDTO passportDto = new PassportDTO(user.getPassport().get(user.getPassport().size() - 1).getSeria(),
				user.getPassport().get(user.getPassport().size() - 1).getNumber(),
				user.getPassport().get(user.getPassport().size() - 1).getPublishedByData());
		passportDto.setComment("comment");
		user.getPassport().get(0).setComment("comment");
		AddressDTO addressDto = new AddressDTO(user.getAddress().get(user.getAddress().size() - 1).getPostCode(),
				user.getAddress().get(user.getAddress().size() - 1).getRegion(),
				user.getAddress().get(user.getAddress().size() - 1).getDistrict(),
				user.getAddress().get(user.getAddress().size() - 1).getCity(),
				user.getAddress().get(user.getAddress().size() - 1).getStreet(),
				user.getAddress().get(user.getAddress().size() - 1).getBuilding(),
				user.getAddress().get(user.getAddress().size() - 1).getFlat());
		final UserDTO expected = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
				user.getRole().getType().toString(), user.getLogin(), user.getEmail(), user.getStatus().toString(),
				addressDto, passportDto);
		// mocks
		mockUserRepository();
		// test action
		UserDTO actual = userService.getUserDto("user");
		assertEqualsUsers(actual, expected);
		
		// other branches
		Tome tom = new Tome(user, "Парк Погулянка");
		ResourceNumber resourceNumber = new ResourceNumber(0, "0", user);
		WillDocument willDocument = new WillDocument();
		willDocument.setComment("comment");
		user.setWillDocument(Arrays.asList(willDocument));
		Mockito.when(tomeRepository.findTomeByRegistrator(Mockito.any(User.class))).thenReturn(tom);
		Mockito.when(resourceNumberRepository.findResourceNumberByUser(Mockito.any())).thenReturn(resourceNumber);
		Assert.assertNotNull(userService.getUserDto("user"));
	
	}

	/**
	 * Unit Test of getting all registered users functionality
	 */
	@Test(dependsOnMethods = { "getUserDtoList", "fillInUserStatusforRegistratedUsers" })
	public void getAllRegistratedUsers() {
		// mocks
		mockUserRepository();
		// get available statuses for registered users
		List<UserStatus> statuses = userService.fillInUserStatusforRegistratedUsers();
		// test action
		List<UserDTO> actual = userService.getAllRegistratedUsers();
		Assert.assertEquals(actual.size(), REGISTERED_USER_COUNT, "Bad size of List<UserDTO>");
		for (UserDTO userDto : actual) {
			UserStatus a = checkUserStatus(userDto.getStatus());
			Assert.assertTrue(statuses.contains(a));
		}
	}

	/**
	 * Unit Test of getting all inactive users functionality
	 */
	@Test(dependsOnMethods = { "getUserDtoList"  })
	public void getAllInactiveUsers() {
		// prepare data
		final int INACTIVE_USERS = 2;
		user.setStatus(UserStatus.INACTIVE);
		commissioner.setStatus(UserStatus.INACTIVE);
		// mocks
		mockUserRepository();
		// test action
		List<UserDTO> actual = userService.getAllInactiveUsers();
		Assert.assertEquals(actual.size(), INACTIVE_USERS, "Bad size of List<UserDTO>");
		for (UserDTO userDto : actual) {
			Assert.assertEquals(userDto.getStatus(), UserStatus.INACTIVE.name());
		}
	}

	/**
	 * Unit Test of check user on existing functionality
	 */
	@Test
	public void checkUsernameNotExistInDB() {
		// mocks
		mockUserRepository();
		// test action
		Assert.assertFalse(userService.checkUsernameNotExistInDB("user"));// exist
		Assert.assertTrue(userService.checkUsernameNotExistInDB("notexsist"));// not exist
	}

	/**
	 * Unit Test of getting user by search tag(last name *) functionality
	 */
	@Test(dependsOnMethods = { "getUserByLogin" })
	public void getUserBySearchTag() {
		// mocks
		mockUserRepository();
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken("registrator", "registrator"));
		Mockito.when(userRepository.findOwnersLikeProposed(communityUkraine, user.getLastName()))
				.thenReturn(Arrays.asList(user));
		// test action
		List<UserDTO> actual = userService.getUserBySearchTag("Труба");
		Assert.assertEquals(actual.get(0).getLogin(), user.getLogin());
	}

	/**
	 * Unit Test of getting user by email functionality
	 */
	@Test
	public void findUserByEmail() {
		// mocks
		mockUserRepository();
		// test action
		User actual = userService.findUserByEmail("vasul.tryba@gmail.com");
		Assert.assertNotNull(actual);
	}

	/**
	 * Unit Test of incrementing fail attempts functionality
	 */
	@Test
	public void updateFailAttempts() {
		// test data
		int expected = user.getAttempts()+1;
		// mocks
		mockUserRepository();
		// test action
		userService.updateFailAttempts("user");
		Assert.assertEquals(user.getAttempts(), expected);
		
		user.setAttempts(2);
		userService.updateFailAttempts("user");
	}

	/**
	 * Unit Test of reseting fail attempts functionality
	 */
	@Test
	public void resetFailAttempts() {
		// test data
		user.setAttempts(2);
		int expected = 0;
		// mocks
		mockUserRepository();
		// test action
		userService.resetFailAttempts("user");
		Assert.assertEquals(user.getAttempts(), expected);
	}
	
	/**
	 * Unit Test of incrementing fail attempts functionality
	 */
	@Test
	public void resetAllFailAttempts() {
		// test data
		user.setAttempts(2);
		admin.setAttempts(1);
		int expected = 0;
		// mocks
		mockUserRepository();
		// test action
		userService.resetAllFailAttempts();
		Assert.assertEquals(user.getAttempts(), expected);
		Assert.assertEquals(admin.getAttempts(), expected);
	}

	/**
	 * Unit Test of getting all inactive users functionality
	 */
	@Test(dependsOnMethods = { "findUserByLogin" })
	public void registerUser() {
		// test data
		UserRegistrationDTO registrationForm = new UserRegistrationDTO();
		registrationForm.setAddress(new AddressDTO("06060","Львівська","Галицький","Львів","Вітовського",
        		"55","22"));
		registrationForm.setConfirmPassword("pikaso1");
		registrationForm.setDateOfAccession(Calendar.getInstance().getTime());
		registrationForm.setEmail("pikaso@gmail.com");
		registrationForm.setFirstName("Іван");
		registrationForm.setLastName("Сидор");
		registrationForm.setLogin("pikaso1");
		registrationForm.setMiddleName("Сергійович");
		registrationForm.setPassport(new PassportDTO("AA","00600","Народом України"));
		registrationForm.setPassword("pikaso");
		registrationForm.setPhoneNumber("0950000000");
		registrationForm.setTerritorialCommunity("Україна");
		
		List<User> fakeUserRepo = new ArrayList<User>();
		List<PassportInfo> fakePassportRepo = new ArrayList<PassportInfo>();
		List<Address> fakeAddressRepo = new ArrayList<Address>();
		// mocks
		Mockito.when(communityService.findByName(registrationForm.getTerritorialCommunity()))
				.thenReturn(communityUkraine);
		Mockito.when(userPasswordEncoder.encode(registrationForm.getPassword()))
				.thenReturn(registrationForm.getPassword());
		Mockito.when(roleRepository.findRoleByType(RoleType.USER)).thenReturn(new Role(RoleType.USER, "description"));
		Mockito.when(userRepository.save(Mockito.any(User.class))).then(new Answer<User>(){

			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				fakeUserRepo.add(invocation.getArgumentAt(0, User.class));
				return null;
			}
			
		});
		Mockito.when(userRepository.findUserByLogin(registrationForm.getLogin())).then(new Answer<User>(){

			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				// TODO Auto-generated method stub
				return fakeUserRepo.get(0);
			}
			
		});
		Mockito.when(passportRepository.save(Mockito.any(PassportInfo.class))).then(new Answer<PassportInfo>(){

			@Override
			public PassportInfo answer(InvocationOnMock invocation) throws Throwable {
				fakePassportRepo.add(invocation.getArgumentAt(0, PassportInfo.class));
				return null;
			}
			
		});
		Mockito.when(addressRepository.save(Mockito.any(Address.class))).then(new Answer<Address>(){

			@Override
			public Address answer(InvocationOnMock invocation) throws Throwable {
				fakeAddressRepo.add(invocation.getArgumentAt(0, Address.class));
				return null;
			}
			
		});
		// test action
		userService.registerUser(registrationForm);
		Assert.assertEquals(fakeUserRepo.get(0).getLogin(), registrationForm.getLogin());
		Assert.assertEquals(fakeAddressRepo.get(0).getStreet(), registrationForm.getAddress().getStreet());
		Assert.assertEquals(fakePassportRepo.get(0).getNumber(), registrationForm.getPassport().getNumber());
	}

	/**
	 * Unit Test of functionality create tome, create resource number
	 */
	@Test
	public void CreateTomeAndRecourceNumber() {
		// test data
		UserDTO userDto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
				user.getRole().getType().toString(), user.getLogin(), user.getEmail(), user.getStatus().toString(),
				new AddressDTO(user.getAddress().get(user.getAddress().size() - 1).getPostCode(),
						user.getAddress().get(user.getAddress().size() - 1).getRegion(),
						user.getAddress().get(user.getAddress().size() - 1).getDistrict(),
						user.getAddress().get(user.getAddress().size() - 1).getCity(),
						user.getAddress().get(user.getAddress().size() - 1).getStreet(),
						user.getAddress().get(user.getAddress().size() - 1).getBuilding(),
						user.getAddress().get(user.getAddress().size() - 1).getFlat()),
				new PassportDTO(user.getPassport().get(user.getPassport().size() - 1).getSeria(),
						user.getPassport().get(user.getPassport().size() - 1).getNumber(),
						user.getPassport().get(user.getPassport().size() - 1).getPublishedByData()));
		ResourceNumberJson resourceNumberDtoJson = new ResourceNumberJson("1", "1", "1");
		resourceNumberDtoJson = Mockito.spy(resourceNumberDtoJson);
		userDto.setResourceNumberJson(resourceNumberDtoJson);
		// mocks
		List<Tome> fakeTomeRepo = new ArrayList<Tome>();
		mockUserRepository();
		Mockito.when(tomeRepository.save(Mockito.any(Tome.class))).then(new Answer<Tome>() {

			@Override
			public Tome answer(InvocationOnMock invocation) throws Throwable {
				fakeTomeRepo.add(invocation.getArgumentAt(0, Tome.class));
				return null;
			}

		});
		Mockito.when(resourceNumberDtoJson.getLogin()).thenReturn(user.getLogin());
		// test action
		userService.CreateTomeAndRecourceNumber(userDto);// entered
		Assert.assertNotNull(fakeTomeRepo.get(0));
		
		//fakeTomeRepo.clear();
		//userDto.setResourceNumberJson(null);
		//userService.CreateTomeAndRecourceNumber(userDto);// exception
		//Assert.assertTrue(fakeTomeRepo.isEmpty());
	}

	private UserStatus checkUserStatus(String status) {
		if (status.equals(UserStatus.BLOCK.name())) {
			return UserStatus.BLOCK;
		} else if (status.equals(UserStatus.INACTIVE.name())) {
			return UserStatus.INACTIVE;
		} else {
			return UserStatus.ACTIVE;
		}
	}

	private void mockUserRepository() {
		Mockito.when(userRepository.getUserByLoginAndPassword(user.getLogin(), user.getPassword())).thenReturn(user);
		Mockito.when(userRepository.getUserByLoginAndPassword(registrator.getLogin(), registrator.getPassword()))
				.thenReturn(registrator);
		Mockito.when(userRepository.getUserByLoginAndPassword(commissioner.getLogin(), commissioner.getPassword()))
				.thenReturn(commissioner);
		Mockito.when(userRepository.getUserByLoginAndPassword(admin.getLogin(), admin.getPassword())).thenReturn(admin);
		Mockito.when(userRepository.findUserByLogin(user.getLogin())).thenReturn(user);
		Mockito.when(userRepository.findUserByLogin(registrator.getLogin())).thenReturn(registrator);
		Mockito.when(userRepository.findUserByLogin(commissioner.getLogin())).thenReturn(commissioner);
		Mockito.when(userRepository.findUserByLogin(admin.getLogin())).thenReturn(admin);
		Mockito.when(userRepository.getUserByEmail(user.getEmail())).thenReturn(user);
		Mockito.when(userRepository.getUserByEmail(registrator.getEmail())).thenReturn(registrator);
		Mockito.when(userRepository.getUserByEmail(commissioner.getEmail())).thenReturn(commissioner);
		Mockito.when(userRepository.getUserByEmail(admin.getEmail())).thenReturn(admin);
		Mockito.when(userRepository.save(user)).thenReturn(user);
		Mockito.when(userRepository.save(registrator)).thenReturn(registrator);
		Mockito.when(userRepository.save(commissioner)).thenReturn(commissioner);
		Mockito.when(userRepository.save(admin)).thenReturn(admin);
		Mockito.when(userRepository.findAll()).thenReturn(Arrays.asList(user, registrator, commissioner, admin));
	}

	private void mockRoleRepository(Role role) {
		Mockito.when(roleRepository.findOne(String.valueOf(RoleType.ADMIN.ordinal() + 1))).thenReturn(role);// [1]
		Mockito.when(roleRepository.findOne(String.valueOf(RoleType.USER.ordinal() + 1))).thenReturn(role);// [1]
		Mockito.when(roleRepository.findOne(String.valueOf(RoleType.REGISTRATOR.ordinal() + 1))).thenReturn(role);// [1]
		Mockito.when(roleRepository.findOne(String.valueOf(RoleType.COMMISSIONER.ordinal() + 1))).thenReturn(role);// [1]
		Mockito.when(roleRepository.findAll())
				.thenReturn(Arrays.asList(new Role(RoleType.USER, "description"),
						new Role(RoleType.ADMIN, "description"), new Role(RoleType.REGISTRATOR, "description"),
						new Role(RoleType.COMMISSIONER, "description")));
	}

	private void mockCommunityService() {
		Mockito.when(communityService.findByName(communityUkraine.getName())).thenReturn(communityUkraine);
		Mockito.when(communityService.findByName(communityLviv.getName())).thenReturn(communityLviv);
	}

	private void assertEqualsUsers(UserDTO actual, UserDTO expected) {
		Assert.assertEquals(actual.getLogin(), expected.getLogin());
		Assert.assertEquals(actual.getEmail(), expected.getEmail());
		Assert.assertEquals(actual.getFirstName(), expected.getFirstName());
		Assert.assertEquals(actual.getLastName(), expected.getLastName());
		Assert.assertEquals(actual.getMiddleName(), expected.getMiddleName());
		Assert.assertEquals(actual.getPhoneNumber(), expected.getPhoneNumber());
		Assert.assertEquals(actual.getRole(), expected.getRole());
		Assert.assertEquals(actual.getStatus(), expected.getStatus());
		Assert.assertEquals(actual.getTerritorialCommunity(), expected.getTerritorialCommunity());
		Assert.assertEquals(actual.getAddress().getBuilding(), expected.getAddress().getBuilding());
		Assert.assertEquals(actual.getAddress().getCity(), expected.getAddress().getCity());
		Assert.assertEquals(actual.getAddress().getDistrict(), expected.getAddress().getDistrict());
		Assert.assertEquals(actual.getAddress().getFlat(), expected.getAddress().getFlat());
		Assert.assertEquals(actual.getAddress().getPostcode(), expected.getAddress().getPostcode());
		Assert.assertEquals(actual.getAddress().getRegion(), expected.getAddress().getRegion());
		Assert.assertEquals(actual.getAddress().getStreet(), expected.getAddress().getStreet());
		Assert.assertEquals(actual.getPassport().getNumber(), expected.getPassport().getNumber());
		Assert.assertEquals(actual.getPassport().getPublished_by_data(), expected.getPassport().getPublished_by_data());
		Assert.assertEquals(actual.getPassport().getSeria(), expected.getPassport().getSeria());
	}
	
	/**
	 * Data for UserService.providerTestLogin();
	 * positive & negative
	 */
	@DataProvider
	public static Object[][] providerTestLogin() {
        return new Object[][] { 
        	{ "user", "user", true } ,
        	{ "admin","somepass", false } ,
        	{ "badlogin","registrator", false }
        	};
    }
	
	/**
	 * Data for UserService.testGetUserByLogin();
	 * positive & negative
	 */
	@DataProvider
	public static Object[][] providerUserLogin() {
        return new Object[][] {     
                 {"user", true}, {"wrong",false}            
           };
    }
	
	/**
	 * Data for UserService.testChangeUserStatus();
	 * positive
	 */
	@DataProvider
	public static Object[][] providerUserStatus() {
        return new Object[][] {     
                 { UserStatus.BLOCK }, 
                 { UserStatus.INACTIVE },
                 { UserStatus.ACTIVE }, 
                 { UserStatus.INACTIVE },
                 { UserStatus.BLOCK },
                 { UserStatus.ACTIVE },
           };
    }
	
	/**
	 * Data for UserService.testChangeUserRole();
	 * positive
	 */
	@DataProvider
	public static Object[][] providerUserRole() {
        return new Object[][] {     
        	//Role: user
            { "user", RoleType.ADMIN },
            { "user", RoleType.REGISTRATOR },
            { "user", RoleType.COMMISSIONER },
            { "user", RoleType.USER }
           };
    }
}
