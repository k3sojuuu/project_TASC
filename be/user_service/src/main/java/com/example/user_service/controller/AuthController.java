package com.example.user_service.controller;

import com.example.user_service.model.DTO.request.ChangePasswordDTO;
import com.example.user_service.model.DTO.request.FormLogin;
import com.example.user_service.model.DTO.request.FormReg;
import com.example.user_service.model.DTO.response.MyRespon;
import com.example.user_service.security.JwtProvider;
import com.example.user_service.security.JwtResponse;
import com.example.user_service.service.UserService;
import com.example.user_service.service.implement.UserServiceImpl;
import com.example.user_service.user_principle.UserPrinciple;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sv1/auth")
public class AuthController {

    @Autowired
    private UserServiceImpl userServiceImp;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private AuthenticationManager authenticationManager;
    @GetMapping("/hello")
    ResponseEntity<?> logger(){
        return ResponseEntity.ok("helloworld");
    }

    @PostMapping("/register")
    ResponseEntity<?> registry(@RequestBody FormReg formReg){return userServiceImp.save(formReg);}

    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody FormLogin formLogin){
       return ResponseEntity.ok(userServiceImp.Login(formLogin));
    }

    @GetMapping("/check-username")
    public ResponseEntity<Boolean> checkUsernameExists(@RequestParam String username) {
        boolean exists = userServiceImp.existByUserName(username);
        return ResponseEntity.ok(exists);
    }

    @GetMapping("/check-email")
    public ResponseEntity<Boolean> checkEmailExists(@RequestParam String email) {
        boolean exists = userServiceImp.existByEmail(email);
        return ResponseEntity.ok(exists);
    }
    @PostMapping("/changePassword")
    private ResponseEntity<?> changePassword(@RequestParam Long userId, @RequestBody ChangePasswordDTO changePasswordDTO){
        return ResponseEntity.ok(userServiceImp.changePassword(userId,changePasswordDTO));
    }


}
