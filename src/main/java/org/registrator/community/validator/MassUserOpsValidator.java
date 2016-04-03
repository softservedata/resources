package org.registrator.community.validator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.json.CommunityParamJson;
import org.registrator.community.dto.json.RoleTypeJson;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.enumeration.RoleType;
import org.registrator.community.service.CommunityService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class MassUserOpsValidator implements Validator {

    @Autowired
    private CommunityService communityService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private Logger logger;
    
    private List<User> userList = new ArrayList<User>();
    private static final String WRONG_INPUT = "msg.batchops.wrongInput",
                                IS_ADMIN = "msg.batchops.cantChangeAdmins",
                                CANT_CHANGE_SELF = "msg.batchops.cantChangeOwnState",
                                DIFFIRENT_TCS = "msg.batchops.moreThenOneTC";

    @Override
    public boolean supports(Class<?> clazz) {
        boolean supports = false;
        supports = (clazz.equals(CommunityParamJson.class) || clazz.equals(RoleTypeJson.class))
                ? true : false;
        return supports;
    }

    @Override
    public void validate(Object target, Errors errors) {
        if (target instanceof CommunityParamJson) {
            CommunityParamJson communityParamBatch = (CommunityParamJson) target;

            if (communityParamBatch.getLogin() == null
                    || communityParamBatch.getCommunityId() == null) {
                logger.warn("Empty RoleTypeJson batch file");
                errors.reject(WRONG_INPUT);
                return;
            }
            
            if(!checkIfUsersExist(communityParamBatch.getLogin())){
                logger.warn("Bad login list");
                errors.reject(WRONG_INPUT);
                return;
            }
            
            if(!checkIfIsSelf()){
                logger.warn("Tried to change self's role");
                errors.reject(CANT_CHANGE_SELF);
                return;
            }     
             
            if(!checkIfIsAdmin()){
                logger.warn("Cant change admins");
                errors.reject(IS_ADMIN);
                return;
            }
            
            if(!checkIfCommunityExists(communityParamBatch.getCommunityId())){
                logger.warn("Community with id# \""+communityParamBatch.getCommunityId()+"\" doesnt exist");
                errors.reject(WRONG_INPUT);
                return;
            }

        } else if (target instanceof RoleTypeJson) {
            RoleTypeJson roleTypeParamBatch = (RoleTypeJson) target;

            if (roleTypeParamBatch.getLogin() == null || roleTypeParamBatch.getRole() == null) {
                logger.warn("Empty RoleTypeJson batch file");
                errors.reject(WRONG_INPUT);
                return;
            }
            
            if(!checkIfUsersExist(roleTypeParamBatch.getLogin())){
                logger.warn("Bad login list");
                errors.reject(WRONG_INPUT);
                return;
            }
            
            if(!checkIfIsSelf()){
                logger.warn("Tried to change self's role");
                errors.reject(CANT_CHANGE_SELF);
                return;
            }
                     
            if(!checkIfIsAdmin()){
                logger.warn("Cant change admins");
                errors.reject(IS_ADMIN);
                return;
            }
            
            if(!checkIfIsFromSameCommunity()){
                logger.warn("Cant select users with diffirent communities");
                errors.reject(DIFFIRENT_TCS);
                return;
            }

            if(!checkIfRoleExists(roleTypeParamBatch.getRole())){
                logger.warn("Role \""+roleTypeParamBatch.getRole()+"\" doesnt exist");
                errors.reject(WRONG_INPUT);
                return;
            }
        }
    }

    private boolean checkIfUsersExist(String userLogins){
        List<String> givenUsers = new ArrayList<String>();
        Collections.addAll(givenUsers, userLogins.split(","));
        
        userList = userRepository.findUsersByLoginList(givenUsers);
        return !userList.isEmpty();
    }
    
    private boolean checkIfIsAdmin() {
        for (User user : userList) {
            if (user.getRole().getType() == RoleType.ADMIN) {
                return false;
            }
        }
        return true;

    }

    private boolean checkIfIsSelf() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String autherizedUser = auth.getName();
        logger.info("Performed by: " + autherizedUser);

        for (User user : userList) {
            if (user.getLogin().equals(autherizedUser)) {
                return false;
            }
        }
        return true;
    }

    private boolean checkIfIsFromSameCommunity() {
        int territorialCommunityId = -1;
        for (User user : userList) {
            int tmp = user.getTerritorialCommunity().getTerritorialCommunityId();
            if (territorialCommunityId == -1) {
                territorialCommunityId = tmp;
            } else {
                if (territorialCommunityId != tmp) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkIfRoleExists(String roleName) {
        try{
            RoleType.valueOf(roleName);
        }catch(Exception e){
            return false;
        }
        return true;
    }

    private boolean checkIfCommunityExists(String communityId) {
        Integer parsedCommunityId = new Integer(-1);
        try{
            parsedCommunityId = Integer.parseInt(communityId);
        }catch(Exception e){
            return false;
        }
        TerritorialCommunity community = communityService.findById(parsedCommunityId);
        return (community != null);
    }
}
