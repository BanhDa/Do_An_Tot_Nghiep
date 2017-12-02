/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.log;

import com.mycompany.usermanagementserver.serversocket.dao.DAO.ChatLogDAO;
import com.mycompany.usermanagementserver.entity.message.Message;

/**
 *
 * @author tuantran
 */
public class LogMessageChatExecutor implements Runnable{
    
    private Message message;

    public LogMessageChatExecutor(Message message) {
        this.message = message;
    }
    
    @Override
    public void run() {
        if (message != null) {
            ChatLogDAO.log(message);
        }
    }
}
