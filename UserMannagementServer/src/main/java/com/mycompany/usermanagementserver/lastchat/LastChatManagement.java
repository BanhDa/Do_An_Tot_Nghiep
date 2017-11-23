/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.lastchat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author tuantran
 */
public class LastChatManagement {
    
    private static final ConcurrentHashMap<String, Map<String, String>> LAST_CHAT = new ConcurrentHashMap<>();
    
    public static void putLastChat(String userId, String friendId, String messageId) {
        Map<String, String> lastMessages = LAST_CHAT.get(userId);
        if (lastMessages == null) {
            lastMessages = new HashMap<>();
        } 
        lastMessages.put(friendId, messageId);
        LAST_CHAT.put(userId, lastMessages);
    }
    
    public static Map<String, String> getLastChat(String userId) {
        return LAST_CHAT.get(userId);
    }
    
    public static Collection<String> getLastMessageIds(String userId) {
        Collection<String> results = new ArrayList<>();
        Map<String, String> lastChat = LAST_CHAT.get(userId);
        if (lastChat != null) {
            results = lastChat.values();
        }
        
        return results;
    }
}
