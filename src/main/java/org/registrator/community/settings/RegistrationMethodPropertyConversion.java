package org.registrator.community.settings;

import org.registrator.community.enumeration.RegistrationMethod;

/**
 * Created by roman.golyuk on 29.03.2016.
 */
public class RegistrationMethodPropertyConversion implements PropertyConversion<RegistrationMethod> {
    @Override
    public RegistrationMethod getDefaultValue() {
        return RegistrationMethod.MANUAL;
    }

    @Override
    public RegistrationMethod fromDatabaseValue(String databaseValue) {
        return RegistrationMethod.valueOf(databaseValue);
    }

    @Override
    public String toDatabaseValue(RegistrationMethod value) {
        return value.toString();
    }
}
