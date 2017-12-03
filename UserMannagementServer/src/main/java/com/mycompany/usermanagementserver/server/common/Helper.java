/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.common;

import com.mycompany.usermanagementserver.cachemanagement.RedisUtil;
import com.mycompany.usermanagementserver.config.Config;
import com.mycompany.usermanagementserver.exception.RedisException;
import com.mycompany.usermanagementserver.exception.TokenException;
import com.mycompany.usermanagementserver.session.Session;
import com.mycompany.usermanagementserver.session.SessionManagement;
import com.mycompany.webchatutil.constant.Constant;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.utils.DateFormat;
import com.mycompany.webchatutil.utils.StringUtils;
import java.io.File;
import java.util.Date;
import java.util.UUID;
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
    
    public static boolean checkToken(String token) {
        Session session = getSession(token);
        if (!checkSession(session)) {
            return false;
        }
        resetTimeAlive(token);
        return true;
    }
    
    public static Session getSession(String token) {
        Session session = null;
        
        if (StringUtils.isValid(token)) {
            session = SessionManagement.getSession(token);
        }
        
        return session;
    }
    
    private static final Long SESSION_TIMEOUT = Config.SESSION_TIMEOUT * Constant.A_MINUTE;
    public static boolean checkSession(Session session) throws TokenException{
        Boolean result = false;
        if (session == null || session.getToken() == null || session.getTimeAlive() == null) {
            return result;
        }
        long duration = System.currentTimeMillis() - session.getTimeAlive();
        if (duration > SESSION_TIMEOUT) {
            SessionManagement.remove( session.getToken() );
        } else {
            result = true;
        }
        return result;
    }
    
    public static void resetTimeAlive(String token) {
        if (StringUtils.isValid(token)) {
            SessionManagement.resetSession(token);
        }
    }
    
    public static String createUrlPath(String fileType) {
        return UUID.randomUUID().toString() + "." + fileType;
//        String dateString = DateFormat.format(new Date());
//        StringBuilder monthYear = new StringBuilder();
//        monthYear.append(dateString.substring(0, 4)).append(dateString.substring(4, 6));
//            
//        return monthYear.toString() + File.separator + dateString.subSequence(6, 8) + File.separator + filename;

    }
    
}
