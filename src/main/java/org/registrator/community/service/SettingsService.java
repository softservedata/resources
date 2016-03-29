package org.registrator.community.service;

import org.registrator.community.entity.ApplicationProperty;

import java.util.Map;

/**
 *
 */
public interface SettingsService {
    <T> T getPropertyValue(ApplicationProperty applicationProperty, Class<T> clazz);

    Object getPropertyValue(ApplicationProperty applicationProperty);

    void savePropertyValue(ApplicationProperty applicationProperty, Object value);

    Map<ApplicationProperty, Object> getAllProperties();
}
