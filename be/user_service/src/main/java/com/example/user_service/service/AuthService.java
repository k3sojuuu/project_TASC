package com.example.user_service.service;

import com.example.user_service.model.DTO.request.ChangePasswordDTO;
import com.example.user_service.model.DTO.request.FormForgotPassword;
import com.example.user_service.model.DTO.request.FormLogin;
import com.example.user_service.model.DTO.request.FormReg;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<?> claimsPassword(String codeConfirm);

    public Boolean existByUserName(String username);
    public Boolean existByEmail(String email);
    Long getUserIdByUsername(String username);
    ResponseEntity<?> Login(FormLogin formLogin);
    public ResponseEntity<?> save(FormReg formReg);
    public ResponseEntity<?> changePassword(Long userId, ChangePasswordDTO changePasswordDTO);

    String generateRandomCode(int length);
    ResponseEntity<?> createCodeForgot(FormForgotPassword formForgotPassword);
//    ResponseEntity<?> claimsPassword(String codeConfirm);
}
