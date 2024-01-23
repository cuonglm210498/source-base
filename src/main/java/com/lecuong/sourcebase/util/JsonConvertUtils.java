package com.lecuong.sourcebase.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author cuong.lemanh10
 * @created 08/08/2023
 * @project source-base
 */
public class JsonConvertUtils {

    private JsonConvertUtils() {}

    public static String convertObjectToJsonWithObjectMapper(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

    public static <T> T convertJsonToObjectWithObjectMapper(String json, Class<T> clazz) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, clazz);
    }

    public static String convertObjectToJsonWithGson(Object object) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    public static <T> T convertJsonToObjectWithGson(String json, Class<T> clazz) {
        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    public static <T> List<T> convertJsonArrayToListWithObjectMapper(String json, Class<T> elementClass) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        CollectionType listType =
                objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, elementClass);
        return objectMapper.readValue(json, listType);
    }

    public static <T> List<T> convertJsonArrayToListWithGson(String json, Class<T> elementClass) {
        Gson gson = new Gson();
        TypeToken<List<T>> listTypeToken = new TypeToken<List<T>>() {};
        return gson.fromJson(json, listTypeToken.getType());
    }

    public static <T> T[] convertJsonArrayToArrayWithObjectMapper(String json, Class<T[]> arrayClass) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(json, arrayClass);
    }

    public static <T> T[] convertJsonArrayToArrayWithGson(String json, Class<T[]> arrayClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, arrayClass);
    }
}
