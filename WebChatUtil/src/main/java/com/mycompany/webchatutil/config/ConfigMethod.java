/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.config;

import com.mycompany.webchatutil.utils.PropertiesLoader;
import java.util.Properties;

/**
 *
 * @author tuantran
 */
public class ConfigMethod {
    
    public static int configIntProperty(String fileName, String propertyName, String defaulValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName, defaulValue);
        int result = 0;
        try {
            result = Integer.parseInt(property.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public static double configDoubleProperty(String fileName, String propertyName, String defaulValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName, defaulValue);
        double result = 0;
        try {
            result = Double.parseDouble(property.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return result;
    }
    
    public static long configLongProperty(String fileName, String propertyName, String defaulValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName, defaulValue);
        long result = 0;
        try {
            result = Long.parseLong(property.trim());
        } catch (NumberFormatException ex) {
            ex.printStackTrace();
        }
        return result;
    }

    
    public static String configStringProperty(String fileName, String propertyName, String defaulValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        return serverProperties.getProperty(propertyName, defaulValue).trim();
    }

    public static Boolean configBooleanProperty(String fileName, String propertyName) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName);
        return property != null && property.trim().equalsIgnoreCase("true");
    }
    
    public static Boolean configBooleanProperty(String fileName, String propertyName, boolean defaultValue) {
        Properties serverProperties = PropertiesLoader.load(fileName);
        String property = serverProperties.getProperty(propertyName);
        return property == null? defaultValue : property.trim().equalsIgnoreCase("true");
    }   
}
