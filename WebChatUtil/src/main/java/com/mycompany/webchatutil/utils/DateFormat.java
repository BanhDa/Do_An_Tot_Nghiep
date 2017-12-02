/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.utils;

import java.util.Date;

/**
 *
 * @author tuantran
 */
public class DateFormat {
    
    public static final int JAVA_DATE_START_YEAR = 1900;
    private static final String ZERO = "0";
    
    public static String format(Date d) {
        if (d == null) {
            return null;
        }
        StringBuilder buidler = new StringBuilder()
                .append(d.getYear() + JAVA_DATE_START_YEAR)
                .append(format2DNumber(d.getMonth() + 1))
                .append(format2DNumber(d.getDate()))
                .append(format2DNumber(d.getHours()))
                .append(format2DNumber(d.getMinutes()))
                .append(format2DNumber(d.getSeconds()));
//                .append( getMillisecond( d.g ) );
        return buidler.toString();
    }
    
    private static String format2DNumber(int n) {
        return n > 9 ? String.valueOf(n) : (ZERO + n);
    }
}
