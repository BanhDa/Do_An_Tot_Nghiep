/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.serversocket.log;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author tuantran
 */
public class LogContainer {
    
    private static final ExecutorService threadPoolExecutor =
        new ThreadPoolExecutor(
                100,
                1000,
                5,
                TimeUnit.MINUTES,
                new LinkedBlockingQueue<>()
                );
    
    public static void addTask(Runnable task) {
        threadPoolExecutor.execute(task);
    }
}
