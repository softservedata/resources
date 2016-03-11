package org.registrator.community.dao;

import org.registrator.community.dto.JSON.ResourseSearchJson;
import org.registrator.community.entity.DiscreteParameter;
import org.registrator.community.entity.LinearParameter;
import org.registrator.community.entity.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Implements search of resources by parameters. Adds additional behavior to ResourceRepository
 */
@Repository
@Transactional(readOnly = true)
public class ResourceFindByParamsImpl implements ResourceFindByParams, Serializable {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private LinearParameterRepository linearParameterRepository;

    @Autowired
    private DiscreteParameterRepository discreteParameterRepository;

    /**
     * Default result size returned from a server
     */
    private static final int PAGE_SIZE = 200;

    /**
     * Search of resources by parameters. All parameters comparisons are combined with AND logical operation
     * @param parameters resource parameters and values for searching
     * @return List fo found resources
     */
    public List<Resource> findByParams(ResourseSearchJson parameters) {

        List<QueryParameter> queryParameters = parseParameters(parameters);

        int pageOffset = parameters.getPage() * PAGE_SIZE;

        return getResultList(queryParameters, pageOffset);

    }

    /**
     * @return result of the search
     */
    private List<Resource> getResultList(List<QueryParameter> queryParameters, int pageOffset) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Resource> criteria = criteriaBuilder.createQuery(Resource.class);
        Root<Resource> criteriaRoot = criteria.from(Resource.class);
        CriteriaQuery<Resource> selection = criteria.select(criteriaRoot).distinct(true);

        if (selection.getRestriction() == null) {
            selection.where(criteriaBuilder.and()); // always true
        }

        for (QueryParameter parameter : queryParameters) {

            selection.where(criteriaBuilder.and(
                    selection.getRestriction(),
                    parameter.getRestriction(criteriaBuilder, criteriaRoot))
            );

        }

        TypedQuery<Resource> query = entityManager.createQuery(selection);

        query.setFirstResult(pageOffset);
        query.setMaxResults(PAGE_SIZE);
        return query.getResultList();

    }


    /*
     * Parsing of client-side parameters to internal representation
     * @param parameters parameters got from a client-side
     */
    private List<QueryParameter> parseParameters(ResourseSearchJson parameters) {

        List<QueryParameter> queryParameters = new ArrayList<>();

        queryParameters.addAll(getDiscreteParametersList(
                parameters.getDiscreteParamsIds(),
                parameters.getDiscreteParamsCompares(),
                parameters.getDiscreteParamsValues())
        );
        queryParameters.addAll(getLinearParametersList(
                parameters.getLinearParamsIds(),
                parameters.getLinearParamsValues())
        );

        return queryParameters;

    }


    // Creates list of QueryParameter from list of DiscreteParameter
    private List<QueryParameter> getDiscreteParametersList(List<Integer> idList, List<String> signList, List<Double> valueList) {

        List<QueryParameter> result = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            if (valueList.get(i) != null) {
                DiscreteParameter parameterEntity = discreteParameterRepository.findByDiscreteParameterId(idList.get(i));
                QueryParameter queryParameter = new QueryParameter(parameterEntity, ResourceParameterCompare.from(signList.get(i)),
                        valueList.get(i));
                result.add(queryParameter);
            }
        }
        return result;
    }

    // Creates list of QueryParameter from list of LinearParameter
    private List<QueryParameter> getLinearParametersList(List<Integer> idList, List<Double> valueList) {

        List<QueryParameter> result = new ArrayList<>();
        for (int i = 0; i < idList.size(); i++) {
            if (valueList.get(i) != null) {
                LinearParameter parameterEntity = linearParameterRepository.findByLinearParameterId(idList.get(i));
                QueryParameter queryParameter= new QueryParameter(parameterEntity, ResourceParameterCompare.LINEAR,
                        valueList.get(i));
                result.add(queryParameter);
            }
        }
        return result;
    }

    /**
     * Combination of resource parameter, compare operation of a query and value of parameter
     */
    private static class QueryParameter {
        private Object parameterEntity;
        private ResourceParameterCompare operation;
        private double value;
        private Join<Resource, ?> join;

        public QueryParameter(Object parameter, ResourceParameterCompare operation, double value) {
            this.parameterEntity = parameter;
            this.operation = operation;
            this.value = value;


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
        public Double getValue() {
            return value;
        }

        /**
         * Creates restriction for this parameter, compare operation and value
         * @return resrriction for a where clause of a query
         */
        public Predicate getRestriction(CriteriaBuilder criteriaBuilder, Root<Resource> criteriaRoot) {
            if (parameterEntity instanceof DiscreteParameter) {
                this.join = criteriaRoot.join("resourceDiscreteValues");
            } else {
                this.join = criteriaRoot.join("resourceLinearValues");
            }

            return criteriaBuilder.and(
                    criteriaBuilder.equal(getParameterPath(), parameterEntity), // equality for parameter id
                    operation.getRestriction(criteriaBuilder, this)             // restriction from compare sign
            );
        }
    }
    
    private enum ResourceParameterCompare {

        LESS("less") {
            @Override
            public Predicate getRestriction(CriteriaBuilder cb, QueryParameter parameter) {
                return cb.lessThan(parameter.getValuePath("value"), parameter.getValue());
            }
        },
        GREATER("greater") {
            @Override
            public Predicate getRestriction(CriteriaBuilder cb, QueryParameter parameter) {
                return cb.greaterThan(parameter.getValuePath("value"), parameter.getValue());
            }
        },
        EQUAL("equal") {
            @Override
            public Predicate getRestriction(CriteriaBuilder cb, QueryParameter parameter) {

                return cb.equal(parameter.getValuePath("value"), parameter.getValue());
            }
        },
        LINEAR("linear"){
            @Override
            public Predicate getRestriction(CriteriaBuilder cb, QueryParameter parameter) {
                return cb.and(
                        cb.lessThan(parameter.getValuePath("minValue"), parameter.getValue()),
                        cb.greaterThan(parameter.getValuePath("maxValue"), parameter.getValue())
                );
            }
        };

        /**
         * String representation of enumeration instance, used to pass parameters from client
         */
        private String compareSign;

        /**
         * Constructor of enumeration instances
         * @param compareSign representation of operation on client-side
         */
        ResourceParameterCompare(String compareSign) {
            this.compareSign = compareSign;
        }

        /**
         * Create Predicate restriction for this operation
         *
         * @param cb
         * @param parameter query parameter for operation, consist of actual resource parameter and value for the search
         * @return restriction for this logical operation
         */
        public abstract Predicate getRestriction(CriteriaBuilder cb, QueryParameter parameter);

        /**
         * Return instances of this enumeration for String representation.
         *
         * @param compareSign String representation of operation.
         *                    If no operation is found IllegalArgumentException will be thrown.
         * @return corresponding instance of a class
         *
         */
        public static ResourceParameterCompare from(String compareSign) {
            for (ResourceParameterCompare value : values()) {
                if (value.compareSign.equalsIgnoreCase(compareSign)) {
                    return value;
                }
            }
            throw new IllegalArgumentException(String.format("There is no comparison operation with compare sign '%s'",
                    compareSign));
        }
    }

}
