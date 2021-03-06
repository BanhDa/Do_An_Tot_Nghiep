/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.config;

import com.mongodb.MongoClient;
import java.net.UnknownHostException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
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
    @Value("${mongodb.host}")
    public String mongoHost;
    @Value("${mongodb.port}")
    public Integer mongoPort;
    @Value("${folder.image}")
    public String folderImage;
    @Value("${folder.file}")
    public String folderFile;

    @Bean
    public MongoClient mongoClient() throws UnknownHostException {
        return new MongoClient(mongoHost, mongoPort);
    }

//    @Bean
//    public MongoDbFactory mongoDBFactory() throws UnknownHostException {
//        return new SimpleMongoDbFactory(mongoClient(), UserDBKey.USER_DB_NAME);
//    }
//    
//    @Bean
//    public MongoTemplate mongoTemplate() throws Exception{
//        return new MongoTemplate(mongoDBFactory());
//    }

    @Bean
    public FilterRegistrationBean corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        FilterRegistrationBean bean = new FilterRegistrationBean(new CorsFilter(source));
        bean.setOrder(0);
        return bean;
    }

    public static final int UPLOAD_SIZE = 100000000;

    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver multipartResolver() {
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
        multipartResolver.setMaxUploadSize(UPLOAD_SIZE);
        return new CommonsMultipartResolver();
    }
}
