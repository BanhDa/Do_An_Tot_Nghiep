/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.session;

import com.mycompany.usermanagementserver.config.Config;
import com.mycompany.webchatutil.constant.Constant;
import java.util.Map;

/**
 *
 * @author tuantran
 */
public class SessionGabarger implements Runnable{

    private static final Long EXPIRED_TIME = Config.SESSION_TIMEOUT * Constant.A_MINUTE;
    
    private SessionGabarger() {}
    
    public static void start() {
        SessionGabarger gabarger = new SessionGabarger();
        Thread thread = new Thread(gabarger);
        thread.start();
    }
    
    @Override
    public void run() {
        while(true) {
            try {
                Map<String, Session> sessionContainer = SessionManagement.getContainer();
                for (Map.Entry<String, Session> entry : sessionContainer.entrySet()) {
                    Session session = entry.getValue();
                    long duration = System.currentTimeMillis() - session.getTimeAlive();
                    if (duration > EXPIRED_TIME) {
                        SessionManagement.remove( entry.getKey() );
                    }
                }
                Thread.sleep(Constant.A_MINUTE);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
    
}
