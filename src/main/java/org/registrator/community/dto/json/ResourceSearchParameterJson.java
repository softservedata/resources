package org.registrator.community.dto.json;

import org.registrator.community.enumeration.ParameterValueCompare;

/**
 * Created by roman.golyuk on 13.03.2016.
 */
public class ResourceSearchParameterJson {
    private Integer id;
    private ParameterValueCompare compare;
    private Double value;

    public Integer getId() {
        return id;
    }

    public ParameterValueCompare getCompare() {
        return compare;
    }

    public void setCompare(String compareString) {
        this.compare = ParameterValueCompare.from(compareString);
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "id:" + id.toString() + ",compare:" + compare + "value,:" + value;
    }
}
