/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.constant;

import java.util.Arrays;
import java.util.List;

/**
 *
 * @author tuantran
 */
public class Constant {
    
    public static final long A_SECOND = 1000L;
    public static final long A_MINUTE = 60 * A_SECOND;
    public static final long A_HOUR = 60 * A_MINUTE;
    public static final long A_DAY = 24 * A_HOUR;
    
    public static class GENDER {
        public static final int MALE = 0;
        public static final int FEMALE = 1;
    }
    
    public static final List<String> SPECIAL_CHARACTER = Arrays.asList("\\", "(", ")", "?", "$", ".", "*", "+", "^", "[", "]", "|");
    
    public static class FLAG {
        public static final int ON = 1;
        public static final int OFF = 0;
    }
}
