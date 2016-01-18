package org.registrator.community.dao;

import org.registrator.community.entity.ResourceNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ResourceNumberRepository extends JpaRepository<ResourceNumber, String> {

//	@Query("select user from registration_number_of_the_resource user where user.user_id=:id")
//	public String findById(@Param("id") Integer id);
}
