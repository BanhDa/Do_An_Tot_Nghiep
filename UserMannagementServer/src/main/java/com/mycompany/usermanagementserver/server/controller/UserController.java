/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.controller;

import com.mycompany.usermanagementserver.server.domain.User;
import com.mycompany.usermanagementserver.server.request.RegisterRequest;
import com.mycompany.usermanagementserver.server.response.Response;
import com.mycompany.usermanagementserver.server.service.base.UserService;
import com.mycompany.usermanagementserver.server.service.base.RedisService;
import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tuantran
 */
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private RedisService redisService;

    @RequestMapping("/getuser")
    public ResponseEntity<Object> getUser() {
        System.out.println("get user");
        User user = new User();
        user.setEmail("tuan@nn.nn");
        user.setPassword("123456");
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @RequestMapping("/login")
    public ResponseEntity<Response> login(@RequestBody RegisterRequest request) {
        Response response = new Response();
        try {
            System.out.println("login");
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

                String token = tokenService.createToken(user.getUserId());
                redisService.addToken(user.getUserId(), token);

                response = new Response(ResponseCode.SUCCESSFUL, user);
            }
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
