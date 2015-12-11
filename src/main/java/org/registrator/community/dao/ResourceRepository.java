package org.registrator.community.dao;

import org.registrator.community.entity.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface ResourceRepository extends PagingAndSortingRepository<Resource,String>{
	
		@Query("Select r" +
			" From Resource r " +
			"Where r.identifier = :identifier")
	public Resource findResourceByIdentifier(@Param("identifier") String identifier);
	
	

}
