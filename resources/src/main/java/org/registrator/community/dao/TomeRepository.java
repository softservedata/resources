package org.registrator.community.dao;

import org.registrator.community.entity.Tome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface TomeRepository extends JpaRepository<Tome,Integer> {
	
	@Query("Select t" +
			" From Tome t " +
			"Where t.identifier = :identifier")
	public Tome findTomeByIdentifier(@Param("identifier") String identifier);

}
