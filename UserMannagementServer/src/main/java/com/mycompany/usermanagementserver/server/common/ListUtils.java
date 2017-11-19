/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.usermanagementserver.server.common;

import com.mycompany.webchatutil.utils.Validator;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author tuantran
 * @param <E>
 */
public class ListUtils<E extends Object> {
    
    public List<E> sublist(List<E> list, Integer skip, Integer take) {
        List<E> resutls = new ArrayList<>();
        
        if ( list != null && !list.isEmpty()
                && Validator.validateObjects(skip, take)) {
            int listSize = list.size();
            int total = skip + take;
            if (listSize > skip) {
                if (listSize < total) {
                    total = listSize;
                }
                resutls = list.subList(skip, total);
            }
        }
        
        return resutls;
    }
    
}
