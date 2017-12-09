/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.controller;

import com.mycompany.usermanagementserver.UserManagementApplication;
import com.mycompany.usermanagementserver.entity.UserFull;
import com.mycompany.usermanagementserver.entity.message.Message;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.domain.User;
import com.mycompany.usermanagementserver.server.request.Request;
import com.mycompany.usermanagementserver.server.request.SearchRequest;
import com.mycompany.usermanagementserver.server.response.LastChatResponse;
import com.mycompany.usermanagementserver.server.response.Response;
import com.mycompany.usermanagementserver.server.response.ResponseMessage;
import com.mycompany.usermanagementserver.server.service.base.ChatService;
import com.mycompany.usermanagementserver.server.service.base.FilesService;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.usermanagementserver.server.service.base.UserService;
import com.mycompany.usermanagementserver.unreadmessage.UnreadMessageManagement;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.utils.ModelMapperUtils;
import com.mycompany.webchatutil.utils.StringUtils;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static final Logger logger = LoggerFactory.getLogger(ChatController.class);
    @Autowired
    private TokenService tokenService;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private UserService userService;
    @Autowired
    private FilesService filesService;

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
            List<UserFull> userFulls = addAvatarResource(friendsInfo);
            
            List<LastChatResponse> lastChatResponses = new ArrayList<>();
            if (userFulls != null && !userFulls.isEmpty()
                    && lastMessages != null && !lastMessages.isEmpty()) {

                int lastMessageSize = lastMessages.size();
                int friendInfoSize = userFulls.size();
                int countLastMessage = 0;
                int countFriendInfo = 0;
                while (countLastMessage < lastMessageSize && countFriendInfo < friendInfoSize) {
                    Message message = lastMessages.get(countLastMessage);
                    UserFull friendInfo = userFulls.get(countFriendInfo);
                    String friendId = message.getFromUserId().equals(userId) ? message.getToUserId() : message.getFromUserId();
                    if (friendInfo.getUserId().equals(friendId)) {
                        LastChatResponse lastChatResponse = ModelMapperUtils.toObject(message, LastChatResponse.class);
                        addUserInfo(lastChatResponse, friendInfo);
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
                        LastChatResponse lastChatResponse = ModelMapperUtils.toObject(friendsInfo.get(i), LastChatResponse.class);
                        lastChatResponses.add(lastChatResponse);
                    }
                } else if (countLastMessage < lastMessageSize) {
                    for (int i = countLastMessage; i < lastMessageSize; i++) {
                        LastChatResponse lastChatResponse = ModelMapperUtils.toObject(lastMessages.get(i), LastChatResponse.class);

                        lastChatResponses.add(lastChatResponse);
                    }
                }
            }
            updateUnreadAndTimeSendMessage(userId, lastChatResponses);
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

    private void updateUnreadAndTimeSendMessage(String userId, List<LastChatResponse> lastChatResponses) {
        if (lastChatResponses != null && !lastChatResponses.isEmpty()) {
            for (LastChatResponse lastChatResponse : lastChatResponses) {
                try {
                    int unreadNumber = UnreadMessageManagement.getUnreadNumber(userId, lastChatResponse.friendId);
                    lastChatResponse.setUnreadNumber(unreadNumber);
                    Date date = new Date(lastChatResponse.getTime());
                    lastChatResponse.setTimeDate(date);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }
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
    
    private void addUserInfo(LastChatResponse lastChatResponse, UserFull user) {
        if (lastChatResponse != null && user != null) {
            lastChatResponse.friendId = user.getUserId();
            lastChatResponse.setUserName( user.getUserName() );
            lastChatResponse.setAvatar( user.getAvatar() );
            lastChatResponse.setAvatarSrc( user.getAvatarSrc() );
        }
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
