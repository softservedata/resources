package org.registrator.community.service.search;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaQuery;

import org.registrator.community.dao.specification.SpecificationsBuilder;
import org.registrator.community.dto.search.SearchColumn;
import org.registrator.community.dto.search.TableSearchRequestDTO;
import org.registrator.community.dto.search.TableSearchResponseDTO;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.core.EntityInformation;

public abstract class AbstractSearchService<T,R extends JpaSpecificationExecutor<T> 
	& PagingAndSortingRepository<T,? extends Serializable>>  implements BaseSearchService<T>{

	public TableSearchResponseDTO getTableSearchResponse(
			TableSearchRequestDTO searchRequest,R repository) {
		
		long count;
		List<T> entityList;
		TableSearchResponseDTO tableResponse;
		int draw=searchRequest.getDraw();
		Pageable page = getPageRequest(searchRequest.getStart(), searchRequest.getLength());
		
		List<SearchColumn> columsList = searchRequest.getColumns();
		if(checkForAllParametersSearch(columsList)){
			entityList = repository.findAll(page).getContent();
			count = repository.count();
		}
		else{
			SpecificationsBuilder<T> sb = new SpecificationsBuilder<T>();
			sb.setColums(columsList);
			Specification<T> userSpecification=sb.build();
			count = repository.count(userSpecification);
			entityList = repository.findAll(userSpecification,page).getContent();		
		}
			
		tableResponse = fillTableResponseDTO(draw,count,entityList);	
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
	
	public abstract List<Map<String,String>> fillResponseDataList(List<T> searchEntityList);
	
	private TableSearchResponseDTO fillTableResponseDTO(int draw,long count,List<T> userList){
		
		TableSearchResponseDTO tableResponse = new TableSearchResponseDTO();
		List<Map<String,String>> personList=fillResponseDataList(userList);
		
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
