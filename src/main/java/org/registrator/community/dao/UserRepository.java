package org.registrator.community.dao;

import org.registrator.community.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User,Integer>, JpaSpecificationExecutor<User>{

	Page<User> findAll(Pageable pageable);

	@Query("select u from User u where u.login = :login")
	User findUserByLogin(@Param("login")String login);

	@Query("select u from User u where u.login = :login and u.password = :password")
	User getUserByLoginAndPassword(@Param("login") String loginName, @Param("password") String password);

	@Query("select u from User u where u.email = :email")
	User getUserByEmail(@Param("email") String email);

	@Query("select u.password from User u where u.login = :login")
	String getUsersPasswordHash(@Param("login") String password);

}
