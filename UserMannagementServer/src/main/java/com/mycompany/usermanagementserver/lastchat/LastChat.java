/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.lastchat;

import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.server.domain.User;

/**
 *
 * @author tuantran
 */
public class LastChat {
    
    private User user;
    private Message message;

    public LastChat() {
    }
    
    public LastChat(User user, Message message) {
        this.user = user;
        this.message = message;
    }

    public LastChat(User user) {
        this.user = user;
    }

    public LastChat(Message message) {
        this.message = message;
    }
    
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
