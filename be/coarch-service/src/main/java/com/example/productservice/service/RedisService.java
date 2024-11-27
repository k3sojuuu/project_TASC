package com.example.productservice.service;

import org.springframework.http.ResponseEntity;

import java.util.List;

public interface RedisService {
    void clear();
    void lPush(String key ,String o);
    String rPop(String key);
    public String get(String key);
    boolean check(String key);
    void lPushAll(String key, List<?> list);
    ResponseEntity<?> getAllCourse(int page );
    ResponseEntity<?> getCourseById(long id);
}
