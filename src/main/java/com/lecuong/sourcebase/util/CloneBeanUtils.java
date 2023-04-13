package com.lecuong.sourcebase.util;

import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;

/**
 * @author CuongLM18
 * @created 13/04/2023 - 1:54 PM
 * @project source-base
 */
@Slf4j
public class CloneBeanUtils {

    private CloneBeanUtils () {}

    public static <T> T cloneBean(T source) {
        try {
            if (source == null) {
                return null;
            }
            T dto = (T) source.getClass().getConstructor().newInstance();
            BeanUtils.copyProperties(source, dto);
            return dto;
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                NoSuchMethodException e) {
            log.error("Error parsing double", e);
            return null;
        }
    }

    public static <T> T cloneBean(T object, Class<T> type) {
        try {
            Gson gson = new Gson();
            return gson.fromJson(gson.toJson(object, type), type);
        } catch (Exception e) {
            log.error("Error parsing double", e);
            return null;
        }
    }
}
