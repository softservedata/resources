package org.registrator.community.dao;

import org.registrator.community.entity.SettingsProperty;
import org.registrator.community.enumeration.ApplicationProperty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * Interface for getting and saving application settings
 */

public interface SettingsRepository extends JpaRepository<SettingsProperty, Integer> {

    @Query("select sp FROM SettingsProperty sp where sp.property = :property")
    SettingsProperty getByProperty(@Param("property") ApplicationProperty applicationProperty);

}
