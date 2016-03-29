package org.registrator.community.settings;

import org.registrator.community.dao.SettingsPropertyRepository;
import org.registrator.community.entity.ApplicationProperty;
import org.registrator.community.entity.SettingsProperty;
import org.registrator.community.service.SettingsService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.EnumMap;
import java.util.Map;

/**
 * Created by roman.golyuk on 29.03.2016.
 */
public class SettingsServiceImpl implements SettingsService {

    @Autowired
    private SettingsPropertyRepository settingsPropertyRepository;

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getPropertyValue(ApplicationProperty applicationProperty, Class<T> clazz) {
        PropertyConversion<T> propertyConversion = applicationProperty.getPropertyConversion();
        T result = propertyConversion.getDefaultValue();

        SettingsProperty settingsProperty = settingsPropertyRepository.getByProperty(applicationProperty);
        if (settingsProperty != null) {
            result = propertyConversion.fromDatabaseValue(settingsProperty.getStringValue());
        }

        return result;

    }

    @Override
    public Object getPropertyValue(ApplicationProperty applicationProperty) {
        return getPropertyValue(applicationProperty, Object.class);
    }

    @Override
    @SuppressWarnings("unchecked")
    public void savePropertyValue(ApplicationProperty applicationProperty, Object value) {
        SettingsProperty settingsProperty = settingsPropertyRepository.getByProperty(applicationProperty);
        if (settingsProperty == null) {
            settingsProperty = new SettingsProperty();
            settingsProperty.setProperty(applicationProperty);
        }

        settingsProperty.setStringValue(applicationProperty.getPropertyConversion().toDatabaseValue(value));
    }

    @Override
    public Map<ApplicationProperty, Object> getAllProperties() {
        Map<ApplicationProperty, Object> result = new EnumMap<>(ApplicationProperty.class);
        for (ApplicationProperty property : ApplicationProperty.values()) {
            result.put(property, getPropertyValue(property, Object.class));
        }
        return result;
    }
}
