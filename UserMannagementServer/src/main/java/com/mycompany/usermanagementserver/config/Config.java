/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 * @author tuantran
 */
@Configuration
public class Config {
    
    @Value("${session.timeout}")
    public Long timeout;
    @Value("${session.config}")
    public String config;
}
