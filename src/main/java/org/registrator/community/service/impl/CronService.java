package org.registrator.community.service.impl;

import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.entity.User;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 * This class give opportunity to reset user attempts every time at midnight
 * </p>
 */

@Service
public class CronService {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private Logger logger;
	/**
	 * <p>
	 * reset user attempts to login to zero every time at midnight
	 * </p>
	 * 
	 * @return void
	 */
	@Scheduled(cron = "0 0 0 * * *")
	@Transactional
	public void resetAllFailAttempts() {
		try {
			List<User> allUsers = userRepository.findAll();
			for (User u : allUsers) {
				u.setAccountNonLocked(1);
				u.setAttempts(0);
			}
		} catch (Exception e) {
			logger.error("Failed to resetAllFailAttempts() with cron expressions " + e);
		}

	}

}
