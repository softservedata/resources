package org.registrator.community.dto.JSON;

import java.util.List;

/**
 * Request parameters from a client-side to search for resource by parameters values.
 */
public class ResourceSearchJSON {
    private List<ResourceSearchParameterJSON> discreteParameters;
    private List<ResourceSearchParameterJSON> linearParameters;
    private Integer resourceTypeId;
    private Integer page;

    public List<ResourceSearchParameterJSON> getDiscreteParameters() {
        return discreteParameters;
    }

    public List<ResourceSearchParameterJSON> getLinearParameters() {
        return linearParameters;
    }


    public Integer getResourceTypeId() {
        return resourceTypeId;
    }

    public void setResourceTypeId(Integer resourceTypeId) {
        this.resourceTypeId = resourceTypeId;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String toString() {
        return "=============================================\n"
                +"discreteParams: " + discreteParameters.toString() + "\n"
                +"linearParams: " + linearParameters.toString() + "\n"
                +"resourceTypeId: " + resourceTypeId.toString() +"\n"
                +"==============================================";
    }
}
