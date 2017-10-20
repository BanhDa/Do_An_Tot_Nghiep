/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.utils;

import java.util.Collection;
import java.util.Map;

/**
 *
 * @author tuantran
 */
public class Validator {
    
    public static boolean validateObject(Object object) {
        return object != null;
    }
    
    public static boolean validateObjects(Object... objects) {
        for (Object object : objects) {
            if (!validateObject(object)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValidCollection(Collection<String> collection) {
        return validateObject(collection) && ! collection.isEmpty();
    }
    
    public static boolean isValidCollections(Collection<String>... collections) {
        for (Collection<String> collection : collections) {
            if (!isValidCollection(collection)) {
                return false;
            }
        }
        return true;
    }
    
    public static boolean isValidMap(Map<String, String> map) {
        return validateObject(map) && !map.isEmpty();
    }
    
    public static boolean isValidMaps(Map<String, String>... maps) {
        for (Map<String, String> map : maps) {
            if (!isValidMap(map)) {
                return false;
            }
        }
        return true;
    }
    
}
