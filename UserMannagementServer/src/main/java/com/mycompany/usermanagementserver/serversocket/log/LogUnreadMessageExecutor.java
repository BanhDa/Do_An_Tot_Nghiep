/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.log;

import com.mycompany.usermanagementserver.dao.DAO.UnreadMessageDAO;
import com.mycompany.webchatutil.utils.StringUtils;

/**
 *
 * @author tuantran
 */
public class LogUnreadMessageExecutor implements Runnable{

    private String userId;
    private String friendId;

    public LogUnreadMessageExecutor(String userId, String friendId) {
        this.userId = userId;
        this.friendId = friendId;
    }
    
    @Override
    public void run() {
        try {
            if (StringUtils.isValid(userId, friendId)) {
                UnreadMessageDAO.increase(userId, friendId);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
}
