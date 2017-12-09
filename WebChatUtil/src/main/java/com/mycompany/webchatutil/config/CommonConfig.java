/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.config;

/**
 *
 * @author tuantran
 */
public class CommonConfig {
    
    public static final String SESSION_TIMEOUT_KEY = "SESSION_TIMEOUT";
    private static Long SESSION_TIMEOUT = 60L;
    
    public static final String CONNECTION_TIMEOUT_KEY = "CONNECTION_TIMEOUT";
    private static Long CONNECTION_TIMEOUT = 1L;
    
    public static final String SOCKET_SERVER_HOST_KEY = "SOCKET_SERVER_HOST";
    private static String SOCKET_SERVER_HOST = "localhost";
    
    public static final String SOCKET_SERVER_PORT_KEY = "SOCKET_SERVER_PORT";
    private static Integer SOCKET_SERVER_PORT = 9092;
    
    public static void initConfig(String configFile) {
        try {
            SESSION_TIMEOUT = ConfigMethod.configLongProperty(configFile, SESSION_TIMEOUT_KEY, "60");
            CONNECTION_TIMEOUT = ConfigMethod.configLongProperty(configFile, CONNECTION_TIMEOUT_KEY, "1");
            
            SOCKET_SERVER_HOST = ConfigMethod.configStringProperty(configFile, SOCKET_SERVER_HOST_KEY, "localhost");
            SOCKET_SERVER_PORT = ConfigMethod.configIntProperty(configFile, SOCKET_SERVER_PORT_KEY, "9092");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Long getSESSION_TIMEOUT() {
        return SESSION_TIMEOUT;
    }

    public static Long getCONNECTION_TIMEOUT() {
        return CONNECTION_TIMEOUT;
    }

    public static String getSOCKET_SERVER_HOST() {
        return SOCKET_SERVER_HOST;
    }

    public static Integer getSOCKET_SERVER_PORT() {
        return SOCKET_SERVER_PORT;
    }

    public static void setSOCKET_SERVER_PORT(Integer SOCKET_SERVER_PORT) {
        CommonConfig.SOCKET_SERVER_PORT = SOCKET_SERVER_PORT;
    }
    
}
