package org.registrator.community.settings;

/**
 * Created by roman.golyuk on 29.03.2016.
 */
public interface PropertyConversion<T> {

    T getDefaultValue();

    T fromDatabaseValue(String databaseValue);

    String toDatabaseValue(T value);
}
