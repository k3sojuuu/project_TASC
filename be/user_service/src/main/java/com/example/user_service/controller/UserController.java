package com.example.user_service.controller;

import com.example.user_service.service.implement.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sv1/user")
public class UserController {
    private final UserServiceImpl userService;
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/getUser")
   ResponseEntity<?> getUserByUserId(@RequestParam Long userId){
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    };
}
