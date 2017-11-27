/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.userconnectionmanagement;

import com.mycompany.webchatutil.utils.StringUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 *
 * @author tuantran
 */
public class UserConnectionsManagement {
    
    private static final ConcurrentHashMap<String, HashMap<UUID, UserConnection>> CONTAINER = new ConcurrentHashMap<>();
    
    public static void addConnection(UserConnection userConnection) {
        if (validConnection(userConnection)) {
            HashMap<UUID, UserConnection> allConnections = CONTAINER.get(userConnection.getUserId());
            if (allConnections == null) {
                allConnections = new HashMap<>();
            }
            allConnections.put(userConnection.getSocket().getSessionId(), userConnection);
            CONTAINER.put(userConnection.getUserId(), allConnections);
        }
    }
    
    public static Collection<UserConnection> getAllConnectionOfUser(String userId) {
        Collection<UserConnection> results = new ArrayList<>();
        
        HashMap<UUID, UserConnection> map = CONTAINER.get(userId);
        if (map != null) {
            results = map.values();
        }
        
        return results;
    }
    
    public static boolean existsConnection(String userId, UUID connectionSession) {
        HashMap<UUID, UserConnection> allConnections = CONTAINER.get(userId);
        if (allConnections != null && !allConnections.isEmpty()) {
            return allConnections.containsKey(connectionSession);
        }
        return false;
    }
    
    public static void removeAllConnections(String userId) {
        CONTAINER.remove(userId);
    }
    
    public static void removeConnection(UserConnection userConnection) {
        if (validConnection(userConnection)) {
            HashMap<UUID, UserConnection> allConnections = CONTAINER.get(userConnection.getUserId());
            if (allConnections != null && !allConnections.isEmpty()) {
                allConnections.remove(userConnection.getSocket().getSessionId());
            }
        }
    }
    
    private static boolean validConnection(UserConnection userConnection) {
        return userConnection != null 
                && StringUtils.isValid( userConnection.getUserId() )
                && userConnection.getSocket() != null;
    }
}
