package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.registrator.community.dao.AddressRepository;
import org.registrator.community.dao.PassportRepository;
import org.registrator.community.dao.ResourceNumberRepository;
//import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.RoleRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.ResourceNumberDTO;
import org.registrator.community.dto.TomeDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.WillDocumentDTO;
import org.registrator.community.dto.JSON.ResourceNumberDTOJSON;
import org.registrator.community.dto.JSON.UserStatusDTOJSON;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.OtherDocuments;
import org.registrator.community.entity.PassportInfo;
import org.registrator.community.entity.ResourceNumber;
import org.registrator.community.entity.Role;
import org.registrator.community.entity.Tome;
import org.registrator.community.entity.User;
import org.registrator.community.entity.WillDocument;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.enumeration.UserStatus;
import org.registrator.community.forms.RegistrationForm;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PassportRepository passportRepository;

	@Autowired
	AddressRepository addressRepository;

	@Autowired
	ResourceNumberRepository resourceNumberRepository;

	@Autowired
	TomeRepository tomeRepository;

	@Autowired
	Logger logger;

	@Autowired
	private PasswordEncoder userPasswordEncoder;

	/**
	 * Method, which returns user from database by login
	 * 
	 * @param login
	 * @return User
	 * 
	 */
	@Transactional
	@Override
	public User getUserByLogin(String login) {
		return userRepository.findUserByLogin(login);
	}

	/**
	 * Method, which changes user status
	 * 
	 * @param userStatusDTO
	 * @return void
	 * 
	 */
	@Transactional
	@Override
	public void changeUserStatus(UserStatusDTOJSON userStatusDTO) {
		logger.info("begin");
		User user = getUserByLogin(userStatusDTO.getLogin());
		if (userStatusDTO.getStatus().equals(UserStatus.BLOCK.toString())) {
			logger.info("set user status to" + UserStatus.BLOCK);
			user.setStatus(UserStatus.BLOCK);
		} else {
			if (userStatusDTO.getStatus().equals(UserStatus.UNBLOCK.toString())) {
				logger.info("set user status to" + UserStatus.UNBLOCK);
				user.setStatus(UserStatus.UNBLOCK);
			} else {
				if (userStatusDTO.getStatus().equals(UserStatus.INACTIVE.toString())) {
					logger.info("set user status to" + UserStatus.INACTIVE);
					user.setStatus(UserStatus.INACTIVE);
				}
			}
		}
		logger.info("Save user in data base");
		userRepository.save(user);
	}

	/**
	 * Method, which retruns all registrated users
	 * 
	 * @return List<UserDTO>
	 * 
	 */

	@Transactional
	@Override
	public List<UserDTO> getAllRegistratedUsers() {
		try {
			List<UserDTO> userList = getUserDtoList();
			List<UserDTO> registratedUsers = new ArrayList<UserDTO>();
			for (UserDTO user : userList) {
				if (user.getStatus().toString() != UserStatus.INACTIVE.toString()) {
					logger.info("User is registrated");
					registratedUsers.add(user);
				}
			}
			return registratedUsers;
		} catch (ArrayIndexOutOfBoundsException ex) {
			return null;
		}
	}

	/**
	 * Method, which retruns all inactive users
	 * 
	 * @return List<UserDTO>
	 * 
	 */
	@Transactional
	@Override
	public List<UserDTO> getAllInactiveUsers() {
		try {
			List<UserDTO> userDtoList = new ArrayList<UserDTO>();
			userDtoList = getUserDtoList();
			List<UserDTO> inactiveUserDtoList = new ArrayList<UserDTO>();
			for (UserDTO userDto : userDtoList) {
				if (userDto.getStatus() == UserStatus.INACTIVE.toString()) {
					userDto.setRole("USER");
					inactiveUserDtoList.add(userDto);
				}
			}
			return inactiveUserDtoList;
		} catch (ArrayIndexOutOfBoundsException ex) {
			return null;
		}
	}

	/**
	 * Method, which changes user role
	 * 
	 * @param login,role_id
	 * @return void
	 * 
	 */
	@Transactional
	@Override
	public void changeUserRole(String login, Integer role_id) {
		User user = getUserByLogin(login);
		Role role = roleRepository.findOne(String.valueOf(role_id));
		logger.info("user role is" + role.getType().name());
		user.setRole(role);
		logger.info("save user role");
		userRepository.save(user);
	}

	/**
	 * Method, which edits information about user
	 * 
	 * @param userDto
	 * @return userDTO
	 * 
	 */
	@Transactional
	@Override
	public UserDTO editUserInformation(UserDTO userDto) {
		User user = getUserByLogin(userDto.getLogin());
		if (user != null) {
			user.setFirstName(userDto.getFirstName());
			user.setLastName(userDto.getLastName());
			user.setMiddleName(userDto.getMiddleName());
			user.setEmail(userDto.getEmail());
			user.setPassword(userDto.getPassword());
			user.setRole(checkRole(userDto.getRole()));
			user.setStatus(checkUserStatus(userDto.getStatus()));
			logger.info("edit user in data base");
			PassportInfo passport = new PassportInfo(user, userDto.getPassport().getSeria(),
					Integer.parseInt(userDto.getPassport().getNumber()), userDto.getPassport().getPublished_by_data());
			Address address = new Address(user, userDto.getAddress().getPostcode(), userDto.getAddress().getRegion(),
					userDto.getAddress().getDistrict(), userDto.getAddress().getCity(),
					userDto.getAddress().getStreet(), userDto.getAddress().getBuilding(),
					userDto.getAddress().getFlat());
			int result = user.getAddress().get(user.getAddress().size() - 1).compareTo(address);
			if (result != 0) {
				logger.info("save address");
				addressRepository.save(address);
			}
			result = user.getPassport().get(user.getPassport().size() - 1).compareTo(passport);
			if (result != 0) {
				logger.info("save passport");
				passportRepository.save(passport);
			}
			logger.info("save all changes");
			userRepository.save(user);

			return userDto;
		} else {
			return null;
		}
	}

	/**
	 * Method, which fill in user status for registrateds users
	 * 
	 * @return List<UserStatus>
	 * 
	 */
	@Transactional
	@Override
	public List<UserStatus> fillInUserStatusforRegistratedUsers() {
		List<UserStatus> userStatusList = new ArrayList<UserStatus>();
		userStatusList.add(UserStatus.BLOCK);
		userStatusList.add(UserStatus.UNBLOCK);
		return userStatusList;
	}

	/**
	 * Method, which fill in user status for inactives users
	 * 
	 * @return List<UserStatus>
	 * 
	 */
	@Transactional
	@Override
	public List<UserStatus> fillInUserStatusforInactiveUsers() {
		List<UserStatus> userStatusList = new ArrayList<UserStatus>();
		userStatusList.add(UserStatus.INACTIVE);
		userStatusList.add(UserStatus.BLOCK);
		userStatusList.add(UserStatus.UNBLOCK);
		return userStatusList;
	}

	/**
	 * Method, which gets user list userDto from database
	 * 
	 * @return userDTO
	 * 
	 */
	@Transactional
	@Override
	public List<UserDTO> getUserDtoList() {
		List<UserDTO> userDtoList = new ArrayList<UserDTO>();
		List<User> userList = userRepository.findAll();
		for (User user : userList) {
			PassportInfo passportInfo = user.getPassport().get(user.getPassport().size() - 1);
			PassportDTO passportDto = new PassportDTO(passportInfo.getSeria(), passportInfo.getNumber().toString(),
					passportInfo.getPublishedByData());
			Address address = user.getAddress().get(user.getAddress().size() - 1);
			AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(),
					address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
			UserDTO userDto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
					user.getRole().toString(), user.getLogin(), user.getPassword(), user.getEmail(),
					user.getStatus().toString(), addressDto, passportDto);
			userDtoList.add(userDto);
		}
		return userDtoList;
	}

	/**
	 * Method, which gets user userDto from database
	 * 
	 * @param login
	 * @return userDTO
	 * 
	 */
	@Transactional
	@Override
	public UserDTO getUserDto(String login) {
		User user = getUserByLogin(login);
		PassportInfo passportInfo = user.getPassport().get(user.getPassport().size() - 1);
		PassportDTO passportDto = new PassportDTO(passportInfo.getSeria(), passportInfo.getNumber().toString(),
				passportInfo.getPublishedByData());
		if (passportInfo.getComment() != null) {
			passportDto.setComment(passportInfo.getComment());
		}
		Address address = user.getAddress().get(user.getAddress().size() - 1);
		AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(),
				address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
		UserDTO userdto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
				user.getRole().toString(), user.getLogin(), user.getPassword(), user.getEmail(),
				user.getStatus().toString(), addressDto, passportDto);
		if (!user.getWillDocument().isEmpty()) {
			WillDocument willDocument = user.getWillDocument().get(user.getWillDocument().size() - 1);
			WillDocumentDTO willDocumentDTO = new WillDocumentDTO();
			willDocumentDTO.setAccessionDate(willDocument.getAccessionDate());
			if (willDocument.getComment() != null) {
				willDocumentDTO.setComment(willDocument.getComment());
			}
			userdto.setWillDocument(willDocumentDTO);
		}
		return formUserDTO(user);
	}

	/**
	 * Method, which gets all inactives users
	 * 
	 * @return List<UserDTO>
	 * 
	 */

	@Transactional
	@Override
	public int updateUser(User user) {
		return 0;
	}

	@Transactional
	@Override
	public boolean login(String username, String password) {
		if (userRepository.getUserByLoginAndPassword(username, password) != null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method, which checks user status
	 * 
	 * @param status
	 * @return UserStatus
	 * 
	 */
	private UserStatus checkUserStatus(String status) {
		if (status.equals(UserStatus.BLOCK.name())) {
			return UserStatus.BLOCK;
		} else if (status.equals(UserStatus.INACTIVE.name())) {
			return UserStatus.INACTIVE;
		} else {
			return UserStatus.UNBLOCK;
		}
	}

	/**
	 * Method, which checks user role
	 * 
	 * @param role
	 * @return Role
	 * 
	 */
	private Role checkRole(String role) {
		List<Role> roleList = roleRepository.findAll();
		switch (role) {
		case "USER":
			return roleList.get(2);
		case "REGISTRATOR":
			return roleList.get(1);
		case "COMMISSIONER":
			return roleList.get(3);
		default:
			return roleList.get(0);
		}
	}

	/**
	 * register user service: accepts 'registrationForm' with fields, needed to
	 * store data in Users, Address and Passport_Data tables By default, every
	 * new user is given role "User" and status "Inactive" until it's changed by
	 * Admin
	 * 
	 * @param registrationForm
	 */
	@Override
	@Transactional
	public void registerUser(RegistrationForm registrationForm) {

		// if (this.userRepository.findUserByLogin(registrationForm.getLogin())
		// != null) {
		// return UserService.ERR_DUP_USER;
		// }
		// if (this.userRepository.findUserByEmail(registrationForm.getEmail())
		// != null) {
		// return UserService.ERR_DUP_EMAIL;
		// }
		User user = new User();
		user.setLogin(registrationForm.getLogin());
		user.setEmail(registrationForm.getEmail());
		user.setPasswordHash(userPasswordEncoder.encode(registrationForm.getPassword()));
		user.setFirstName(registrationForm.getFirstName());
		user.setLastName(registrationForm.getLastName());
		user.setMiddleName(registrationForm.getMiddleName());
		user.setRole(roleRepository.findRoleByType(RoleType.USER));
		user.setStatus(UserStatus.INACTIVE);

		userRepository.saveAndFlush(user);
		log.info("Inserted new user data into 'users' table: user_id = " + user.getUserId());

		if (userRepository.findUserByLogin(user.getLogin()) != null) {
			// insert user's passport data into "passport_data" table
			PassportInfo passport = new PassportInfo();
			passport.setUser(user);
			passport.setSeria(registrationForm.getSeria());
			passport.setNumber(Integer.parseInt(registrationForm.getNumber()));
			passport.setPublishedByData(registrationForm.getPublishedByData());

			passportRepository.saveAndFlush(passport);
			log.info("Inserted passport data for user {0}, passport_data_id = {1}", user.getLogin(),
					passport.getPassportId());

			// insert user's address records into "address" table
			Address address = new Address();
			address.setUser(user);
			address.setCity(registrationForm.getCity());
			address.setRegion(registrationForm.getRegion());
			address.setDistrict(registrationForm.getDistrict());
			address.setStreet(registrationForm.getStreet());
			address.setBuilding(registrationForm.getBuilding());
			address.setFlat(registrationForm.getFlat());
			address.setPostCode(registrationForm.getPostcode());

			addressRepository.saveAndFlush(address);
			log.info("Inserted address data for user {0}, address_id = {1}", user.getLogin(), address.getAddressId());
		}

	}

	// @Transactional
	// @Override
	// public int updateUser(User user) {
	// return 0;
	// }
	//
	// @Transactional
	// @Override
	// public boolean login(String login, String password) {
	// if (userRepository.findUserByLogin(login) != null
	// && userRepository.getUsersPasswordHash(login) ==
	// DigestUtils.md5Hex(password)) {
	// return true;
	// } else {
	// return false;
	// }
	// }

	@Transactional
	@Override
	public boolean checkUsernameNotExistInDB(String login) {
		if (userRepository.findUserByLogin(login) != null) {
			// when username exists in DB
			return false;
		}
		// if username isn't found in DB
		return true;
	}

	/**
	 * Method, which creates resoure number
	 * 
	 * @param resourseNumberDtoJson
	 * @return void
	 * 
	 */
	@Transactional
	@Override
	public void createResourceNumber(ResourceNumberDTOJSON resourseNumberDtoJson) {
		User user = userRepository.findUserByLogin(resourseNumberDtoJson.getLogin());
		ResourceNumberDTO resourseNumberDto = new ResourceNumberDTO(
				Integer.parseInt(resourseNumberDtoJson.getResource_number()),
				resourseNumberDtoJson.getRegistrator_number());
		ResourceNumber resourceNumber = new ResourceNumber(resourseNumberDto.getNumber(),
				resourseNumberDto.getRegistratorNumber(), user);
		resourceNumberRepository.save(resourceNumber);
	}

	/**
	 * Method, which creates tome
	 * 
	 * @param resourseNumberDtoJson
	 * @return void
	 * 
	 */
	@Transactional
	@Override
	public void createTome(ResourceNumberDTOJSON resourseNumberDtoJson) {
		User user = userRepository.findUserByLogin(resourseNumberDtoJson.getLogin());
		TomeDTO tomeDto = new TomeDTO(resourseNumberDtoJson.getIdentifier(), user.getFirstName(), user.getLastName(),
				user.getMiddleName());
		Tome tome = new Tome(user, tomeDto.getTomeIdentifier());
		tomeRepository.save(tome);
	}

	// @Override

	@Override
	public List<UserDTO> getUserBySearchTag(String searchTag) {
		List<User> usersList = userRepository.findOwnersLikeProposed(searchTag);
		List<UserDTO> userDtos = new ArrayList<UserDTO>();
		for (User user : usersList) {
			UserDTO userdto = formUserDTO(user);
			userDtos.add(userdto);
		}
		System.out.println("DtOs" + userDtos);
		return userDtos;
	}

	private UserDTO formUserDTO(User user) {
		PassportInfo passportInfo = user.getPassport().get(user.getPassport().size() - 1);
		PassportDTO passportDto = new PassportDTO(passportInfo.getSeria(), passportInfo.getNumber().toString(),
				passportInfo.getPublishedByData());
		if (passportInfo.getComment() != null) {
			passportDto.setComment(passportInfo.getComment());
		}
		Address address = user.getAddress().get(user.getAddress().size() - 1);
		AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(),
				address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
		UserDTO userdto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
				user.getRole().toString(), user.getLogin(), user.getPassword(), user.getEmail(),
				user.getStatus().toString(), addressDto, passportDto);
		if (!user.getWillDocument().isEmpty()) {
			WillDocument willDocument = user.getWillDocument().get(user.getWillDocument().size() - 1);
			WillDocumentDTO willDocumentDTO = new WillDocumentDTO();
			willDocumentDTO.setAccessionDate(willDocument.getAccessionDate());
			if (willDocument.getComment() != null) {
				willDocumentDTO.setComment(willDocument.getComment());
			}
			userdto.setWillDocument(willDocumentDTO);
		}

		if (!user.getOtherDocuments().isEmpty()) {
			List<String> otherDocuments = new ArrayList<String>();
			for (OtherDocuments otherDocument : user.getOtherDocuments()) {
				otherDocuments.add(otherDocument.getComment());
			}
			userdto.setOtherDocuments(otherDocuments);
		}

		return userdto;
	}
}