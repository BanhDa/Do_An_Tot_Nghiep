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
import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.session.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tuantran
 */
@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SessionService sessionService;

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
            System.out.println("request: " + request);
            response.setData(request);
//            if (!request.validData()) {
//                response.setCode(ResponseCode.WRONG_DATA_FORMAT);
//            } else {
//                User user = userService.login(request.getEmail(), request.getPassword());
//                response.setCode(ResponseCode.SUCCESSFUL);
//                response.setData(user);
//            }
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
            System.out.println("register:");
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
                sessionService.addSession(new Session(token));
                
                response = new Response(ResponseCode.SUCCESSFUL, user);
            }
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/")
    public ResponseEntity<Response> getUserInfo(@RequestParam("userid") String userId,
                @RequestHeader("Authorization") String token) {
        Response response = new Response();
        
        try {
            Session session = sessionService.getSession(token);
            if (!sessionService.checkSession(session)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
            }
            sessionService.resetTimeAlive(token);
            
            
            
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
