/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.service.impl;

import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.lastchat.LastChatManagement;
import com.mycompany.usermanagementserver.server.repository.ChatLogRepository;
import com.mycompany.usermanagementserver.server.service.base.ChatService;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author tuantran
 */
@Service
public class ChatServiceImpl implements ChatService{

    @Autowired
    private ChatLogRepository chatLogRepository;
    
    @Override
    public List<Message> getLastMessages(String userId) {
        Collection<String> lastMessageIds = LastChatManagement.getLastMessageIds(userId);
        List<Message> results = chatLogRepository.getMessages(userId, lastMessageIds);
        
        return results;
    }
    
}
