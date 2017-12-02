/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.constant;

/**
 *
 * @author tuantran
 */
public class ResponseCode {
    
    public static final int SUCCESSFUL = 0;
    public static final int UNKNOW_ERROR = 1;
    public static final int WRONG_DATA_FORMAT = 2;
    
    //register
    public static final int EXISTED_EMAIL = 3;
    
    //login
    public static final int EMAIL_NOT_FOUND = 4;
    public static final int INVALID_PASSWORD = 5;
    
    //token
    public static final int INVALID_TOKEN = 6;
    
    public static final int NOT_EXIST_USER = 7;
    public static final int FILE_NOT_FOUND = 8;
    public static final int SAVE_FILE_ERROR = 9;
    public static final int WRITE_FILE_ERROR = 10;
}
