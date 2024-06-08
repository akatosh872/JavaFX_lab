package com.example.jfxtest;

import javafx.beans.property.SimpleStringProperty;

public class SystemProperty {
    private final SimpleStringProperty propertyName;
    private final SimpleStringProperty propertyValue;

    public SystemProperty(String propertyName, String propertyValue) {
        this.propertyName = new SimpleStringProperty(propertyName);
        this.propertyValue = new SimpleStringProperty(propertyValue);
    }

    public String getPropertyName() {
        return propertyName.get();
    }

    public SimpleStringProperty propertyNameProperty() {
        return propertyName;
    }

    public String getPropertyValue() {
        return propertyValue.get();
    }

    public SimpleStringProperty propertyValueProperty() {
        return propertyValue;
    }
}
