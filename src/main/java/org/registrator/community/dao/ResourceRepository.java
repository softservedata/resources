package org.registrator.community.dao;

import org.registrator.community.entity.Resource;
import org.registrator.community.entity.ResourceType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ResourceRepository extends JpaRepository<Resource, Long> {

    @Query("Select r" +
            " From Resource r " +
            "Where r.identifier = :identifier")
    public Resource findResourceByIdentifier(@Param("identifier") String identifier);

    List<Resource> findByType(ResourceType type);

    long count();

}

