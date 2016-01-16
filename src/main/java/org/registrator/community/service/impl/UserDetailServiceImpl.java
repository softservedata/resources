package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

	@Autowired
	UserRepository userRepository;

	@Autowired
	UserService userService;

	private static final Logger logger = LoggerFactory.getLogger(UserDetailServiceImpl.class);
	
	@Override
	public UserDetails loadUserByUsername(final String login)
			throws UsernameNotFoundException {

		org.registrator.community.entity.User userEntity = userRepository.findUserByLogin(login);

		if (userEntity == null){
			logger.error("User - {} - not found");
			throw new UsernameNotFoundException("Помилка в паролі, чи емейлі");
		}
		else{
			logger.info("Requested user - {} - is found: ", userEntity.getLogin());
		}

		List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
		authorities.add(new SimpleGrantedAuthority("ROLE_"+ userEntity.getRole()));

		return buildUserForAuthentication(userEntity,authorities);
	}
	
	private User buildUserForAuthentication(org.registrator.community.entity.User userEntity, List<GrantedAuthority> authorities) {
		//return new User(userEntity.getFirstName()+"  "+userEntity.getLastName(), userEntity.getPassword(), true, true, true, true, authorities);
		
		//userService.login(userEntity.getLogin(), userEntity.getPassword());
		//return new User(userEntity.getLogin(), userRepository.getUsersPasswordHash(userEntity.getPassword()), authorities);
		return new User(userEntity.getLogin(), userEntity.getPassword(), authorities);
	}
	
}
