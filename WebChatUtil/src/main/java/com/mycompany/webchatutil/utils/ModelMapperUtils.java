/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.webchatutil.utils;

import org.modelmapper.ModelMapper;

/**
 *
 * @author TuanTV
 */
public class ModelMapperUtils {
    
    private static final ModelMapper MODEL_MAPPER = new ModelMapper();

    private ModelMapperUtils() {
    }

    public static <T> T toObject(Object obj, Class<T> type) {
        T t = null;
        if (obj != null) {
            try {
                t = MODEL_MAPPER.map(obj, type);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        }
        return t;
    }
}
