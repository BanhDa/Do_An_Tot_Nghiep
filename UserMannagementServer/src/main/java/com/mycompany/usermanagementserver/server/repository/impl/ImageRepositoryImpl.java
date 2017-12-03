/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.repository.impl;

import com.mongodb.MongoClient;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.domain.Image;
import com.mycompany.usermanagementserver.server.repository.ImageRepository;
import com.mycompany.usermanagementserver.server.response.ResponseMessage;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.constant.mongodbkey.ChatLogDBKey;
import com.mycompany.webchatutil.constant.mongodbkey.StaticFileDBKey;
import com.mycompany.webchatutil.utils.StringUtils;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

/**
 *
 * @author tuantran
 */
@Repository
public class ImageRepositoryImpl implements ImageRepository{
    
    private MongoOperations mongoOperations;
    
    @Autowired
    public ImageRepositoryImpl(MongoClient mongoClient) {
        MongoDbFactory factory = new SimpleMongoDbFactory(mongoClient, StaticFileDBKey.DB_NAME);
        mongoOperations = new MongoTemplate(factory);
    }
    
    @Override
    public Image save(Image image) throws UserManagememtException {
        try {
            mongoOperations.save(image);
            return image;
        } catch (Exception e) {
            e.printStackTrace();
            throw new UserManagememtException(ResponseCode.SAVE_FILE_ERROR, ResponseMessage.SAVE_FILE_ERROR);
        }
    }
    
    @Override
    public Image findByImageId(String imageId) throws UserManagememtException{
        if (StringUtils.isValid(imageId)) {
            ObjectId id = new ObjectId(imageId);
            Query query = new Query(Criteria.where(StaticFileDBKey.IMAGE.ID).is(id));
            return mongoOperations.findOne(query, Image.class);
        }
        throw new UserManagememtException(ResponseCode.FILE_NOT_FOUND, ResponseMessage.FILE_NOT_FOUND);
    }
}
