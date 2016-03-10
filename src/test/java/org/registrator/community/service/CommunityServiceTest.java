package org.registrator.community.service;

import java.util.ArrayList;
import java.util.Arrays;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.registrator.community.dao.CommunityRepository;
import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.TerritorialCommunity;
import org.registrator.community.entity.User;
import org.registrator.community.service.impl.CommunityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class CommunityServiceTest {
	
	private Logger testLogger = LoggerFactory.getLogger(CommunityServiceTest.class);
	
	// test data
	private final Integer ID = 1;
	private final String NAME = "TestName";
	TerritorialCommunity tc;
	//end test data
	
	@InjectMocks
	private CommunityService communityService = new CommunityServiceImpl();
	
	@Mock
	private CommunityRepository communityRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private Logger logger;

	@BeforeMethod
    public void init() {
        MockitoAnnotations.initMocks(this);
        tc = new TerritorialCommunity(NAME);
		tc.setTerritorialCommunityId(ID);
    }
	
	@BeforeClass
	public void startClass(){
		testLogger.info("Start: CommunityServiceTest");
	}
	
	@AfterClass
	public void endClass(){
		testLogger.info("End: CommunityServiceTest");
	}
	
	
	@Test
	public void findAll(){
		testLogger.info("Start");
		Mockito.when(communityRepository.findAll()).thenReturn(new ArrayList<TerritorialCommunity>(Arrays.asList(tc)));
		TerritorialCommunity actualTC = communityService.findAll().get(0);
		Assert.assertEquals(actualTC.getName(), NAME);
		Assert.assertEquals(actualTC.getTerritorialCommunityId(), ID);
		testLogger.info("End");
	}
	
	@Test
	public void findByName(){
		testLogger.info("Start");
		Mockito.when(communityRepository.findByName(NAME)).thenReturn(tc);
		Assert.assertEquals(communityService.findByName(NAME).getName(), NAME);
		Assert.assertEquals(communityService.findByName(NAME).getTerritorialCommunityId(), ID);
		testLogger.info("End");
	}
	
	@Test
	public void addCommunity(){
		testLogger.info("Start");
		Mockito.when(communityRepository.saveAndFlush(tc)).thenReturn(tc);
		Assert.assertEquals(communityService.addCommunity(tc).getName(), NAME);
		Assert.assertEquals(communityService.addCommunity(tc).getTerritorialCommunityId(), ID);
		testLogger.info("End");
	}
	
	@Test
	public void findById(){
		testLogger.info("Start");
		Mockito.when(communityRepository.findOne(ID)).thenReturn(tc);
		//Mockito.doReturn(tc).when(communityRepository).findOne(ID);
		Assert.assertEquals(communityService.findById(ID).getName(), NAME);
		Assert.assertEquals(communityService.findById(ID).getTerritorialCommunityId(), ID);
		testLogger.info("End");
	}
	
	@Test
	public void deleteCommunity(){
		testLogger.info("Start");
		Assert.assertTrue(communityService.deleteCommunity(tc));// try to delete, if success then return true
		Mockito.verify(logger, Mockito.atLeastOnce()).info(Mockito.anyString());// do not forgot about logging
		Mockito.verify(communityRepository).delete(tc); // check whether method have been call
		Mockito.when(userRepository.findByTerritorialCommunity(tc)).thenReturn(new ArrayList<User>(Arrays.asList(new User())));// imitation of constraint
		Assert.assertFalse(communityService.deleteCommunity(tc)); // try to delete with constraint so method must return false
		Mockito.verifyZeroInteractions(communityRepository);// check whether  method "communityRepository.delete" have not been call at least one time
		Mockito.verify(logger, Mockito.atLeastOnce()).info(Mockito.anyString());// do not forgot about logging
		testLogger.info("End");
	}
	
	@Test
	public void findAllByAsc(){
		testLogger.info("Start");
		Mockito.when(communityRepository.findAllByAsc()).thenReturn(new ArrayList<TerritorialCommunity>(Arrays.asList(tc)));
		TerritorialCommunity actualTC = communityService.findAllByAsc().get(0);
		Assert.assertEquals(actualTC.getName(), NAME);
		Assert.assertEquals(actualTC.getTerritorialCommunityId(), ID);
		testLogger.info("End");
	}

}
