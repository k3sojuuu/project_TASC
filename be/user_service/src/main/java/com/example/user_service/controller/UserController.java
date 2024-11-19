package com.example.user_service.controller;

import com.example.user_service.model.DTO.response.PagedResponse;
import com.example.user_service.model.DTO.response.PtResponse;
import com.example.user_service.service.implement.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/getAllPt")
    ResponseEntity<PagedResponse<PtResponse>> getAllPt(@RequestParam Integer page,
                                                       @RequestParam Integer size) {
        PagedResponse<PtResponse> response = userService.getAllPt(page, size);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/getAllRegisPT")
    ResponseEntity<PagedResponse<PtResponse>> getApprovalPT(@RequestParam Integer page,
                                                            @RequestParam Integer size){
        PagedResponse<PtResponse> response = userService.getAllRegisterPt(page,size);
        return ResponseEntity.ok(response);
    }
}
