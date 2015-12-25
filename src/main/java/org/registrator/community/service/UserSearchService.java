package org.registrator.community.service;

import org.registrator.community.dto.search.PersonDemoDTO;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponceDTO;

public interface UserSearchService {
	
	public TableSearchResponceDTO<PersonDemoDTO> getTableSearchResponce(TableSearchRequestDTO searchRequest);

}
