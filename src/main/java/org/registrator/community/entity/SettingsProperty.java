package org.registrator.community.entity;

import javax.persistence.*;

/**
 * Entity to save application settings
 */
public class SettingsProperty {

    @Id
    @GeneratedValue
    private Integer id;

    @Column
    @Enumerated(EnumType.STRING)
    private ApplicationProperty property;

    @Column(name = "value")
    private String stringValue;

    public Integer getId() {
        return id;
    }

    public ApplicationProperty getProperty() {
        return property;
    }

    public void setProperty(ApplicationProperty property) {
        this.property = property;
    }

    public String getStringValue() {
        return stringValue;
    }

    public void setStringValue(String stringValue) {
        this.stringValue = stringValue;
    }
}
