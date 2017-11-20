/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.common;

import com.mycompany.webchatutil.utils.StringUtils;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author tuantran
 */
public class Helper {
    
    public static final String REGEX_EMAIL = "^[\\w-\\+]+(\\.[\\w]+)*@[\\w-]+(\\.[\\w]+)*(\\.[a-z]{2,})$";
    
    public static boolean isEmail(String email) {
        
        if (StringUtils.isValid(email)) {
            Pattern pattern = Pattern.compile(REGEX_EMAIL);
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }
        
        return false;
    }
}
