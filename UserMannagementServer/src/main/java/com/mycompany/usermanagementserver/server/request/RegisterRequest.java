/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.request;

import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.utils.StringUtils;
import com.mycompany.webchatutil.utils.Validator;


/**
 *
 * @author tuantran
 */
public class RegisterRequest extends Request{
    
    private String userId;
    private String userName;
    private String email;
    private String password;
    private Integer gender;
    private String birthday;
    private String avatar;

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

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    
    @Override
    public boolean validData() {
        return StringUtils.isValid(email, password);
    }

    @Override
    public String toString() {
        return "RegisterRequest{" + "userId=" + userId + ", userName=" + userName + ", email=" + email + ", password=" + password + ", gender=" + gender + ", birthday=" + birthday + ", avatar=" + avatar + '}';
    }

}
