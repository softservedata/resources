package org.registrator.community.service;

import org.registrator.community.enumeration.ApplicationProperty;

import java.util.Map;

/**
 * Service to get and save application settings.
 *
 * To add new properties create new enum instance in ApplicationProperty.class and new
 * implementation of PropertyConverter interface for this enum.
 */
public interface SettingsService {
    /**
     * Get application property value, if there is no stored value default value will be returned
     * @param applicationProperty application property for which need to return value
     * @param clazz type of value to return
     * @return stored or default value for a property
     */
    <T> T getPropertyValue(ApplicationProperty applicationProperty, Class<T> clazz);

    /**
     * convenience method to return application property
     * @param applicationProperty application property for which need to return value
     * @return stored or default value for a property as Object.class
     */
    Object getPropertyValue(ApplicationProperty applicationProperty);

    /**
     * save property value
     */
    void savePropertyValue(ApplicationProperty applicationProperty, Object value);

    /**
     * Get all properties in form suitable to work in a client
     * @return Map<String, String> with String keys of application properties and String representation of values
     */
    Map<String, String> getAllPropertiesDTO();
}
