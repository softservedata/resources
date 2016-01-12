package org.registrator.community.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.registrator.community.dao.UserRepository;
import org.registrator.community.dao.specification.SpecificationsBuilder;
import org.registrator.community.dto.search.PersonDemoDTO;
import org.registrator.community.dto.search.SearchColumn;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponceDTO;
import org.registrator.community.entity.User;
import org.registrator.community.service.UserSearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
public class UserSearchServiceImpl implements UserSearchService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public TableSearchResponceDTO<PersonDemoDTO> getTableSearchResponce(
			TableSearchRequestDTO searchRequest) {
		
		long count;
		List<User> userList;
		TableSearchResponceDTO<PersonDemoDTO> tableResponse;
		int draw=searchRequest.getDraw();
		Pageable page = getPageRequest(searchRequest.getStart(), searchRequest.getLength());
		
		List<SearchColumn> columsList = searchRequest.getColumns();
		if(checkForAllParametersSearch(columsList)==true){
			userList=userRepository.findAll(page).getContent();
			count = userRepository.count();
		}
		else{
			SpecificationsBuilder sb = new SpecificationsBuilder();
			sb.setColums(columsList);
			Specification<User> userSpecification=sb.build();
			count = userRepository.count(userSpecification);
			userList=userRepository.findAll(userSpecification,page).getContent();		
		}
			
		tableResponse = fillTableResponceDTO(draw,count,userList);	
		return tableResponse;
	}
	
	private boolean checkForAllParametersSearch(List<SearchColumn> searchColumnList){
		for(SearchColumn searchColumn : searchColumnList){
			String searchValue=searchColumn.getSearch().getValue();
			if(searchValue!="" && searchValue!=null){
				return false;
			}
		}		
		return true;
	}
	
	private Pageable getPageRequest(int start,int length){
		int pageNumber = convertStartToPageNumber(start, length);
		return new PageRequest(pageNumber, length);
	}
	
	private List<PersonDemoDTO> fillResponseDataList(List<User> userList){
		List<PersonDemoDTO> personList = new ArrayList<PersonDemoDTO>();
		for(User user : userList){
			PersonDemoDTO p =new PersonDemoDTO();
			p.setFirstName(user.getFirstName());
			p.setLastName(user.getLastName());
			p.setMiddleName(user.getMiddleName());
			p.setEmail(user.getEmail());
			personList.add(p);
		}
		return personList;
	}
	
	private TableSearchResponceDTO<PersonDemoDTO> fillTableResponceDTO(int draw,long count,List<User> userList){
		
		TableSearchResponceDTO<PersonDemoDTO> tableResponse = new TableSearchResponceDTO<PersonDemoDTO>();
		List<PersonDemoDTO> personList=fillResponseDataList(userList);
		
		tableResponse.setDraw(draw);
		tableResponse.setRecordsTotal(count);
		tableResponse.setRecordsFiltered(count);
		tableResponse.setData(personList);
		
		return tableResponse;
	}
	
	private int convertStartToPageNumber(int start, int length){
		if(start!=0){
			return start/length;
		}
		else{
			return 0;
		}
	}
	
}