/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.request;

import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.utils.StringUtils;

/**
 *
 * @author tuantran
 */
public class SearchRequest extends Request{
    
    private String friendId;
    private String searchUserName;
    private Integer skip;
    private Integer take;

    public String getFriendId() {
        return friendId;
    }

    public void setFriendId(String friendId) {
        this.friendId = friendId;
    }

    public String getSearchUserName() {
        return searchUserName;
    }

    public void setSearchUserName(String searchUserName) {
        this.searchUserName = searchUserName;
    }

    public Integer getSkip() {
        return skip;
    }

    public void setSkip(Integer skip) {
        this.skip = skip;
    }

    public Integer getTake() {
        return take;
    }

    public void setTake(Integer take) {
        this.take = take;
    }
    
    @Override
    public boolean validData() throws UserManagememtException{
        if (StringUtils.isValid(this.friendId)) {
            return true;
        } else {
            throw new UserManagememtException(ResponseCode.WRONG_DATA_FORMAT, "Friend id null");
        }
    }
    
}
