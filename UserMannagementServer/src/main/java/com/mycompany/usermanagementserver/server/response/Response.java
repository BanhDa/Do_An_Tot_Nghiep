/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.response;

/**
 *
 * @author tuantran
 */
public class Response {
    
    private int code;
    private Object data;
    private String token;

    public Response() {
    }
    
    public Response(int code) {
        this.code = code;
    }
    
    public Response(int code, Object data) {
        this.code = code;
        this.data = data;
    }
    
    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
    
}
