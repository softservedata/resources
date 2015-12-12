package org.registrator.community.dao;

import org.registrator.community.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends PagingAndSortingRepository<User,String> {

	public Page<User> findAll(Pageable pageable);
	
	@Query("Select u"+
			" From User u" +
			" Where u.login = :login")
	public User findUserByLogin(@Param("login")String login);
	
	
}
