/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.unreadmessage;

import com.mycompany.usermanagementserver.serversocket.dao.DAO.UnreadMessageDAO;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author tuantran
 */
public class UnreadMessageManagement {
    
    private static final ConcurrentHashMap<String, Map<String, Integer>> CONTAINER = new ConcurrentHashMap<>();
    
    public static void cache() {
        Map<String, HashMap<String, Integer>> unreadMessages = UnreadMessageDAO.getAll();
        CONTAINER.putAll(unreadMessages);
    }
    
    public static void increase(String userId, String friendId) {
        Map<String, Integer> unreadNumberOfUsers = CONTAINER.get(userId);
        Integer unreadNumber = 0;
        if (unreadNumberOfUsers == null) {
            unreadNumberOfUsers = new HashMap<>();
            unreadNumber = 1;
        } else {
            unreadNumber = unreadNumberOfUsers.get(friendId);
            unreadNumber = unreadNumber == null ? 1 : unreadNumber + 1;
        }
        unreadNumberOfUsers.put(friendId, unreadNumber);
        CONTAINER.put(userId, unreadNumberOfUsers);
    }
    
    public static void read(String userId, String friendId) {
        Map<String, Integer> unreadNumberOfUsers = CONTAINER.get(userId);
        if (unreadNumberOfUsers == null) {
            unreadNumberOfUsers = new HashMap<>();
        }
        unreadNumberOfUsers.put(friendId, 0);
        CONTAINER.put(userId, unreadNumberOfUsers);
    }
}
