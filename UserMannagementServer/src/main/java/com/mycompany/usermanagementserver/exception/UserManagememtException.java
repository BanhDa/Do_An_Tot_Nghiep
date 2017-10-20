/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.exception;

/**
 *
 * @author tuantran
 */
public class UserManagememtException extends RuntimeException{
    
    private int code;
    private String message;
    
    public UserManagememtException() {
        super();
    }
    
    public UserManagememtException(int code) {
        super();
        this.code = code;
    }

    public UserManagememtException(String message) {
        super(message);
        this.message = message;
    }
    
    public UserManagememtException(int code, String message) {
        super(message);
        this.message = message;
        this.code = code;
    }
    
    public UserManagememtException(String message, Throwable ex) {
        super(ex);
        this.message = message;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
}
