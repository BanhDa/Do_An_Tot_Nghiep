/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.userconnectionmanagement;

import com.mycompany.webchatutil.constant.Constant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 *
 * @author tuantran
 */
public class UserConnectionGabarger{

    private static final Long CONNECTION_TIMEOUT = Constant.A_MINUTE;
    
    public static void start() {
        GabargeCollector gabargeCollector = new GabargeCollector();
        new Thread(gabargeCollector).start();
    }
    
    private static class GabargeCollector implements Runnable{

        @Override
        public void run() {
            while (true) {
                try {
                    Map<String, HashMap<UUID, UserConnection>> allConnection = UserConnectionsManagement.getAll();
                    checkExpiredConnections(allConnection);
                    Thread.sleep(Constant.A_MINUTE);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
        
        private void checkExpiredConnections(Map<String, HashMap<UUID, UserConnection>> allConnection) {
            if (allConnection != null && !allConnection.isEmpty()) {
                for (Map.Entry<String, HashMap<UUID, UserConnection>> entry : allConnection.entrySet()) {
                    HashMap<UUID, UserConnection> connectionsOfUser = entry.getValue();
                    
                    if (connectionsOfUser != null && !connectionsOfUser.isEmpty()) {
                        for (Map.Entry<UUID, UserConnection> entry1 : connectionsOfUser.entrySet()) {
                            UserConnection connection = entry1.getValue();
                            if (checkExpiredConnection(connection)) {
                                UserConnectionsManagement.removeConnection(connection);
                            }
                        }
                    }
                }
            }
        }
        
        private boolean checkExpiredConnection(UserConnection userConnection) {
            if (userConnection != null) {
                long currentTime = System.currentTimeMillis();
                long duration = currentTime - userConnection.getTimeAlive();
                return duration > CONNECTION_TIMEOUT;
            }
            return true;
        }
    }
}
