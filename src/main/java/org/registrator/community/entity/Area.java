package org.registrator.community.entity;

import java.io.Serializable;

import javax.persistence.*;

@Entity
@Table(name = "area")
public class Area implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "area_id")
    @GeneratedValue
    private Integer areaId;

    @ManyToOne
    @JoinColumn(name = "polygon_id", nullable = false)
    private Polygon polygon;

    @Column(name = "latitude", nullable = false)
    private Double latitude;
    
    @Column(name = "longitude", nullable = false)
    private Double longitude;
    
    @Column(name = "order_number", nullable = false)
    private Integer orderNumber;

    public Area() {
        
    }
    
    public Area(Polygon polygon, Integer orderNumber, Double latitude, Double longitude) {
        this.polygon = polygon;
        this.orderNumber = orderNumber;
        this.latitude = latitude;
        this.longitude = longitude;
    }



    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Integer getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(Integer orderNumber) {
        this.orderNumber = orderNumber;
    }
}
