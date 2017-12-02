/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.lastchat;

import com.mycompany.usermanagementserver.serversocket.dao.DAO.LastChatDAO;
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
    
    private static final ConcurrentHashMap<String, Map<String, String>> LAST_CHAT_CONTAINER = new ConcurrentHashMap<>();
    
    public static void cache() {
        Map<String, HashMap<String, String>> lastChats = LastChatDAO.getAll();
        LAST_CHAT_CONTAINER.putAll(lastChats);
    }
    
    public static void putLastChat(String userId, String friendId, String messageId) {
        Map<String, String> lastMessages = LAST_CHAT_CONTAINER.get(userId);
        if (lastMessages == null) {
            lastMessages = new HashMap<>();
        } 
        lastMessages.put(friendId, messageId);
        LAST_CHAT_CONTAINER.put(userId, lastMessages);
    }
    
    public static Map<String, String> getLastChat(String userId) {
        return LAST_CHAT_CONTAINER.get(userId);
    }
    
    public static Collection<String> getLastMessageIds(String userId) {
        Collection<String> results = new ArrayList<>();
        Map<String, String> lastChat = LAST_CHAT_CONTAINER.get(userId);
        if (lastChat != null) {
            results = lastChat.values();
        }
        
        return results;
    }
    
    public static List<String> getFriendIds(String userId) {
        List<String> results = new ArrayList<>();
        
        Map<String, String> lastChats = LAST_CHAT_CONTAINER.get(userId);
        if (lastChats != null && !lastChats.isEmpty()) {
            lastChats.entrySet().forEach((lastChat) -> {
                results.add( lastChat.getKey() );
            });
        }
        
        return results;
    }
}
