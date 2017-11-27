/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.controller;

import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.lastchat.LastChat;
import com.mycompany.usermanagementserver.lastchat.LastChatManagement;
import com.mycompany.usermanagementserver.server.domain.User;
import com.mycompany.usermanagementserver.server.request.Request;
import com.mycompany.usermanagementserver.server.request.SearchRequest;
import com.mycompany.usermanagementserver.server.response.LastChatResponse;
import com.mycompany.usermanagementserver.server.response.Response;
import com.mycompany.usermanagementserver.server.response.ResponseMessage;
import com.mycompany.usermanagementserver.server.service.base.ChatService;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.usermanagementserver.server.service.base.UserService;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.utils.ModelMapperUtils;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
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
    @Autowired
    private UserService userService;
    
    @RequestMapping("/conversations")
    public ResponseEntity<Response> listConversations(@RequestHeader(Request.AUTHORIZATION) String token) {
        Response response = new Response();
        
        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
            }
            
            String userId = tokenService.getUserId(token);
            List<Message> lastMessages = chatService.getLastMessages(userId);
            
            List<String> friendIds = chatService.getLastChatUsers(userId);
            List<User> friendsInfo = userService.getUsersInfo(friendIds);
            
            List<LastChatResponse> lastChatResponses = new ArrayList<>();
            if ( friendsInfo != null && !friendsInfo.isEmpty() 
                    && lastMessages != null && !lastMessages.isEmpty()) {
                
                int lastMessageSize = lastMessages.size();
                int friendInfoSize = friendsInfo.size();
                int countLastMessage = 0;
                int countFriendInfo = 0;
                while (countLastMessage < lastMessageSize && countFriendInfo < friendInfoSize) {                
                    Message message = lastMessages.get( countLastMessage );
                    User friendInfo = friendsInfo.get( countFriendInfo );
                    String friendId = message.getFromUserId().equals(userId) ? message.getToUserId() : message.getFromUserId();
                    if ( friendInfo.getUserId().equals(friendId) ) {
                        LastChat lastChat = new LastChat(friendInfo, message);
                        LastChatResponse lastChatResponse = ModelMapperUtils.toObject(lastChat, LastChatResponse.class);
                        lastChatResponses.add(lastChatResponse);
                        countFriendInfo++;
                        countLastMessage++;
                    } else if (friendInfo.getUserId().compareTo(friendId) > 0) {
                        countFriendInfo++;
                    } else {
                        countLastMessage++;
                    }
                }
                
                if (countFriendInfo < friendInfoSize) {
                    for (int i = countFriendInfo; i < friendInfoSize; i++) {
                        LastChat lastChat = new LastChat(  friendsInfo.get(i) );
                        LastChatResponse lastChatResponse = ModelMapperUtils.toObject(lastChat, LastChatResponse.class);
                        lastChatResponses.add(lastChatResponse);
                    }
                } else if (countLastMessage < lastMessageSize) {
                    for (int i = countLastMessage; i < lastMessageSize; i++) {
                        LastChat lastChat = new LastChat( lastMessages.get(i) );
                        LastChatResponse lastChatResponse = ModelMapperUtils.toObject(lastChat, LastChatResponse.class);
                        lastChatResponses.add(lastChatResponse);
                    }
                }
            }
            
            response.setCode(ResponseCode.SUCCESSFUL);
            response.setData(lastChatResponses);
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        } catch (Exception ex) {
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData(ResponseMessage.UNKNOW_ERROR);
            ex.printStackTrace();
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @RequestMapping("/history")
    public ResponseEntity<Response> getChatHistory(@RequestHeader(Request.AUTHORIZATION) String token, 
            @RequestBody SearchRequest request) {
        Response response = new Response();
        
        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
            }
            
            if (request.validData()) {
                String userId = tokenService.getUserId(token);
                List<Message> history = chatService.getChatHistory(userId, request.getFriendId(), request.getSkip(), request.getTake());
                response.setCode(ResponseCode.SUCCESSFUL);
                response.setData(history);
            }
            
        } catch (UserManagememtException ex) {
            response.setCode(ex.getCode());
            response.setData(ex.getMessage());
        } catch (Exception ex) {
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData(ResponseMessage.UNKNOW_ERROR);
            ex.printStackTrace();
        }
        
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
