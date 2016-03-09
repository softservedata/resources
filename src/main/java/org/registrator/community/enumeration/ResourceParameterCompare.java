package org.registrator.community.enumeration;

import org.registrator.community.dao.ResourceFindByParamsImpl;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;

/**
 * Enumeration of compare operations for resource search by parameters.
 *
 * Used to construct query restrictions by resource parameter and logical operation such as "less than", "equal" etc.
 *
 *
 */

public enum ResourceParameterCompare {

    LESS("less") {
        @Override
        public Predicate getRestriction(CriteriaBuilder cb, ResourceFindByParamsImpl.QueryParameter parameter) {
            return cb.lessThan(parameter.getValuePath("value"), parameter.getValue());
        }
    },
    GREATER("greater") {
        @Override
        public Predicate getRestriction(CriteriaBuilder cb, ResourceFindByParamsImpl.QueryParameter parameter) {
            return cb.greaterThan(parameter.getValuePath("value"), parameter.getValue());
        }
    },
    EQUAL("equal") {
        @Override
        public Predicate getRestriction(CriteriaBuilder cb, ResourceFindByParamsImpl.QueryParameter parameter) {

            return cb.equal(parameter.getValuePath("value"), parameter.getValue());
        }
    },
    LINEAR("linear"){
        @Override
        public Predicate getRestriction(CriteriaBuilder cb, ResourceFindByParamsImpl.QueryParameter parameter) {
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
    public abstract Predicate getRestriction(CriteriaBuilder cb, ResourceFindByParamsImpl.QueryParameter parameter);

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