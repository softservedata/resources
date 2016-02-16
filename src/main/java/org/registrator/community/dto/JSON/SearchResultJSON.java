package org.registrator.community.dto.JSON;

import java.util.List;

/**
 * Created by Oleksiy on 15.02.2016.
 */
public class SearchResultJSON {
    private List<PolygonJSON> polygons;
    private Integer countPolygons;

    public List<PolygonJSON> getPolygons() {
        return polygons;
    }

    public void setPolygons(List<PolygonJSON> polygons) {
        this.polygons = polygons;
    }

    public Integer getCountPolygons() {
        return countPolygons;
    }

    public void setCountPolygons(Integer countPolygons) {
        this.countPolygons = countPolygons;
    }
}
