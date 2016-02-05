package org.registrator.community.service.impl;

import java.util.List;

import org.registrator.community.dao.CommunityRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.service.CommunityService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommunityServiceImpl implements CommunityService{

    @Autowired
    private Logger logger;
    
    @Autowired
    private CommunityRepository communityRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Override
    public List<TerritorialCommunity> findAll() {
        return communityRepository.findAll();
    }
    @Override
    public TerritorialCommunity findByName(String name) {
        return communityRepository.findByName(name);
    }
    @Override
    public TerritorialCommunity addCommunity(TerritorialCommunity territorialCommunity) {
        return communityRepository.saveAndFlush(territorialCommunity);
    }
    @Override
    public TerritorialCommunity findById(Integer id) {
        return communityRepository.findOne(id);
    }
    @Override
    public boolean deleteCommunity(TerritorialCommunity territorialCommunity) {
        
        List<User> listOfUsers = userRepository.findByTerritorialCommunity(territorialCommunity);
        if(listOfUsers.isEmpty()){
            communityRepository.delete(territorialCommunity);
            logger.info("end: return true if list is empty");
            return true;
        }
        logger.info("end: return false if list isn't empty");
        return false;
        
    }
    @Override
    public List<TerritorialCommunity> findAllByAsc() {
        return communityRepository.findAllByAsc();
    }
}
