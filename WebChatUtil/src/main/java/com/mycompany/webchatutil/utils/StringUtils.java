/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.utils;

/**
 *
 * @author tuantran
 */
public class StringUtils {
    
    public static boolean isValid(String inputString) {
        return inputString != null && !inputString.isEmpty();
    }
    
    public static boolean isValid(String... inputStrings) {
        for (String inputString : inputStrings) {
            if (!isValid(inputString)) {
                return false;
            }
        }
        return true;
    }
}
