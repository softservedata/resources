package org.registrator.community.components;

import org.registrator.community.enumeration.RegistrationMethod;
import org.springframework.stereotype.Component;

@Component
public class AdminSettings {
    
    private RegistrationMethod registrationMethod = RegistrationMethod.MANUAL;
    
    public AdminSettings(){}
    
    public AdminSettings(RegistrationMethod registrationMethod){
        this.registrationMethod=registrationMethod;
    }
    public void changeRegMethod(String methodName){
        this.registrationMethod = RegistrationMethod.valueOf(methodName.toUpperCase());
    }

    public RegistrationMethod getRegistrationMethod() {
        return registrationMethod;
    }
    
}