/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.controller;

import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.lastchat.LastChatManagement;
import com.mycompany.usermanagementserver.server.request.Request;
import com.mycompany.usermanagementserver.server.response.Response;
import com.mycompany.usermanagementserver.server.service.base.ChatService;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.webchatutil.constant.ResponseCode;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author tuantran
 */
@RestController
@RequestMapping("/chat")
public class ChatController {
    
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private ChatService chatService;
    
    @RequestMapping("/conversations")
    public ResponseEntity<Response> listConversations(@RequestHeader(Request.AUTHORIZATION) String token) {
        Response response = new Response();
        
        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, "INVALID_TOKEN");
            }
            
            String userId = tokenService.getUserId(token);
            List<Message> lastMessages = chatService.getLastMessages(userId);
            
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
