package org.registrator.community.service.impl;

import org.registrator.community.dao.SettingsRepository;
import org.registrator.community.enumeration.ApplicationProperty;
import org.registrator.community.entity.SettingsProperty;
import org.registrator.community.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * Implementation of SettingsService
 *
 * @see org.registrator.community.service.SettingsService
 */

@Service
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsRepository settingsRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getPropertyValue(ApplicationProperty applicationProperty, Class<T> clazz) {

        SettingsProperty<T> settingsProperty = settingsRepository.getByProperty(applicationProperty);
        if (settingsProperty == null) {
            settingsProperty = new SettingsProperty(applicationProperty);
        }

        return settingsProperty.getValue();

    }

    @Override
    public Object getPropertyValue(ApplicationProperty applicationProperty) {
        return getPropertyValue(applicationProperty, Object.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void savePropertyValue(ApplicationProperty applicationProperty, Object value) {
        SettingsProperty settingsProperty = settingsRepository.getByProperty(applicationProperty);
        if (settingsProperty == null) {
            settingsProperty = new SettingsProperty(applicationProperty);
            settingsProperty.setProperty(applicationProperty);
        }

        settingsProperty.setValue(value);
        settingsRepository.save(settingsProperty);
    }

    @Override
    public Map<String, String> getAllPropertiesDTO() {
        Map<String, String> result = new HashMap<>();
        for (ApplicationProperty property : ApplicationProperty.values()) {
            Object value = getPropertyValue(property);
            result.put(property.toString(), property.getPropertyConverter().convertToClientRepresentation(value));
        }
        return result;
    }
}
