/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.request;

/**
 *
 * @author tuantran
 */
public abstract class Request {
    
    public static final String AUTHORIZATION = "authorization";
    
    public abstract boolean validData();
}
