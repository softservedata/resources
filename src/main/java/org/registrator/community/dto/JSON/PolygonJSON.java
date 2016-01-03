package org.registrator.community.dto.JSON;

import org.registrator.community.entity.Area;

import java.util.List;

/**
 * Created by Oleksiy on 02.01.2016.
 */
public class PolygonJSON {
    private String resourceDescription;
    private List<PointJSON> points;

    public String getResourceDescription() {
        return resourceDescription;
    }

    public void setResourceDescription(String resourceDescription) {
        this.resourceDescription = resourceDescription;
    }

    public List<PointJSON> getPoints() {
        return points;
    }

    public void setPoints(List<PointJSON> points) {
        this.points = points;
    }
}
