package org.registrator.community.service.impl;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.registrator.community.dao.AddressRepository;
import org.registrator.community.dao.PassportRepository;
import org.registrator.community.dao.ResourceNumberRepository;
import org.registrator.community.dao.RoleRepository;
import org.registrator.community.dao.TomeRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.AddressDTO;
import org.registrator.community.dto.PassportDTO;
import org.registrator.community.dto.ResourceNumberDTO;
import org.registrator.community.dto.UserDTO;
import org.registrator.community.dto.UserRegistrationDTO;
import org.registrator.community.dto.WillDocumentDTO;
import org.registrator.community.dto.json.CommunityParamJson;
import org.registrator.community.dto.json.ResourceNumberJson;
import org.registrator.community.dto.json.RoleTypeJson;
import org.registrator.community.dto.json.UserStatusJson;
import org.registrator.community.entity.Address;
import org.registrator.community.entity.OtherDocuments;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImpl.class);

    private static final int MAX_ATTEMPTS = 2;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PassportRepository passportRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private ResourceNumberRepository resourceNumberRepository;

    @Autowired
    private TomeRepository tomeRepository;

    @Autowired
    private Logger logger;

    @Autowired
    private PasswordEncoder userPasswordEncoder;

    @Autowired
    private CommunityService communityService;

    /**
     * Method, which returns user from database by login
     *
     * @return User
     */
    @Transactional
    @Override
    public User getUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    /**
     * Method, which changes user status
     *
     * @return void
     */
    @Transactional
    @Override
    public void changeUserStatus(UserStatusJson userStatusJson) {
        logger.info("begin");
        User user = getUserByLogin(userStatusJson.getLogin());
        if (userStatusJson.getStatus().equals(UserStatus.BLOCK.toString())) {
            logger.info("set user status to" + UserStatus.BLOCK);
            user.setStatus(UserStatus.BLOCK);
        } else {
            if (userStatusJson.getStatus().equals(UserStatus.ACTIVE.toString())) {
                logger.info("set user status to" + UserStatus.ACTIVE);
                user.setStatus(UserStatus.ACTIVE);
            } else {
                if (userStatusJson.getStatus().equals(UserStatus.INACTIVE.toString())) {
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
     * @param login ,role_id
     * @return void
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
     * @return userDTO
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
            user.setRole(checkRole(userDto.getRole()));
            user.setStatus(checkUserStatus(userDto.getStatus()));
            TerritorialCommunity territorialCommunity = communityService.findByName(userDto.getTerritorialCommunity());
            user.setTerritorialCommunity(territorialCommunity);
            logger.info("edit user in data base");
            PassportInfo passport = new PassportInfo(user, userDto.getPassport().getSeria(),
                    userDto.getPassport().getNumber(), userDto.getPassport().getPublished_by_data());
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
     */
    @Transactional
    @Override
    public List<UserStatus> fillInUserStatusforRegistratedUsers() {
        List<UserStatus> userStatusList = new ArrayList<UserStatus>();
        userStatusList.add(UserStatus.BLOCK);
        userStatusList.add(UserStatus.ACTIVE);
        return userStatusList;
    }

    /**
     * Method, which fill in user status for inactives users
     *
     * @return List<UserStatus>
     */
    @Transactional
    @Override
    public List<UserStatus> fillInUserStatusforInactiveUsers() {
        List<UserStatus> userStatusList = new ArrayList<UserStatus>();
        userStatusList.add(UserStatus.INACTIVE);
        userStatusList.add(UserStatus.BLOCK);
        userStatusList.add(UserStatus.ACTIVE);
        return userStatusList;
    }

    /**
     * Method, which gets user list userDto from database
     *
     * @return userDTO
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
                    user.getRole().toString(), user.getLogin(), user.getEmail(), user.getStatus().toString(),
                    addressDto, passportDto);
            userDto.setTerritorialCommunity(user.getTerritorialCommunity().getName());
            userDtoList.add(userDto);
        }
        return userDtoList;
    }

    /**
     * Method, which gets user userDto from database
     *
     * @return userDTO
     */
    @Transactional
    @Override
    public UserDTO getUserDto(String login) {
        User user = getUserByLogin(login);
        logger.info("user "+user);
        PassportInfo passportInfo = user.getPassport().get(user.getPassport().size() - 1);
        PassportDTO passportDto = new PassportDTO(passportInfo.getSeria(), passportInfo.getNumber().toString(),
                passportInfo.getPublishedByData());
        if (passportInfo.getComment() != null) {
            passportDto.setComment(passportInfo.getComment());
        }
        Address address = user.getAddress().get(user.getAddress().size() - 1);
        AddressDTO addressDto = new AddressDTO(address.getPostCode(), address.getRegion(), address.getDistrict(),
                address.getCity(), address.getStreet(), address.getBuilding(), address.getFlat());
        ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);
        Tome tome = tomeRepository.findTomeByRegistrator(user);
        ResourceNumberJson resourceNumberJson = null;
        if ((tome != null) && (resourceNumber != null)) {
            resourceNumberJson = new ResourceNumberJson(resourceNumber.getNumber().toString(),
                    resourceNumber.getRegistratorNumber(), tome.getIdentifier());
        } else {
            resourceNumberJson = new ResourceNumberJson();
            resourceNumberJson.setResource_number("0");
            resourceNumberJson.setRegistrator_number("0");
            resourceNumberJson.setIdentifier("0");
        }
        UserDTO userdto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
                user.getRole().toString(), user.getLogin(), user.getEmail(), user.getStatus().toString(), addressDto,
                passportDto, user.getTerritorialCommunity().getName(), resourceNumberJson);
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
     */

    @Transactional
    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    /**
     * Method, which delete user only if user status = NOTCOMFIRMED
     *
     * @return List<UserDTO>
     */
    @Transactional
    @Override
    public String deleteNotConfirmedUser(String logins) {
        
        List<PassportInfo> passportInfoList = new ArrayList<PassportInfo>();
        List<Address> addressList = new ArrayList<Address>();
        List<String> users = new ArrayList<String>();

        Collections.addAll(users, logins.split(","));
        
        List<User> userList = userRepository.findUsersByLoginList(users);
        
        for (User user: userList){
            if (user.getStatus() == UserStatus.NOTCOMFIRMED) {
                passportInfoList.addAll(user.getPassport());
                addressList.addAll(user.getAddress());
            }else{
                logger.info("Try to delete users wich are not in status NOTCOMFIRMED");
                return "only NOTCOMFIRMED alowed to delete";
                }
        }
        
        logger.info("start delete operations");
        
        passportRepository.delete(passportInfoList);
        logger.info("pasports deleted");
        
        addressRepository.delete(addressList);
        logger.info("addresses deleted");
        
        userRepository.delete(userList);
        logger.info("users deleted");
        
        return "sucsesfuly deleted";
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
     * @return UserStatus
     */
    private UserStatus checkUserStatus(String status) {
        if (status.equals(UserStatus.BLOCK.name())) {
            return UserStatus.BLOCK;
        } else if (status.equals(UserStatus.INACTIVE.name())) {
            return UserStatus.INACTIVE;
        } else {
            return UserStatus.ACTIVE;
        }
    }

    /**
     * Method, which checks user role
     *
     * @return Role
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
            case "ADMIN":
                return roleList.get(0);
            default:
                return roleList.get(2);
        }
    }

    /**
     * register user service: accepts 'registrationForm' with fields, needed to store data in Users,
     * Address and Passport_Data tables By default, every new user is given role "User" and status
     * "Inactive" until it's changed by Admin
     */
    @Override
    @Transactional
    public void registerUser(UserRegistrationDTO registrationForm) {
        TerritorialCommunity territorialCommunity = communityService
                .findByName(registrationForm.getTerritorialCommunity());
        User user = new User();
        user.setLogin(registrationForm.getLogin());
        user.setEmail(registrationForm.getEmail());
        user.setPasswordHash(userPasswordEncoder.encode(registrationForm.getPassword()));
        user.setFirstName(registrationForm.getFirstName());
        user.setLastName(registrationForm.getLastName());
        user.setMiddleName(registrationForm.getMiddleName());
        user.setPhoneNumber(registrationForm.getPhoneNumber());
        user.setRole(roleRepository.findRoleByType(RoleType.USER));
        //user.setStatus(UserStatus.INACTIVE);
        user.setStatus(UserStatus.NOTCOMFIRMED);
        user.setPhoneNumber(registrationForm.getPhoneNumber());
        user.setDateOfAccession(registrationForm.getDateOfAccession());
        user.setTerritorialCommunity(territorialCommunity);

        userRepository.save(user);
        log.info("Inserted new user data into 'users' table: user_id = " + user.getUserId());

        if (userRepository.findUserByLogin(user.getLogin()) != null) {
            // insert user's passport data into "passport_data" table
            PassportDTO passportDTO = registrationForm.getPassport();
            PassportInfo passport = new PassportInfo();
            passport.setUser(user);
            passport.setSeria(passportDTO.getSeria());
            passport.setNumber((passportDTO.getNumber()));
            passport.setPublishedByData(passportDTO.getPublished_by_data());
            passport.setComment(passportDTO.getComment());

            passportRepository.save(passport);
            log.info("Inserted passport data for user with passport_data_id", user.getLogin(),
                    passport.getPassportId());

            // insert user's address records into "address" table
            AddressDTO addressDTO = registrationForm.getAddress();
            Address address = new Address();
            address.setUser(user);
            address.setCity(addressDTO.getCity());
            address.setRegion(addressDTO.getRegion());
            address.setDistrict(addressDTO.getDistrict());
            address.setStreet(addressDTO.getStreet());
            address.setBuilding(addressDTO.getBuilding());
            address.setFlat(addressDTO.getFlat());
            address.setPostCode(addressDTO.getPostcode());

            addressRepository.save(address);
            log.info("Inserted address data for user with address_id", user.getLogin(), address.getAddressId());
        }

    }

    /*
     * @Override
     * 
     * @Transactional public void registerUser(RegistrationForm
     * registrationForm) { TerritorialCommunity territorialCommunity =
     * communityService .findByName(registrationForm.getTerritorialCommunity());
     * User user = new User(); user.setLogin(registrationForm.getLogin());
     * user.setEmail(registrationForm.getEmail());
     * user.setPasswordHash(userPasswordEncoder
     * .encode(registrationForm.getPassword()));
     * user.setFirstName(registrationForm.getFirstName());
     * user.setLastName(registrationForm.getLastName());
     * user.setMiddleName(registrationForm.getMiddleName());
     * user.setPhoneNumber(registrationForm.getPhoneNumber());
     * user.setRole(roleRepository.findRoleByType(RoleType.USER));
     * user.setStatus(UserStatus.INACTIVE);
     * user.setPhoneNumber(registrationForm.getPhoneNumber());
     * user.setDateOfAccession(registrationForm.getDateOfAccession());
     * user.setTerritorialCommunity(territorialCommunity);
     * 
     * userRepository.save(user); log.info(
     * "Inserted new user data into 'users' table: user_id = " +
     * user.getUserId());
     * 
     * if (userRepository.findUserByLogin(user.getLogin()) != null) { // insert
     * user's passport data into "passport_data" table PassportInfo passport =
     * new PassportInfo(); passport.setUser(user);
     * passport.setSeria(registrationForm.getSeria());
     * passport.setNumber((registrationForm.getNumber()));
     * passport.setPublishedByData(registrationForm.getPublishedByData());
     * 
     * passportRepository.save(passport); log.info(
     * "Inserted passport data for user with passport_data_id", user.getLogin(),
     * passport.getPassportId());
     * 
     * // insert user's address records into "address" table Address address =
     * new Address(); address.setUser(user);
     * address.setCity(registrationForm.getCity());
     * address.setRegion(registrationForm.getRegion());
     * address.setDistrict(registrationForm.getDistrict());
     * address.setStreet(registrationForm.getStreet());
     * address.setBuilding(registrationForm.getBuilding());
     * address.setFlat(registrationForm.getFlat());
     * address.setPostCode(registrationForm.getPostcode());
     * 
     * addressRepository.save(address); log.info(
     * "Inserted address data for user with address_id", user.getLogin(),
     * address.getAddressId()); }
     * 
     * }
     */
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

    @Override
    public List<UserDTO> getUserBySearchTag(String searchTag) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User registrator = getUserByLogin(auth.getName());
        List<User> usersList = userRepository.findOwnersLikeProposed(registrator.getTerritorialCommunity(), searchTag);
        List<UserDTO> userDtos = new ArrayList<UserDTO>();
        for (User user : usersList) {
            UserDTO userdto = new UserDTO();
            userdto.setFirstName(user.getFirstName());
            userdto.setMiddleName(user.getMiddleName());
            userdto.setLastName(user.getLastName());
            userdto.setLogin(user.getLogin());
            userDtos.add(userdto);
        }
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
        ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);
        Tome tome = tomeRepository.findTomeByRegistrator(user);
        ResourceNumberJson resourceNumberJson = null;
        if ((tome != null) && (resourceNumber != null)) {
            resourceNumberJson = new ResourceNumberJson(resourceNumber.getNumber().toString(),
                    resourceNumber.getRegistratorNumber(), tome.getIdentifier());
        } else {
            resourceNumberJson = new ResourceNumberJson();
            resourceNumberJson = new ResourceNumberJson();
            resourceNumberJson.setResource_number("0");
            resourceNumberJson.setRegistrator_number("0");
            resourceNumberJson.setIdentifier("0");
        }
        UserDTO userdto = new UserDTO(user.getFirstName(), user.getLastName(), user.getMiddleName(),
                user.getRole().toString(), user.getLogin(), user.getEmail(), user.getStatus().toString(), addressDto,
                passportDto, user.getTerritorialCommunity().getName(), resourceNumberJson);
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

    @Override
    public User findUserByEmail(String email) {
        return userRepository.getUserByEmail(email);
    }

    /**
     * Method, which creates tome and number of resource
     *
     * @return void
     */
    @Transactional
    @Override
    public void createTomeAndRecourceNumber(UserDTO userDto) {
        User user = userRepository.findUserByLogin(userDto.getLogin());
        Tome tome = tomeRepository.findTomeByRegistrator(user);
        ResourceNumber resourceNumber = resourceNumberRepository.findResourceNumberByUser(user);

        if (tome != null && resourceNumber != null && userDto.getResourceNumberJson() != null) {
            ResourceNumberJson resourceNumberJson = userDto.getResourceNumberJson();
            if (resourceNumberJson.getRegistrator_number() == null || resourceNumberJson.getResource_number() == null) {
                logger.error("Bad ResourceNumberJson data");
                return;
            }

            ResourceNumberDTO resourseNumberDto = new ResourceNumberDTO(
                    Integer.parseInt(resourceNumberJson.getResource_number()),
                    resourceNumberJson.getRegistrator_number());

            resourceNumber.setRegistratorNumber(resourseNumberDto.getRegistratorNumber());
            resourceNumber.setNumber(resourseNumberDto.getNumber());

            resourceNumberRepository.save(resourceNumber);
        } else {
            List<User> userList = Collections.singletonList(user);
            batchCreateTomeAndResourceNumber(userList);
        }
    }

    @Transactional
    @Override
    public void batchCreateTomeAndResourceNumber(List<User> users) {
        if (users.isEmpty())return;

        List<Tome> tomeList = tomeRepository.findAll();
        Integer tomeNumber = 0;
        if (!tomeList.isEmpty()) {
            Tome tempTome = tomeList.get(tomeList.size() - 1);
            String lastTomeNum = tempTome.getIdentifier();
            tomeNumber = Integer.parseInt(lastTomeNum);
        }

        List<ResourceNumber> resourceNumberList = resourceNumberRepository
                .findResourceNumbersByCommunity(users.get(0).getTerritorialCommunity());
        Integer registratorNumber = 0;
        Integer registratorStartIncrement = 1;
        for (ResourceNumber res : resourceNumberList) {
            try{
                Integer tmpNumber = Integer.parseInt(res.getRegistratorNumber());
                registratorNumber = (tmpNumber > registratorNumber) ? tmpNumber : registratorNumber;
            }catch(NumberFormatException e){
                logger.warn("Resource number of user \""+res.getUser().getLogin()+"\" is in a incorrect format: "+res.getRegistratorNumber()+". Only decimals allowed.");
            }
        }

        for (User user : users) {
            Tome userTome = tomeRepository.findTomeByRegistrator(user);
            if (userTome == null) {
                tomeNumber++;
                String newTomeId = tomeNumber.toString();
                Tome newUserTome = new Tome(user, newTomeId);
                tomeRepository.save(newUserTome);
            }

            ResourceNumber userResourceNumber = resourceNumberRepository.findResourceNumberByUser(user);

            if (userResourceNumber == null) {
                registratorNumber++;
                ResourceNumber newResourceNumber = new ResourceNumber(registratorStartIncrement,
                        registratorNumber.toString(), user);
                resourceNumberRepository.save(newResourceNumber);
            } else {
                String foundRegistratorNumber = userResourceNumber.getRegistratorNumber();
                ResourceNumber registratorWithSameNumber = null;

                for (ResourceNumber resNum : resourceNumberList) {
                    if (resNum.getRegistratorNumber().equals(foundRegistratorNumber)) {
                        if (registratorWithSameNumber == null) {
                            registratorWithSameNumber = resNum;
                        }
                    }
                }

                if (registratorWithSameNumber != null
                        && !registratorWithSameNumber.getUser().equals(userResourceNumber.getUser())) {

                    registratorNumber++;
                    userResourceNumber.setRegistratorNumber(registratorNumber.toString());
                    resourceNumberRepository.save(userResourceNumber);
                }
            }
        }

    }

    /**
     * <p> Method, which make updates in user entity for preventing brute force attacks </p>
     *
     * @param String login
     * @return void
     * @author Vitalii Horban
     */

    @Transactional
    @Override
    public void updateFailAttempts(String login) {

        try {
            User user = userRepository.findUserByLogin(login);

            // if user failed to login
            if (user != null) {

                int previousAttempts = user.getAttempts();
                user.setAttempts(previousAttempts + 1);
                user.setLastModified(new Timestamp(System.currentTimeMillis()));

                if (user.getAttempts() + 1 > MAX_ATTEMPTS) {
                    user.setAccountNonLocked(0);
                }

            }
        } catch (Exception e) {
            logger.error("Failed to updateFailAttempts() " + e);
        }
    }

    /**
     * <p> Method, which reset user attempts to zero after authentifacation </p>
     *
     * @param String login
     * @return void
     * @author Vitalii Horban
     */
    @Transactional
    @Override
    public void resetFailAttempts(String login) {
        try {
            User user = userRepository.findUserByLogin(login);
            user.setAttempts(0);
            user.setLastModified(null);
        } catch (Exception e) {
            logger.error("Failed to resetFailAttempts() " + e);
        }

    }

    @Override
    public User findUserByLogin(String login) {
        return userRepository.findUserByLogin(login);
    }

    @Transactional
    @Override
    public void resetAllFailAttempts() {
        try {
            List<User> allUsers = userRepository.findAll();
            for (User u : allUsers) {
                u.setAccountNonLocked(1);
                u.setAttempts(0);
            }
        } catch (Exception e) {
            logger.error("Failed to resetAllFailAttempts() " + e);
        }

    }

    /* Batch Role change */

    /**
     * Method to provide batch operation over users to perform "from the list" mass role
     * assignment.
     *
     * @param batch RoleTypeJson - holds JSON data of type {users logins(comma separator), role type
     *              and info, needed to setup RoleType.REGISTRATOR}
     * @return String message, which is passed to the controller to return a decent HTTP status with
     * info.
     * @author ATsyhanenko
     */
    @Transactional
    @Override
    public String batchRoleChange(RoleTypeJson batch) {
        logger.info("Recieved package: " + batch);
        if (batch.getLogin() == null || batch.getRole() == null) {
            logger.warn("Empty RoleTypeJson batch file");
            return "msg.batchops.wrongInput";
        }

        List<String> givenUsers = new ArrayList<String>();
        Collections.addAll(givenUsers, batch.getLogin().split(","));

        List<User> userList = userRepository.findUsersByLoginList(givenUsers);

        Role role = checkRole(batch.getRole());
        logger.info("Selected role: " + role);
        logger.info("Performing Change Role operations");

        for (User user : userList) {
            user.setRole(role);
            userRepository.save(user);
        }

        if (role.getType() == RoleType.REGISTRATOR) {
            batchCreateTomeAndResourceNumber(userList);
        }

        return "msg.batchops.changesaccepted";
    }

    /* Batch Community change */

    /**
     * Method to provide batch operation over users to perform "from the list" mass comunity
     * assignment.
     *
     * @param batch CommunityParamJson - holds JSON data of type {users logins(comma separator),
     *              communityId}
     * @return String message, which is passed to the controller to return a decent HTTP status with
     * info.
     * @author ATsyhanenko
     */
    @Transactional
    @Override
    public String batchCommunityChange(CommunityParamJson batch) {
        logger.info("Recieved package: " + batch);

        if (batch.getLogin() == null || batch.getCommunityId() == null) {
            logger.warn("Empty CommunityParamJson batch file");
            return "msg.batchops.wrongInput";
        }

        List<String> givenUsers = new ArrayList<String>();
        Collections.addAll(givenUsers, batch.getLogin().split(","));

        List<User> userList = userRepository.findUsersByLoginList(givenUsers);

        Integer commumityId = Integer.parseInt(batch.getCommunityId());
        TerritorialCommunity community = communityService.findById(commumityId);
        if (community == null) {
            logger.warn("Incorrect community id");
            return "msg.batchops.wrongInput";
        }
        
        logger.info("Selected Community: " + community.getName());

        Role role = checkRole("USER");

        for (User user : userList) {
            user.setRole(role);
            user.setTerritorialCommunity(community);
        }

        return "msg.batchops.changesaccepted";
    }


    @Override
    public boolean isAuthenticated() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null &&
               auth.isAuthenticated() &&
               !(auth instanceof AnonymousAuthenticationToken);
    }

    @Override
    public User getLoggedUser() {
        if (!isAuthenticated()) {
            return null;
        }

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return getUserByLogin(auth.getName());
    }
}