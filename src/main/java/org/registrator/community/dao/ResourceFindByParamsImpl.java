package org.registrator.community.dao;

import org.registrator.community.dto.JSON.ResourseSearchJson;
import org.registrator.community.entity.*;
import org.registrator.community.enumeration.ResourceParameterCompare;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements search of resources by parameters. Adds additional behavior to ResourceRepository
 */

@Component
public class ResourceFindByParamsImpl {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LinearParameterRepository linearParameterRepository;

    @Autowired
    private DiscreteParameterRepository discreteParameterRepository;

    /**
     * List of parameters for a query
     */
    private List<QueryParameter> queryParameters;

    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery<Resource> selection;
    private Root<Resource> criteriaRoot;

    /**
     * Default result size returned from a server
     */
    private static final int PAGE_SIZE = 200;

    public ResourceFindByParamsImpl() {}

    /**
     * Search of resources by parameters. All parameters comparisons are combined with AND logical operation
     * @param parameters resource parameters and values for searching
     * @return List fo found resources
     */
    public List<Resource> findByParams(ResourseSearchJson parameters) {
        parseParameters(parameters);

        if (selection.getRestriction() == null) {
            selection.where(criteriaBuilder.and()); // always true
        }

        for (QueryParameter parameter : queryParameters) {
            selection.where(criteriaBuilder.and(
                    selection.getRestriction(),
                    parameter.getRestriction())
            );

        }

        int pageOffset = parameters.getPage()* PAGE_SIZE;
        TypedQuery<Resource> query = entityManager.createQuery(selection);

        query.setFirstResult(pageOffset);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();
    }

    /**
     * Parsing of clien-side parameters to internal representation
     * @param parameters parameters got from a client-side
     */
    private void parseParameters(ResourseSearchJson parameters) {

        criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Resource> criteria = criteriaBuilder.createQuery(Resource.class);
        criteriaRoot = criteria.from(Resource.class);
        selection = criteria.select(criteriaRoot).distinct(true);

        buildParametersList(parameters);
    }

    /**
     * This method needed to deal with fact that we have different parameter types DiscreteParameter and LinearParameter
     * it is a subject to further refactoring
     * @param parameters
     */
    private void buildParametersList(ResourseSearchJson parameters) {
        queryParameters = new ArrayList<>();

        queryParameters.addAll(getDiscreteParametersList(
                parameters.getDiscreteParamsIds(),
                parameters.getDiscreteParamsCompares(),
                parameters.getDiscreteParamsValues())
        );
        queryParameters.addAll(getLinearParametersList(
                parameters.getLinearParamsIds(),
                parameters.getLinearParamsValues())
        );

    }


    /** Creaters list of QueryParameter from list of DiscreteParameter
     */
    // TODO this is needed to be refactored when DiscreteParameter and LinearParameter will be in one hierarchy
    private List<QueryParameter> getDiscreteParametersList(List<Integer> idList, List<String> signList, List<Double> valueList) {

        List<QueryParameter> result = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            if (valueList.get(i) != null) {
                DiscreteParameter parameter = discreteParameterRepository.findByDiscreteParameterId(idList.get(i));
                result.add(new QueryParameter(parameter, ResourceParameterCompare.from(signList.get(i)),
                        valueList.get(i)));
            }
        }
        return result;
    }

    /** Creaters list of QueryParameter from list of DiscreteParameter
     */
    // TODO this is needed to be refactored when DiscreteParameter and LinearParameter will be in one hierarchy
    private List<QueryParameter> getLinearParametersList(List<Integer> idList, List<Double> valueList) {

        List<QueryParameter> result = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            if (valueList.get(i) != null) {
                LinearParameter parameter = linearParameterRepository.findByLinearParameterId(idList.get(i));
                result.add(new QueryParameter(parameter, ResourceParameterCompare.LINEAR, valueList.get(i)));
            }
        }
        return result;
    }

    /**
     * Combination of resource parameter, compare operation of a query and value of parameter
     */
    // TODO this is a basis for refactoring ResourseSearchJson class
    public class QueryParameter {
        private Object parameterEntity;
        private ResourceParameterCompare operation;
        private double value;
        private Join<Resource, ?> join;

        public QueryParameter(Object parameter, ResourceParameterCompare operation,
                              double value) {
            this.parameterEntity = parameter;
            this.operation = operation;
            this.value = value;
            if (parameterEntity instanceof DiscreteParameter) {
                this.join = criteriaRoot.join("resourceDiscreteValues");
            } else {
                this.join = criteriaRoot.join("resourceLinearValues");
            }

        }

        /**
         * @return Path of ID column for parameter entity in a query for this parameter
         */
        public Path getParameterPath() {

            if (parameterEntity instanceof DiscreteParameter) {
                return join.get("discreteParameter");
            } else {
                return join.get("linearParameter");
            }
        }

        /**
         * Returns Path of value column for parameter
         * @param attributeName name of an attribute in parameter class
         * @return Path of value column for parameter
         */
        public Path<Double> getValuePath(String attributeName) {
            return join.get(attributeName);
        }

        /**
         * @return value for the search
         */
        public double getValue() {
            return value;
        }

        /**
         * Creates restriction for this parameter, compare operation and value
         * @return resrriction for a where clause of a query
         */
        public Predicate getRestriction() {
            return criteriaBuilder.and(
                    criteriaBuilder.equal(getParameterPath(), parameterEntity), // equality for parameter id
                    operation.getRestriction(criteriaBuilder, this)             // restriction from compare sign
            );
        }
    }

}
