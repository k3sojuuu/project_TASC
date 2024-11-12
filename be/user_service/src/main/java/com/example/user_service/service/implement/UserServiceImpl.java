package com.example.user_service.service.implement;

import com.example.user_service.model.entity.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    public UserServiceImpl(UserRepository userRepository){
        this.userRepository = userRepository;
    }


    @Override
    public Users getUserByUserId(Long userId) {
        Users info = userRepository.getUsersByUserId(userId);
        return info;
    }
}
