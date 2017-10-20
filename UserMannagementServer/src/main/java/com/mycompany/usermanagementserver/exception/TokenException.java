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
public class TokenException extends UserManagememtException{
    
    public TokenException(String message) {
        super(message);
    }
    
    public TokenException(String message, Throwable ex) {
        super(message, ex);
    }
}
