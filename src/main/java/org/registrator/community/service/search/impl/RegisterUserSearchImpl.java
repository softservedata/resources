package org.registrator.community.service.search.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;
import org.registrator.community.entity.User;
import org.registrator.community.service.search.AbstractSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("registerUserSearchService")
public class RegisterUserSearchImpl extends AbstractSearchService<User,UserRepository> {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public TableSearchResponseDTO executeSearchRequest(
			TableSearchRequestDTO searchRequest) {
		return getTableSearchResponse(searchRequest, userRepository);
	}

	@Override
	public List<Map<String, String>> fillResponseDataList(
			List<User> searchEntityList) {
		
		if(searchEntityList != null){
			List<Map<String,String>> personList = new ArrayList<Map<String,String>>();
			for(User user : searchEntityList){
				Map<String,String> map = new HashMap<String,String>();
				map.put("0", user.getStatus().toString());
				map.put("1", user.getFirstName());
				map.put("2", user.getLastName());
				map.put("3", user.getLogin());
				map.put("4", user.getTerritorialCommunity().getName());
				map.put("5", user.getEmail());
				map.put("6", user.getRole().toString());

				personList.add(map);	
			}
			return personList;
		}
		return null;	
	}

}
