package org.registrator.community.dao;

import org.registrator.community.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long>{
	
	@Query("select t from PasswordToken t where t.userEmail = :userEmail")
	public VerificationToken findTokenByEmail(@Param("userEmail") String userEmail);
	
}
