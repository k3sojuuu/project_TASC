package com.example.user_service.controller;

import com.example.user_service.model.DTO.request.ChangePasswordDTO;
import com.example.user_service.model.DTO.request.FormForgotPassword;
import com.example.user_service.model.DTO.request.FormLogin;
import com.example.user_service.model.DTO.request.FormReg;
import com.example.user_service.security.JwtProvider;
import com.example.user_service.service.implement.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sv1/auth")
public class AuthController {

    @Autowired
    private AuthServiceImpl authService;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/private/hello")
    ResponseEntity<?> logger(){
        return ResponseEntity.ok("helloworld");
    }

    @PostMapping("/register")
    @CrossOrigin(origins = "http://localhost:4200")
    ResponseEntity<?> registry(@RequestBody FormReg formReg){return authService.save(formReg);}

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody FormLogin formLogin){
       return ResponseEntity.ok(authService.Login(formLogin));
    }

    @GetMapping("/check_username")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean exists = authService.existByUserName(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check_email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = authService.existByEmail(email);
        return ResponseEntity.ok(exists);
    }
    @PostMapping("/change_password")
    ResponseEntity<?> changePassword(@RequestParam Long userId, @RequestBody ChangePasswordDTO changePasswordDTO){
        return ResponseEntity.ok(authService.changePassword(userId,changePasswordDTO));
    }

    @PostMapping("/create_code_forgot_password")
    ResponseEntity<?> createCodeForgot(@RequestBody FormForgotPassword forgotPassword){
        return ResponseEntity.ok(authService.createCodeForgot(forgotPassword));
    }

    @GetMapping("/claim_new_password")
    ResponseEntity<?> claimsPass(@RequestParam String codeCofirm){
        return ResponseEntity.ok(authService.claimsPassword(codeCofirm));
    }
}
