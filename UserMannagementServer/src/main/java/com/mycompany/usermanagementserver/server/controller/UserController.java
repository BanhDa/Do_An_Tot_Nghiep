/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.controller;

import com.mycompany.usermanagementserver.entity.UserFull;
import com.mycompany.usermanagementserver.server.domain.User;
import com.mycompany.usermanagementserver.server.request.RegisterRequest;
import com.mycompany.usermanagementserver.server.response.Response;
import com.mycompany.usermanagementserver.server.service.base.UserService;
import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.request.Request;
import com.mycompany.usermanagementserver.server.request.SearchRequest;
import com.mycompany.usermanagementserver.server.service.base.FilesService;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.session.Session;
import com.mycompany.webchatutil.utils.ModelMapperUtils;
import com.mycompany.webchatutil.utils.StringUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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
    @Autowired
    private FilesService filesService;

    @RequestMapping("/login")
    public ResponseEntity<Response> login(@RequestBody @Validated RegisterRequest request) {
        Response response = new Response();
        try {
            if (!request.validData()) {
                response.setCode(ResponseCode.WRONG_DATA_FORMAT);
            } else {
                User user = userService.login(request.getEmail(), request.getPassword());
                
                String token = tokenService.createToken(user.getUserId());
                sessionService.addSession(new Session(token));
                
                response.setCode(ResponseCode.SUCCESSFUL);
                response.setData(user);
                response.setToken(token);
            }
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        } catch (Exception ex) {
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData("UNKNOW_ERROR");
            ex.printStackTrace();
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
                sessionService.addSession(new Session(token));
                
                response = new Response(ResponseCode.SUCCESSFUL, user);
                response.setToken(token);
            }
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        } catch (Exception ex) {
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData("UNKNOW_ERROR");
            ex.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/profile")
    public ResponseEntity<Response> getUserInfo(@RequestParam("userid") String friendId,
                @RequestHeader(Request.AUTHORIZATION) String token) {
        Response response = new Response();
        
        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
            }
            
            User userInfo;
            String userId = tokenService.getUserId(token);
            if (StringUtils.isValid(friendId) && !friendId.equals(userId)) {
                userInfo = userService.getUserInfo(friendId);
                userInfo.setPassword(null);
            } else {
                userInfo = userService.getUserInfo(userId);
            }
            
            response.setCode(ResponseCode.SUCCESSFUL);
            response.setData(userInfo);
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        } catch (Exception ex) {
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData("UNKNOW_ERROR");
            ex.printStackTrace();
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/editprofile")
    public ResponseEntity<Response> editProfile(@RequestHeader(Request.AUTHORIZATION) String token,
                    @RequestBody RegisterRequest request) {
        Response response = new Response();
        
        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
            }
            
            User updateUser = new User(request);
            updateUser = userService.updateUserInfo(updateUser);
            
            response.setCode(ResponseCode.SUCCESSFUL);
            response.setData(updateUser);
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        } catch (Exception ex) {
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData("UNKNOW_ERROR");
            ex.printStackTrace();
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/searchuser")
    public ResponseEntity<Response> searchUser(@RequestHeader(Request.AUTHORIZATION) String token, 
            @RequestBody SearchRequest requestData) {
        Response response = new Response();
        
        try {
            if (sessionService.checkToken(token)) {
                String userId = tokenService.getUserId(token);
                List<User> users = userService.searchUser(userId, requestData.getSearchUserName(), requestData.getSkip(), requestData.getTake());
                List<UserFull> data = addAvatarResource(users);
                response.setCode(ResponseCode.SUCCESSFUL);
                response.setData(data);
            } else {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
            }
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        } catch (Exception ex) {
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData("UNKNOW_ERROR");
            ex.printStackTrace();
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    private List<UserFull> addAvatarResource(List<User> users) {
        List<UserFull> userFulls = new ArrayList<>();
        
        if (users != null && !users.isEmpty()) {
            for (User user : users) {
                UserFull userFull = ModelMapperUtils.toObject(user, UserFull.class);
                if (StringUtils.isValid( user.getAvatar() )) {
                    String avatarSrc = filesService.getAvatarResourceByAvatarId(user.getAvatar());
                    userFull.setAvatarSrc(avatarSrc);
                }
                userFulls.add(userFull);
            }
        }
        
        return userFulls;
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Response> logout(@RequestHeader(Request.AUTHORIZATION) String token) {
        Response response = new Response();
        
        try {
            sessionService.remove(token);
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        } catch (Exception ex) {
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData("UNKNOW_ERROR");
            ex.printStackTrace();
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
}
