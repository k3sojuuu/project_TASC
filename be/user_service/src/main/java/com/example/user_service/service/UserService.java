package com.example.user_service.service;

import com.example.user_service.model.entity.Users;

public interface UserService {
    Users getUserByUserId(Long userId);
}
