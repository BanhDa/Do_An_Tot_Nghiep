/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermannagementserver.server.controller;

import com.mycompany.usermannagementserver.config.Config;
import com.mycompany.usermannagementserver.server.domain.User;
import com.mycompany.usermannagementserver.server.request.RegisterRequest;
import com.mycompany.usermannagementserver.server.response.Response;
import com.mycompany.usermannagementserver.server.service.base.UserService;
import com.mycompany.usermannagementserver.token.JWTUtil;
import com.mycompany.usermannagementserver.token.TokenElement;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.usermannagementserver.exception.UserManagememtException;
import com.mycompany.webchatutil.utils.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tuantran
 */

@RestController
@RequestMapping("/user")
public class UserController {
    
    @Autowired
    UserService userService;
    
    @RequestMapping("/login")
    public ResponseEntity<Response> login(@RequestBody RegisterRequest request) {
        Response response = new Response();
        try {
            if (!request.validData()) {
                response.setCode(ResponseCode.WRONG_DATA_FORMAT);
            } else {
                User user = userService.login(request.getEmail(), request.getPassword());
                response.setCode(ResponseCode.SUCCESSFUL);
                response.setData(user);
            }
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/register")
    public ResponseEntity<Response> register(@RequestBody RegisterRequest request) {
        Response response = new Response();
        try {
            if (!request.validData()) {
                response.setCode(ResponseCode.WRONG_DATA_FORMAT);
            } else {
                User createdUser = new User();
                createdUser.setUserName(request.getUserName());
                createdUser.setPassword(request.getPassword());
                createdUser.setGender(request.getGender());
                createdUser.setBirthday(request.getBirthday());
                createdUser.setEmail(request.getEmail());
                User user = userService.createUser(createdUser);
        //        TokenElement tokenElement = new TokenElement(user.getUserId());
        //        String token = JWTUtil.generateToken(tokenElement);

                response = new Response(ResponseCode.SUCCESSFUL, user);
            }
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
