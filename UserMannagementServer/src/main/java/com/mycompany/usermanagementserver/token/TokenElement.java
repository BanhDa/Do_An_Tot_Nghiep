/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.token;

/**
 *
 * @author tuantran
 */
public class TokenElement {
    
    private String userId;

    public TokenElement() {
    }
    
    public TokenElement(String userId) {
        this.userId = userId;
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
