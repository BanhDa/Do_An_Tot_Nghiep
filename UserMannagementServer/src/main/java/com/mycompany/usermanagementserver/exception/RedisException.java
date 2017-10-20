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
public class RedisException extends UserManagememtException{
    
    public RedisException(String message) {
        super(message);
    }
    
    public RedisException(String message, Throwable ex) {
        super(message, ex);
    }
    
}
