package org.registrator.community.entity;

import org.registrator.community.settings.PropertyConversion;
import org.registrator.community.settings.RegistrationMethodPropertyConversion;
import org.registrator.community.settings.TimeZonePropertyConversion;

/**
 * Created by roman.golyuk on 29.03.2016.
 */
public enum ApplicationProperty {
    REGISTRATION_METHOD(new RegistrationMethodPropertyConversion()),
    TIME_ZONE(new TimeZonePropertyConversion());


    private final PropertyConversion<?> propertyConversion;

    ApplicationProperty(PropertyConversion<?> propertyConversion) {
        this.propertyConversion = propertyConversion;
    }

    public PropertyConversion getPropertyConversion() {
        return propertyConversion;
    }
}
