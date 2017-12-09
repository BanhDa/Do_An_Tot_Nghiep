/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.response;

import java.util.Date;

/**
 *
 * @author tuantran
 */
public class LastChatResponse {
    
    public String friendId;
    private String userName;
    private String avatar;
    
    private String id;
    private String messageId;
    private String fromUserId;
    private String toUserId;
    private Long time;
    private Date timeDate;
    private String readTime;
    private String messageType;
    private String value;
    private Integer unreadNumber;
    private String avatarSrc;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(String fromUserId) {
        this.fromUserId = fromUserId;
    }

    public String getToUserId() {
        return toUserId;
    }

    public void setToUserId(String toUserId) {
        this.toUserId = toUserId;
    }

    public Long getTime() {
        return time;
    }

    public Date getTimeDate() {
        return timeDate;
    }

    public void setTimeDate(Date timeDate) {
        this.timeDate = timeDate;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getReadTime() {
        return readTime;
    }

    public void setReadTime(String readTime) {
        this.readTime = readTime;
    }

    public String getMessageType() {
        return messageType;
    }

    public void setMessageType(String messageType) {
        this.messageType = messageType;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getUnreadNumber() {
        return unreadNumber;
    }

    public void setUnreadNumber(Integer unreadNumber) {
        this.unreadNumber = unreadNumber;
    }

    public String getAvatarSrc() {
        return avatarSrc;
    }

    public void setAvatarSrc(String avatarSrc) {
        this.avatarSrc = avatarSrc;
    }

    @Override
    public String toString() {
        return "LastChatResponse{" + "friendId=" + friendId + ", userName=" + userName + ", avatar=" + avatar + ", id=" + id + ", messageId=" + messageId + ", fromUserId=" + fromUserId + ", toUserId=" + toUserId + ", time=" + time + ", readTime=" + readTime + ", messageType=" + messageType + ", value=" + value + '}';
    }
    
    
}
