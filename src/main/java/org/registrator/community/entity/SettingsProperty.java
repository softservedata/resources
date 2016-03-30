package org.registrator.community.entity;

import org.registrator.community.entity.converter.PropertyConverter;
import org.registrator.community.enumeration.ApplicationProperty;

import javax.persistence.*;

/**
 * Entity to save application settings
 *
 * @see org.registrator.community.service.SettingsService
 */
@Entity
@Table(name = "SETTINGS")
public class SettingsProperty<T> {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    @Access(AccessType.PROPERTY)
    private ApplicationProperty property;

    @Column(name = "value")
    @Access(AccessType.PROPERTY)
    private String stringValue;

    @Transient
    private PropertyConverter<T> propertyConverter;

    @Transient
    private T value;

    protected SettingsProperty() {}

    public SettingsProperty(ApplicationProperty property) {
        setProperty(property);
    }

    public Integer getId() {
        return id;
    }

    public ApplicationProperty getProperty() {
        return property;
    }

    public void setProperty(ApplicationProperty property) {
        this.property = property;
        this.propertyConverter = this.property.getPropertyConverter();
    }

    private String getStringValue() {
        return stringValue;
    }

    private void setStringValue(String stringValue) {
        this.stringValue = stringValue;
        this.value = propertyConverter.convertToEntityAttribute(this.stringValue);
    }

    public T getValue() {
        if (value == null) {
            return propertyConverter.getDefaultValue();
        }
        return value;
    }

    public void setValue(T value) {
        this.value = value;
        this.stringValue = propertyConverter.convertToDatabaseColumn(this.value);
    }
}
