package org.registrator.community.settings;

import java.util.TimeZone;

/**
 * Created by roman.golyuk on 29.03.2016.
 */
public class TimeZonePropertyConversion implements PropertyConversion<TimeZone> {
    @Override
    public TimeZone getDefaultValue() {
        return TimeZone.getTimeZone("EET");
    }

    @Override
    public TimeZone fromDatabaseValue(String databaseValue) {
        return TimeZone.getTimeZone(databaseValue);
    }

    @Override
    public String toDatabaseValue(TimeZone value) {
        return value.getID();
    }
}
