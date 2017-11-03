/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.config;

import com.mycompany.webchatutil.constant.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 *
 * @author tuantran
 */
@Configuration
@ComponentScan("com.mycompany.usermanagementserver")
@EnableWebMvc
public class Config {
    
    @Value("${session.timeout}")
    public Long timeout;
    @Value("${session.config}")
    public String config;
    
    public static Long SESSION_TIMEOUT = 60L;
}
