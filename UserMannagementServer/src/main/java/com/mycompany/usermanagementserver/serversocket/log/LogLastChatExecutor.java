/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.log;

import com.mycompany.usermanagementserver.serversocket.dao.DAO.LastChatDAO;
import com.mycompany.webchatutil.utils.StringUtils;

/**
 *
 * @author tuantran
 */
public class LogLastChatExecutor implements Runnable{
    
    private String userId;
    private String friendId;
    private String messageId;

    public LogLastChatExecutor(String userId, String friendId, String messageId) {
        this.userId = userId;
        this.friendId = friendId;
        this.messageId = messageId;
    }

    @Override
    public void run() {
        try {
            if (StringUtils.isValid(userId, friendId, messageId)) {
                LastChatDAO.update(userId, friendId, messageId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
