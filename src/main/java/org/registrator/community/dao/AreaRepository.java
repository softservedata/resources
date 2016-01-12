package org.registrator.community.dao;

import java.util.List;

import org.registrator.community.entity.Area;
import org.registrator.community.entity.Polygon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AreaRepository extends JpaRepository<Area, Integer>{
    
    @Query("Select a"+
            " From Area a" +
            " Where a.polygon = :polygon" )
    List<Area> findByPolygon(@Param("polygon")Polygon polygon);

    @Query("Select a"+
            " From Area a" +
            " Where a.latitude >= :minLat " +
            "and a.latitude <= :maxLat " +
            "and a.longitude >= :minLng " +
            "and a.longitude <= :maxLng" )
    List<Area> findByLatLngLimits(@Param("minLat")Double minLat,
                                  @Param("maxLat")Double maxLat,
                                  @Param("minLng")Double minLng,
                                  @Param("maxLng")Double maxLng);

}
