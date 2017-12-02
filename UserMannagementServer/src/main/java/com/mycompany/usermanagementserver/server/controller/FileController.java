/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.controller;

import com.mycompany.usermanagementserver.config.Config;
import com.mycompany.usermanagementserver.exception.UserManagememtException;
import com.mycompany.usermanagementserver.server.common.Helper;
import com.mycompany.usermanagementserver.server.domain.Image;
import com.mycompany.usermanagementserver.server.request.Request;
import com.mycompany.usermanagementserver.server.response.Response;
import com.mycompany.usermanagementserver.server.response.ResponseMessage;
import com.mycompany.usermanagementserver.server.service.base.FilesService;
import com.mycompany.usermanagementserver.server.service.base.SessionService;
import com.mycompany.usermanagementserver.server.service.base.TokenService;
import com.mycompany.webchatutil.constant.FilesAndFolders;
import com.mycompany.webchatutil.constant.ResponseCode;
import com.mycompany.webchatutil.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author tuantran
 */
@RestController
@RequestMapping("/file")
public class FileController {
    
    @Autowired
    private Config config;
    @Autowired
    private SessionService sessionService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private FilesService filesService;
    
    @PostMapping("/uploadimage")
    public ResponseEntity<Response> singleFileUpload(@RequestHeader(Request.AUTHORIZATION) String token,
            @RequestParam("file") MultipartFile file) {

        Response response = new Response();

        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
            }
            
            if (file.isEmpty()) {
                byte[] bytes = file.getBytes();
                String urlPath = Helper.createUrlPath(FilesAndFolders.IMAGE_JPG_EXTENSION);
                String urlImage = config.folderImage + urlPath;
                filesService.writeFile(urlImage, bytes);
                
                String userId = tokenService.getUserId(token);
                Image image = new Image(userId, urlPath, System.currentTimeMillis());
                filesService.saveImage(image);
                
                response.setCode(ResponseCode.SUCCESSFUL);
                response.setData(image);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.setCode(ResponseCode.UNKNOW_ERROR);
            response.setData(ResponseMessage.UNKNOW_ERROR);
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
    
    @PostMapping("/loadimage")
    public ResponseEntity<byte[]> loadImage(@RequestHeader(Request.AUTHORIZATION) String token,
                @RequestParam("imageid") String imageId) {
        byte[] images = new byte[0];
        try {
            if (!sessionService.checkToken(token)) {
                throw new UserManagememtException(ResponseCode.INVALID_TOKEN, ResponseMessage.INVALID_TOKEN);
            }
            
            if (StringUtils.isValid(imageId)) {
                Image image = filesService.getImageByImageId(imageId);
                if (image != null) {
                    images = filesService.readFile(image.getPath());
                }
            } else {
                throw new UserManagememtException(ResponseCode.WRONG_DATA_FORMAT, ResponseMessage.IMAGE_ID_WRONG);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).body(images);
    }
}
