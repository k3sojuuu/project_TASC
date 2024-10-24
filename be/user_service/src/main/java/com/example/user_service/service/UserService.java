package com.example.user_service.service;

import com.example.user_service.model.DTO.request.FormReg;
import org.springframework.http.ResponseEntity;

public interface UserService {
    public Boolean existByUserName(String username);
    public Boolean existByEmail(String email);

    Long getUserIdByUsername(String username);

    public ResponseEntity<?> save(FormReg formReg);
}
