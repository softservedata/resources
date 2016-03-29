package org.registrator.community.dao;

import org.registrator.community.entity.SettingsProperty;
import org.registrator.community.entity.ApplicationProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Interface for getting and saving application settings
 */
public interface SettingsPropertyRepository extends JpaRepository<ApplicationProperty, Integer> {

    @Query("select sp FROM SettingsProperty sp where sp.property = :property")
    SettingsProperty getByProperty(@Param("property") ApplicationProperty applicationProperty);

}
