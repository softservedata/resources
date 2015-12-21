package org.registrator.community.dao;
import java.util.List;

import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.ResourceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface LinearParameterRepository extends JpaRepository<LinearParameter, Integer>{
	
}
