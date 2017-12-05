/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.domain;

import com.mycompany.webchatutil.constant.mongodbkey.StaticFileDBKey;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

/**
 *
 * @author tuantran
 */
@Document(collection = "image")
public class Image {
    
    @Id
    private String imageId;
    @Field(StaticFileDBKey.IMAGE.USER_ID)
    private String userId;
    @Field(StaticFileDBKey.IMAGE.PATH)
    private String path;
    @Field(StaticFileDBKey.IMAGE.UPLOAD_TIME)
    private Long uploadTime;
    @Field(StaticFileDBKey.IMAGE.IS_AVATAR)
    private Integer isAvatar;

    public Image() {
    }

    public Image(String userId, String path, Long uploadTime) {
        this.userId = userId;
        this.path = path;
        this.uploadTime = uploadTime;
    }

    public Image(String userId, String path, Long uploadTime, Integer isAvatar) {
        this.userId = userId;
        this.path = path;
        this.uploadTime = uploadTime;
        this.isAvatar = isAvatar;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Long uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getIsAvatar() {
        return isAvatar;
    }

    public void setIsAvatar(Integer isAvatar) {
        this.isAvatar = isAvatar;
    }

}
