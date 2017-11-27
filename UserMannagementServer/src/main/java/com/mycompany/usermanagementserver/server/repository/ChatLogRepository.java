/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository;

import com.mycompany.usermanagementserver.entity.message.Message;
import java.util.Collection;
import java.util.List;

/**
 *
 * @author tuantran
 */
public interface ChatLogRepository {
    
    public Message getMessage(String userId, String messageId);
    
    public List<Message> getMessages(String userId, Collection<String> messageIds);
            
    public List<Message> getMessages(String userId, String friendId, Integer skip, Integer take);
}
