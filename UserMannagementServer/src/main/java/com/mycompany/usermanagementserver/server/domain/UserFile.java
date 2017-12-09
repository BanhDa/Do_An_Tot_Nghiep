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
@Document(collection = "file")
public class UserFile {
    
    @Id
    private String fileId;
    @Field(StaticFileDBKey.FILE.USER_ID)
    private String userId;
    @Field(StaticFileDBKey.FILE.PATH)
    private String path;
    @Field(StaticFileDBKey.FILE.UPLOAD_TIME)
    private Long uploadTime;
    @Field(StaticFileDBKey.FILE.ORIGINAL_FILE_NAME)
    private String originalFileName;

    public UserFile() {
    }

    public UserFile(String userId, String path, Long uploadTime, String originalFileName) {
        this.userId = userId;
        this.path = path;
        this.uploadTime = uploadTime;
        this.originalFileName = originalFileName;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
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

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }
    
}
