package org.registrator.community.service.impl;

import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
public class CronService {
	@Autowired
	private UserRepository userRepository;
	
	
	 
	 @Scheduled(fixedRate=120000) 
	 @Transactional
		public void resetFailAttempts() {
			List<User> allUsers=userRepository.findAll();
				for (User u : allUsers) {
					u.setAccountNonLocked(1);
					u.setAttempts(0);
					System.err.println("All users logins are unlocked");
				}
			
			
		}
	 
	 
	
	 
	 
	 
}
