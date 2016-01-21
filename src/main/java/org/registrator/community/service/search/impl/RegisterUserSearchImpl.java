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
	UserRepository userRepository;

	@Override
	public TableSearchResponseDTO executeSearchRequest(
			TableSearchRequestDTO searchRequest) {
		return getTableSearchResponse(searchRequest, userRepository);
	}

	@Override
	public List<Map<String, String>> fillResponseDataList(
			List<User> searchEntityList) {
		
		List<Map<String,String>> personList = new ArrayList<Map<String,String>>();
		for(User user : searchEntityList){
			Map<String,String> map = new HashMap<String,String>();
			map.put("firstName", user.getFirstName());
			map.put("lastName", user.getLastName());
			map.put("middleName", user.getMiddleName());
			map.put("login", user.getLogin());
			map.put("address_city", user.getAddress().get(0).getCity());
			map.put("address_street", user.getAddress().get(0).getStreet());
			map.put("passport_seria", user.getPassport().get(0).getSeria());
			map.put("passport_number", user.getPassport().get(0).getNumber().toString());
			personList.add(map);	
		}
		return personList;
		
	}

}
