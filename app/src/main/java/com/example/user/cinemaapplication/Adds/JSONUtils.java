package com.example.user.cinemaapplication.Adds;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSONUtils {

    final static ObjectMapper mapper = new ObjectMapper();

    public JSONUtils(){
    }

    public static <T> Collection<T> assertRoundTrip(String json) throws IOException {
        return mapper.readValue(json,new TypeReference<Collection<T>>(){});
    }

    public static <T> List<T> toList(Class<T> clazz, String json) throws IOException {
        return mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
    }

    public static <T> List<Object> toListObject(Class<T> clazz, String json) throws IOException {
        List<Object> c = null;
        try{
            c = mapper.readValue(json, mapper.getTypeFactory().constructCollectionType(List.class, clazz));
        }catch (Exception e){
            System.out.println("e " + e.getMessage());
        }
        return c;
    }

    public static <T> T parseJsonToObject(String jsonStr, Class<T> clazz) throws IOException {
        return mapper.readValue(jsonStr, clazz);
    }

    public static <T> String parseObjectToJson(T obj){
        String jsonString = "";
        try{
            jsonString = mapper.writerWithView(JSONViews.Normal.class).writeValueAsString(obj);
        }catch (JsonProcessingException e){
            e.printStackTrace();
        }
        return jsonString;
    }
}