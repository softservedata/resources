package org.registrator.community.service;

import java.util.ArrayList;
import java.util.Arrays;
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
import org.registrator.community.service.CommunityService;
import org.registrator.community.service.UserService;
import org.registrator.community.service.impl.UserServiceImpl;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
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
	
	private static final String START = "Start";
	private static final String END = "End";

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
	
	private List<User> fakeUserRepository = new ArrayList<User>();
	private void mockUserRepository(){
		//Mock our UserRepository
		Mockito.when(userRepository.findUserByLogin(Mockito.anyString())).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				for(int i=0;i<fakeUserRepository.size();i++)
					if(fakeUserRepository.get(i).getLogin()==invocation.getArgumentAt(0, String.class))
						return fakeUserRepository.get(i);
				return null;
			}
		});
		Mockito.when(userRepository.getUserByEmail(Mockito.anyString())).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				for(int i=0;i<fakeUserRepository.size();i++)
					if(fakeUserRepository.get(i).getEmail()==invocation.getArgumentAt(0, String.class))
						return fakeUserRepository.get(i);
				return null;
			}
		});
		Mockito.when(userRepository.getUserByLoginAndPassword(Mockito.anyString(), Mockito.anyString())).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				for(int i=0;i<fakeUserRepository.size();i++)
					if(fakeUserRepository.get(i).getLogin()==invocation.getArgumentAt(0, String.class))
						if(fakeUserRepository.get(i).getPassword()==invocation.getArgumentAt(1, String.class))
							return fakeUserRepository.get(i);
				return null;
			}
		});
		Mockito.when(userRepository.save(Mockito.any(User.class))).then(new Answer<User>() {
			@Override
			public User answer(InvocationOnMock invocation) throws Throwable {
				fakeUserRepository.add(invocation.getArgumentAt(0, User.class));
				return invocation.getArgumentAt(0, User.class);
			}
		});
		Mockito.when(userRepository.findOwnersLikeProposed(Mockito.any(TerritorialCommunity.class), Mockito.anyString())).then(new Answer<List<User>>() {
			@Override
			public List<User> answer(InvocationOnMock invocation) throws Throwable {
				List<User> users = new ArrayList<User>();
				for(User user: fakeUserRepository){
					if(user.getTerritorialCommunity().getName()==invocation.getArgumentAt(0, TerritorialCommunity.class).getName())
						if(user.getLastName().contains(invocation.getArgumentAt(1, String.class)))
							users.add(user);
				}
				return users;
			}
		});
		Mockito.when(userRepository.findAll()).thenReturn(fakeUserRepository);
	}
	
	private List<Role> fakeRoleRepository = new ArrayList<Role>();
	private void mockRoleRepository(){
		Mockito.when(roleRepository.findOne(Mockito.anyString())).then(new Answer<Role>() {
			@Override
			public Role answer(InvocationOnMock invocation) throws Throwable {
				if(Integer.parseInt(invocation.getArgumentAt(0, String.class))<fakeRoleRepository.size())
					return fakeRoleRepository.get(Integer.parseInt(invocation.getArgumentAt(0, String.class)));
				return null;
			}
		});
		Mockito.when(roleRepository.findAll()).thenReturn(fakeRoleRepository);
	}
	
	private List<TerritorialCommunity> fakeCommunityRepository = new ArrayList<TerritorialCommunity>();
	private void mockCommunityRepository(){
		Mockito.when(communityService.findByName(Mockito.anyString())).then(new Answer<TerritorialCommunity>() {
			@Override
			public TerritorialCommunity answer(InvocationOnMock invocation) throws Throwable {
				for(int i=0;i<fakeCommunityRepository.size();i++)
					if(fakeCommunityRepository.get(i).getName()==invocation.getArgumentAt(0, String.class))
						return fakeCommunityRepository.get(i);
				return null;
			}
		});
	}
	
	private List<Address> fakeAddressRepository = new ArrayList<Address>();
	private void mockAddressRepository(){
		Mockito.when(addressRepository.save(Mockito.any(Address.class))).then(new Answer<Address>() {
			@Override
			public Address answer(InvocationOnMock invocation) throws Throwable {
				fakeAddressRepository.add(invocation.getArgumentAt(0, Address.class));
				return invocation.getArgumentAt(0, Address.class);
			}
		});
	}
	
	private List<PassportInfo> fakePassportRepository = new ArrayList<PassportInfo>();
	private void mockPassportRepository(){
		Mockito.when(passportRepository.save(Mockito.any(PassportInfo.class))).then(new Answer<PassportInfo>() {
			@Override
			public PassportInfo answer(InvocationOnMock invocation) throws Throwable {
				fakePassportRepository.add(invocation.getArgumentAt(0, PassportInfo.class));
				return invocation.getArgumentAt(0, PassportInfo.class);
			}
		});
	}

	@BeforeMethod
	public void init() {
		logger = log;//give job to our real logger
		MockitoAnnotations.initMocks(this);
		// initialize test data before each method
		Role roleUser = new Role(RoleType.USER, "description");
		Role roleRegistrator = new Role(RoleType.REGISTRATOR, "description");
		Role roleCommissioner = new Role(RoleType.COMMISSIONER, "description");
		Role roleAdmin = new Role(RoleType.ADMIN, "description");
		fakeRoleRepository.add(roleUser);//id = 0
		fakeRoleRepository.add(roleRegistrator);//id = 1
		fakeRoleRepository.add(roleAdmin);//id = 2
		fakeRoleRepository.add(roleCommissioner);//id = 3
		
		TerritorialCommunity communityUkraine = new TerritorialCommunity();
		TerritorialCommunity communityLviv = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		communityLviv.setName("Львів");
		fakeCommunityRepository.add(communityUkraine);
		fakeCommunityRepository.add(communityLviv);

		User user = new User("user", "user", roleUser, "Василь", "Труба", "Георгійович",
				"vasul.tryba@gmail.com", UserStatus.ACTIVE.toString(), "09800000002");
		User registrator = new User("registrator", "registrator", roleRegistrator, "Володимир",
				"Сидор", "Тарасович", "sydor@gmail.com", UserStatus.ACTIVE.toString(), "09500000003");
		User commissioner = new User("commissioner", "commissioner", roleCommissioner,
				"Віталій", "Гонгадзе", "Юрійович", "gongadze@gmail.com", UserStatus.ACTIVE.toString(), "09300000004");
		User admin = new User("admin", "admin", roleAdmin, "Назар", "Вітер", "Романович",
				"viter@gmail.com", UserStatus.ACTIVE.toString(), "06700000005");
		fakeUserRepository.add(user);
		fakeUserRepository.add(registrator);
		fakeUserRepository.add(commissioner);
		fakeUserRepository.add(admin);
		
		Address userAddress = new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31");
		Address registratorAddress = new Address(registrator, "11111", "Львівська", "Личаківський", "Львів", "Княгині Ольги", "21", "12");
		Address commissionerAddress = new Address(commissioner, "22222", "Київська", "Шевченківський", "Київ", "Т.Г.Шевченка", "13", "31");
		Address adminAddress = new Address(admin, "33333", "Київська", "Шевченківський", "Київ", "Т.Г.Шевченка", "44", "66");
		user.setAddress(Arrays.asList(userAddress));
		registrator.setAddress(Arrays.asList(registratorAddress));
		commissioner.setAddress(Arrays.asList(commissionerAddress));
		admin.setAddress(Arrays.asList(adminAddress));
		fakeAddressRepository.add(userAddress);
		fakeAddressRepository.add(registratorAddress);
		fakeAddressRepository.add(commissionerAddress);
		fakeAddressRepository.add(adminAddress);
		

		user.setTerritorialCommunity(communityUkraine);
		registrator.setTerritorialCommunity(communityUkraine);
		commissioner.setTerritorialCommunity(communityUkraine);
		admin.setTerritorialCommunity(communityUkraine);
		
		
		PassportInfo userPassport = new PassportInfo(user, "AA", "00000", "Народом України");
		PassportInfo registratorPassport = new PassportInfo(registrator, "ББ", "11111", "Народом України");
		PassportInfo commissionerPassport = new PassportInfo(commissioner, "ВВ", "22222", "Народом України");
		PassportInfo adminPassport = new PassportInfo(admin, "ГГ", "33333", "Народом України");
		user.setPassport(Arrays.asList(userPassport));
		registrator.setPassport(Arrays.asList(registratorPassport));
		commissioner.setPassport(Arrays.asList(commissionerPassport));
		admin.setPassport(Arrays.asList(adminPassport));
		fakePassportRepository.add(userPassport);
		fakePassportRepository.add(registratorPassport);
		fakePassportRepository.add(commissionerPassport);
		fakePassportRepository.add(adminPassport);
		
		
		user.setUserId(1);
		registrator.setUserId(2);
		admin.setUserId(3);
		commissioner.setUserId(4);
		
		

		mockUserRepository();
		mockRoleRepository();
		mockCommunityRepository();
		mockAddressRepository();
		mockPassportRepository();
	}
	
	@AfterMethod
	public void afterMethod(){
		//clear fake repositories after each method
		fakeUserRepository.clear();
		fakeRoleRepository.clear();
		fakePassportRepository.clear();
		fakeAddressRepository.clear();
	}

	/**
	 * Unit Test of login functionality
	 */
	@Test(dataProvider="providerLogin")
	public void login(String login, String password, boolean isPositive) {
		log.info(START);
		log.info(String.format("credentials: Log:'%s' Pass:'%s'",login,password));
		Assert.assertEquals(userService.login(login, password), isPositive);// correct
		log.info(END);
	}
	
	/**
	 * Unit Test of getting user by login functionality
	 */
	@Test(dataProvider="providerGetUserByLogin")
	public void getUserByLogin(String login, User expected,  boolean isPositive) {
		log.info(START);
		log.info(String.format("login: %s [%s]", login, isPositive));
		if(isPositive){
			User actual = userService.getUserByLogin(login);
			Assert.assertNotNull(actual);// correct data
			assertEqualsUsers(actual, expected);
		}else{
			Assert.assertNull(userService.getUserByLogin(login));// incorrect data
		}
		log.info(END);
	}
	
	/**
	 * Unit Test of finding user by login functionality (duplicate method of
	 * previous one 'getUserByLogin')
	 */
	@Test(dataProvider="providerGetUserByLogin")
	public void findUserByLogin(String login, User expected,  boolean isPositive) {
		log.info(START);
		log.info(String.format("login: %s [%s]", login, isPositive));
		if(isPositive){
			User actual = userService.getUserByLogin(login);
			Assert.assertNotNull(actual);// correct data
			assertEqualsUsers(actual, expected);
		}else{
			Assert.assertNull(userService.getUserByLogin(login));// incorrect data
		}
		log.info(END);
	}

	/**
	 * Unit Test of changing user status functionality
	 */
	@Test(dataProvider = "providerChangeUserStatus", dependsOnMethods={"findUserByLogin"})
	public void changeUserStatus(String login, UserStatus status) {
		log.info(START);
		UserStatusJson userStatusDto = new UserStatusJson(login, status.toString());
		log.info(String.format("login: '%s', status: '%s'", login, status.toString()));
		userService.changeUserStatus(userStatusDto);
		Assert.assertEquals(userService.findUserByLogin(login).getStatus(), status);// correct data
		log.info(END);
	}
	
	/**
	 * Unit Test of changing user role functionality
	 */
	@Test(dataProvider = "providerChangeUserRole", dependsOnMethods = { "findUserByLogin" })
	public void changeUserRole(String login, RoleType roleType) {
		log.info(START);
		userService.changeUserRole(login, roleType.ordinal());
		Assert.assertEquals(userService.findUserByLogin(login).getRole().getType(), roleType);
		log.info(END);
	}
	
	/**
	 * Unit Test of editing user information functionality
	 */
	@Test(dependsOnMethods = { "findUserByLogin" })
	public void editUserInformation() {
		log.info(START);
		// test data
		final UserDTO expected = new UserDTO("Петро", "Замоканий", "Афанасійович", RoleType.REGISTRATOR.toString(),
				"user","petro@gmail.com", UserStatus.BLOCK.toString(), 
				new AddressDTO("11111", "Івано-Франківська","Шевченківський", "Івано-Франківськ", "Зелена", "21", "12"),
				new PassportDTO("ББ", "22222", "Народом України2"));// login 'user' important!
		expected.setTerritorialCommunity("Львів");
		//positive
		UserDTO actual = userService.editUserInformation(expected);
		assertEqualsUsersDto(actual, expected);//compare
		//negative
		Mockito.when(userRepository.findUserByLogin("user")).thenReturn(null);
		Assert.assertNull(userService.editUserInformation(expected), "Saved null user");//
		log.info(END);
	}
	
	/**
	 * Unit Test of getting all statuses for registered users functionality
	 * Registered user have statuses: ACTIVE, BLOCK
	 */
	@Test
	public void fillInUserStatusforRegistratedUsers() {
		log.info(START);
		// test data
		final List<UserStatus> expected = Arrays.asList(UserStatus.ACTIVE, UserStatus.BLOCK);
		// test action
		List<UserStatus> actual = userService.fillInUserStatusforRegistratedUsers();
		Assert.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
		log.info(END);
	}
	
	/**
	 * Unit Test of getting all statuses for inactive users functionality
	 * Inactive user have status: ACTIVE, BLOCK, INACTIVE
	 */
	@Test
	public void fillInUserStatusforInactiveUsers() {
		log.info(START);
		// test data
		final List<UserStatus> expected = Arrays.asList(UserStatus.ACTIVE, UserStatus.BLOCK, UserStatus.INACTIVE);
		// test action
		List<UserStatus> actual = userService.fillInUserStatusforInactiveUsers();
		Assert.assertTrue(actual.containsAll(expected) && expected.containsAll(actual));
		log.info(END);
	}

	/**
	 * Unit Test of getting complex information about all users functionality
	 */
	@Test
	public void getUserDtoList() {
		log.info(START);
		//create expected result
		List<UserDTO> expected = new ArrayList<UserDTO>();
		for(int i=0;i<fakeUserRepository.size();i++){
			UserDTO userDto = createUserDto(fakeUserRepository.get(i).getLogin());
			expected.add(userDto);
		}
		//receive function result and compare
		List<UserDTO> actual = userService.getUserDtoList();
		Assert.assertEquals(actual.size(), expected.size(), "Bad size of users returned number");
		for (UserDTO actualDto : actual) {
			for(UserDTO expectedDto: expected){
				if(actualDto.getLogin()==expectedDto.getLogin()){
					assertEqualsUsersDto(actualDto, expectedDto);
					break;//test next actualDto
				}
			}
		}
		log.info(END);
	}
	
	/**
	 * Unit Test of getting complex information about user functionality
	 */
	@Test(dataProvider="providerGetUserDto" ,dependsOnMethods = { "getUserByLogin" })
	public void getUserDto(String login, boolean isPositive) {
		log.info(START);
		boolean exception = false;
		UserDTO expected = createUserDto(login);
		if(isPositive)
			expected.getPassport().setComment("comment");
		User expectedUser = null;
		for(User user: fakeUserRepository)
			if(user.getLogin()==login)
				expectedUser = user;
		// test action
		try{
			UserDTO actual = userService.getUserDto(login);
			assertEqualsUsersDto(actual, expected);
			// other branches
			Tome tom = new Tome(expectedUser, "Парк Погулянка");
			ResourceNumber resourceNumber = new ResourceNumber(0, "0", expectedUser);
			WillDocument willDocument = new WillDocument();
			willDocument.setComment("comment");
			expectedUser.setWillDocument(Arrays.asList(willDocument));
			Mockito.when(tomeRepository.findTomeByRegistrator(Mockito.any(User.class))).thenReturn(tom);
			Mockito.when(resourceNumberRepository.findResourceNumberByUser(Mockito.any())).thenReturn(resourceNumber);
			Assert.assertNotNull(userService.getUserDto("user"));
			log.info(END);
		}catch(NullPointerException e){
			//for negative test we wait for exception
			exception = true;
		}
		Assert.assertEquals(exception, !isPositive, "Test crashed");
	}
	
	/**
	 * Unit Test of getting all registered users functionality
	 * @param logins logins of users, which status we'll change to inactive for test
	 */
	@Test(dataProvider="providerGetAllUsers", dependsOnMethods = { "findUserByLogin", "fillInUserStatusforRegistratedUsers" })
	public void getAllRegistratedUsers(List<String> logins) {
		log.info(START);
		// prepare test data
		for(String login: logins)
			userService.findUserByLogin(login).setStatus(UserStatus.INACTIVE);
		// get available statuses for registered users
		List<UserDTO> expected = new ArrayList<UserDTO>();
		List<UserStatus> statuses = userService.fillInUserStatusforRegistratedUsers();
		for(User user: fakeUserRepository){
			if(statuses.contains(user.getStatus())){
				UserDTO userDto = createUserDto(user.getLogin());
				expected.add(userDto);
			}	
		}
		// test action
		List<UserDTO> actual= userService.getAllRegistratedUsers();
		Assert.assertEquals(actual.size(), expected.size(), "Bad size of List<UserDTO>");
		for (UserDTO actualDto : actual) {
			for (UserDTO expectedDto : expected)
				if (actualDto.getLogin() == expectedDto.getLogin())
					assertEqualsUsersDto(actualDto, expectedDto);
		}
		log.info(END);
	}
	
	/**
	 * Unit Test of getting all inactive users functionality
	 * @param logins logins of users, which status we'll change to inactive for test
	 */
	@Test(dataProvider="providerGetAllUsers", dependsOnMethods = { "getUserDtoList" })
	public void getAllInactiveUsers(List<String> logins) {
		log.info(START);
		// prepare test data
		for(String login: logins){
			userService.findUserByLogin(login).setStatus(UserStatus.INACTIVE);
			userService.findUserByLogin(login).setRole(new Role(RoleType.USER,"description"));//inactive users must have ROLE_USER
		}
		List<UserDTO> expected = new ArrayList<UserDTO>();
		for(User user: fakeUserRepository){
			if(user.getStatus() == UserStatus.INACTIVE){
				UserDTO userDto = createUserDto(user.getLogin());
				expected.add(userDto);
			}	
		}
		// test action
		List<UserDTO> actual= userService.getAllInactiveUsers();
		Assert.assertEquals(actual.size(), expected.size(), "Bad size of List<UserDTO>");
		for (UserDTO actualDto : actual) {
			for (UserDTO expectedDto : expected)
				if (actualDto.getLogin() == expectedDto.getLogin())
					assertEqualsUsersDto(actualDto, expectedDto);
		}
		log.info(END);
	}
	
	/**
	 * Unit Test of check user on existing functionality
	 */
	@Test(dataProvider="providerGetUserDto")
	public void checkUsernameNotExistInDB(String login, boolean isExist) {
		log.info(START);
		Assert.assertEquals(userService.checkUsernameNotExistInDB(login),!isExist,"");
		log.info(END);
	}
	
	/**
	 * Unit Test of getting user by search tag(last name *) functionality
	 */
	@Test(dataProvider="providerGetUserBySearchTag", dependsOnMethods = { "getUserByLogin" })
	public void getUserBySearchTag(User registrator, String searchTag) {
		log.info(START);
		// prepare expected
		List<UserDTO> expected = new ArrayList<UserDTO>();
		for(User user: fakeUserRepository)
			if(user.getTerritorialCommunity().getName()==registrator.getTerritorialCommunity().getName())
				if(user.getLastName().contains(searchTag))
					expected.add(createUserDto(user.getLogin()));
		// method that we test need security authentication of registrator
		SecurityContextHolder.getContext()
				.setAuthentication(new UsernamePasswordAuthenticationToken(registrator.getLogin(),registrator.getPassword()));
		// test action
		List<UserDTO> actual = userService.getUserBySearchTag(searchTag);
		Assert.assertEquals(actual.size(), expected.size(), "Different sizes of actual and expected");
		for(UserDTO actualDto: actual){
			for(UserDTO expectedDto: expected){
				if(actualDto.getLogin()==expectedDto.getLogin()){
					//not full DTO is filled
					Assert.assertEquals(actualDto.getFirstName(), expectedDto.getFirstName(), "Different Firstname");
					Assert.assertEquals(actualDto.getLastName(), expectedDto.getLastName(), "Different Lastname");
					Assert.assertEquals(actualDto.getMiddleName(), expectedDto.getMiddleName(), "Different Middlename");
					expected.remove(expectedDto);
					break;
				}
			}
		}
		Assert.assertEquals(expected.isEmpty(), true);
		log.info(END);
	}
	
	/**
	 * Unit Test of getting user by email functionality
	 */
	@Test(dataProvider="providerGetUserByEmail")
	public void findUserByEmail(User expected, String login, boolean isPositive) {
		log.info(START);
		User actual = userService.findUserByEmail(login);
		if(isPositive)
			assertEqualsUsers(actual, expected);
		else
			Assert.assertEquals(actual, expected);
		log.info(END);
	}

	/**
	 * Unit Test of incrementing fail attempts functionality
	 */
	@Test
	public void updateFailAttempts() {
		log.info(START);
		// test data
		User user = fakeUserRepository.get(0);
		int expected = user.getAttempts()+1;
		// 1st test action
		userService.updateFailAttempts(user.getLogin());
		Assert.assertEquals(user.getAttempts(), expected);
		// 2nd test action
		expected = 3;
		user.setAttempts(expected-1);
		userService.updateFailAttempts(user.getLogin());
		Assert.assertEquals(user.getAttempts(), expected);
		log.info(END);
	}
	
	
	
	/**
	 * Data for UserService.providerTestLogin();
	 * positive & negative
	 */
	@DataProvider
	public static Object[][] providerLogin() {
        return new Object[][] { 
        	{ "user", "user", true } ,
        	{ null, null, false } ,
        	{ "admin","somepass", false } ,
        	{ "badlogin","registrator", false }
        	};
    }
	
	/**
	 * Data for UserService.testGetUserByLogin();
	 * positive & negative
	 */
	@DataProvider
	public static Object[][] providerGetUserByLogin() {
		//prepare expected users
		User user = new User();
		TerritorialCommunity communityUkraine = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		user = new User("user", "user", new Role(RoleType.USER, "description"), "Василь", "Труба", "Георгійович",
				"vasul.tryba@gmail.com", UserStatus.ACTIVE.toString(), "09800000002");
		user.setTerritorialCommunity(communityUkraine);
		user.setAddress(Arrays
				.asList(new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31")));
		user.setPassport(Arrays.asList(new PassportInfo(user, "AA", "00000", "Народом України")));
		user.setUserId(1);
        return new Object[][] {     
                 {"user", user,true}, {"wrong",null,false} , {null,null,false}        
           };
    }
	
	/**
	 * Data for UserService.testChangeUserStatus();
	 * positive
	 */
	@DataProvider
	public static Object[][] providerChangeUserStatus() {
        return new Object[][] {     
                 { "user", UserStatus.BLOCK }, 
                 { "user", UserStatus.INACTIVE },
                 { "user", UserStatus.ACTIVE }, 
                 { "user", UserStatus.INACTIVE },
                 { "user", UserStatus.BLOCK },
                 { "user", UserStatus.ACTIVE },
           };
    }
	
	/**
	 * Data for UserService.testChangeUserRole();
	 * positive
	 */
	@DataProvider
	public static Object[][] providerChangeUserRole() {
        return new Object[][] {     
        	//Role: user
            { "user", RoleType.ADMIN },
            { "user", RoleType.REGISTRATOR },
            { "user", RoleType.COMMISSIONER },
            { "user", RoleType.USER }
           };
    }
	
	/**
	 * Data for UserService.getAllInactiveUsers()/getAllRegisteredUsers();
	 */
	@DataProvider
	public static Object[][] providerGetAllUsers() {
        return new Object[][] {     
            { Arrays.asList("user") }, 
            { Arrays.asList("user","admin") }, 
            { Arrays.asList("user","admin","registrator") }, 
            { Arrays.asList("user","admin","registrator","commissioner") }
           };
    }
	
	/**
	 * Data for UserService.getUserDto();
	 */
	@DataProvider
	public static Object[][] providerGetUserDto() {
        return new Object[][] {     
        	//Role: user
            { "user", true}, {"admin", true}, {"registrator", true}, {"commissioner", true}, {null,false}, {"login",false}
           };
    }
	
	/**
	 * Data for UserService.getUserBySearchTag;
	 */
	@DataProvider
	public static Object[][] providerGetUserBySearchTag() {
		//prepare expected users
		TerritorialCommunity communityUkraine = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		User registrator = new User("registrator", "registrator", new Role(RoleType.REGISTRATOR, "description"), "Володимир",
				"Сидор", "Тарасович", "sydor@gmail.com", UserStatus.ACTIVE.toString(), "09500000003");//we need only login&pass&role
		registrator.setTerritorialCommunity(communityUkraine);//and community

		return new Object[][] { {registrator, "Труба"}, {registrator, "Нема"} };
    }
	
	/**
	 * Data for UserService.getUserBySearchTag;
	 */
	@DataProvider
	public static Object[][] providerGetUserByEmail() {
		//prepare expected users
		User user = new User();
		TerritorialCommunity communityUkraine = new TerritorialCommunity();
		communityUkraine.setName("Україна");
		user = new User("user", "user", new Role(RoleType.USER, "description"), "Василь", "Труба", "Георгійович",
				"vasul.tryba@gmail.com", UserStatus.ACTIVE.toString(), "09800000002");
		user.setTerritorialCommunity(communityUkraine);
		user.setAddress(Arrays
				.asList(new Address(user, "00000", "Львівська", "Галицький", "Львів", "Вітовського", "48", "31")));
		user.setPassport(Arrays.asList(new PassportInfo(user, "AA", "00000", "Народом України")));
		user.setUserId(1);

		return new Object[][] { {user,user.getEmail(), true}, {null, "notmy@gmail.com", false} };
    }
	

	private void assertEqualsUsers(User actual, User expected) {
		Assert.assertEquals(actual.getLogin(), expected.getLogin(), "Different login");
		Assert.assertEquals(actual.getEmail(), expected.getEmail(), "Different email");
		Assert.assertEquals(actual.getFirstName(), expected.getFirstName(), "Different Firstname");
		Assert.assertEquals(actual.getLastName(), expected.getLastName(), "Different Lastname");
		Assert.assertEquals(actual.getMiddleName(), expected.getMiddleName(), "Different Middlename");
		Assert.assertEquals(actual.getPhoneNumber(), expected.getPhoneNumber(), "Different phone");
		Assert.assertEquals(actual.getRole().toString(), expected.getRole().toString(), "Different role");
		Assert.assertEquals(actual.getStatus(), expected.getStatus(), "Different status");
		Assert.assertEquals(actual.getTerritorialCommunity().getName(), expected.getTerritorialCommunity().getName(), "Different community");
		Assert.assertEquals(actual.getAddress().size(), expected.getAddress().size(), "Different addresses count");
		for(int i=0;i<actual.getAddress().size();i++){
			Assert.assertEquals(actual.getAddress().get(i).getBuilding(), expected.getAddress().get(i).getBuilding(), "Different building");
			Assert.assertEquals(actual.getAddress().get(i).getCity(), expected.getAddress().get(i).getCity(), "Different city");
			Assert.assertEquals(actual.getAddress().get(i).getDistrict(), expected.getAddress().get(i).getDistrict(), "Different disrict");
			Assert.assertEquals(actual.getAddress().get(i).getFlat(), expected.getAddress().get(i).getFlat(), "Different flat");
			Assert.assertEquals(actual.getAddress().get(i).getPostCode(), expected.getAddress().get(i).getPostCode(), "Different postcode");
			Assert.assertEquals(actual.getAddress().get(i).getRegion(), expected.getAddress().get(i).getRegion(), "Different region");
			Assert.assertEquals(actual.getAddress().get(i).getStreet(), expected.getAddress().get(i).getStreet(), "Different street");
		}
		Assert.assertEquals(actual.getPassport().size(), expected.getPassport().size(), "Different passports count");
		for(int i=0;i<actual.getPassport().size();i++){
			Assert.assertEquals(actual.getPassport().get(0).getNumber(), expected.getPassport().get(0).getNumber(), "Different passport number");
			Assert.assertEquals(actual.getPassport().get(0).getPublishedByData(), expected.getPassport().get(0).getPublishedByData(), "Different passport published data");
			Assert.assertEquals(actual.getPassport().get(0).getSeria(), expected.getPassport().get(0).getSeria(), "Different passport seria");
			Assert.assertEquals(actual.getPassport().get(0).getComment(), expected.getPassport().get(0).getComment(), "Different passport comment");
		}
	}
	
	private void assertEqualsUsersDto(UserDTO actual, UserDTO expected) {
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
	 * Form UserDTO from user.login
	 * @param login user name
	 * @return userDTO, else null if user with 'login' don't exist
	 */
	private UserDTO createUserDto(String login){
		for(User user: fakeUserRepository){
			if(user.getLogin()==login){
				Address address = user.getAddress().get(user.getAddress().size()-1);
				PassportInfo passport = user.getPassport().get(user.getPassport().size()-1);
				AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(), address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
				PassportDTO passportDto = new PassportDTO(passport.getSeria(),passport.getNumber(),passport.getPublishedByData());;
				UserDTO userDto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(), user.getRole().toString(), user.getLogin(), user.getEmail(), user.getStatus().toString(), addressDto, passportDto);
				return userDto;
			}
		}
		return null;
	}


}
