package com.baizhitong.resource.common.utils;

import java.lang.reflect.Field;
import java.util.Map;

public class BeanUtil {
    public static <T> T mapToEntity(T entity, Map<String, Object> param) {
        Class clazz = entity.getClass();
        Field[] fileds = clazz.getDeclaredFields();
        for (String key : param.keySet()) {
            for (Field field : fileds) {
                String fieldName = field.getName();
                if (fieldName.equals(key)) {
                    try {
                        field.setAccessible(true);
                        field.set(entity, param.get(key));
                    } catch (IllegalArgumentException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        }
        return entity;
    }

}
