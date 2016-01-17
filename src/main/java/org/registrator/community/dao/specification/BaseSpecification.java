package org.registrator.community.dao.specification;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.registrator.community.dto.search.SearchColumn;
import org.springframework.data.jpa.domain.Specification;

public class BaseSpecification<T> implements Specification<T>{
	
	private SearchColumn criteria;
	
	public SearchColumn getCriteria() {
		return criteria;
	}

	public void setCriteria(SearchColumn criteria) {
		this.criteria = criteria;
	}

	@Override
	public Predicate toPredicate(Root<T> root, CriteriaQuery<?> query,
			CriteriaBuilder builder) {
		if (criteria.getSearch().getCompareSign().equalsIgnoreCase("greater")) {
            return builder.greaterThanOrEqualTo(
              root.<String> get(criteria.getData()), criteria.getSearch().getValue());
        } 
        else if (criteria.getSearch().getCompareSign().equalsIgnoreCase("less")) {
            return builder.lessThanOrEqualTo(
              root.<String> get(criteria.getData()), criteria.getSearch().getValue());
        } 
        else if (criteria.getSearch().getCompareSign().equalsIgnoreCase("like")) {
            if (root.get(criteria.getData()).getJavaType() == String.class) {
                return builder.like(
                  root.<String>get(criteria.getName()), "%" + criteria.getSearch().getValue() + "%");
            }
            return builder.lessThanOrEqualTo(getPathFromRoot( root)
                    , criteria.getSearch().getValue());
        }
        else if (criteria.getSearch().getCompareSign().equalsIgnoreCase("equal")){
        	return builder.equal(getPathFromRoot(root), criteria.getSearch().getValue());
        }
        return null;
	}
	
	public Path<String> getPathFromRoot(Root<T> root){
		if(criteria.getData().contains("_")){
			String[]pathParameters = criteria.getData().split("_");
			return root.join(pathParameters[0]).get(pathParameters[1]);
		}
		return root.get(criteria.getData());
	}
}