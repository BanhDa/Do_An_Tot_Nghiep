/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.domain;

import com.mycompany.usermanagementserver.server.request.RegisterRequest;
import com.mycompany.webchatutil.constant.mongodbkey.UserDBKey;
import com.mycompany.webchatutil.utils.StringUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author tuantran
 */
@Document(collection = "user")
public class User {
    
    @Id
    private String userId;
    @Field(UserDBKey.USER.USER_NAME)
    private String userName;
    @Field(UserDBKey.USER.EMAIL)
    private String email;
    @Field(UserDBKey.USER.PASSWORD)
    private String password;
    @Field(UserDBKey.USER.BIRTHDAY)
    private String birthday;
    @Field(UserDBKey.USER.GENDER)
    private Integer gender;
    @Field(UserDBKey.USER.AVATAR)
    private String avatar;

    public User() {}
    
    public User(RegisterRequest request) {
        if ( StringUtils.isValid(request.getUserId() )) {
            userId = request.getUserId();
        }
        if ( StringUtils.isValid(request.getUserName() )) {
            userName = request.getUserName();
        }
        if ( StringUtils.isValid(request.getEmail() )) {
            email = request.getEmail();
        }
        if ( StringUtils.isValid(request.getPassword() )) {
            password = request.getPassword();
        }
        if ( StringUtils.isValid(request.getAvatar() )) {
            avatar = request.getAvatar();
        }
        if ( StringUtils.isValid(request.getBirthday() )) {
            birthday = request.getBirthday();
        }
        if (request.getGender() != null) {
            gender = request.getGender();
        }
    }
    
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    @Override
    public String toString() {
        return "User{" + "userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password + ", birthday=" + birthday + ", gender=" + gender + ", avatar=" + avatar + '}';
    }
}
