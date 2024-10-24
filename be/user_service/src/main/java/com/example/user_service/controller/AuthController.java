package com.example.user_service.controller;

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
    @Secured("ADMIN")
    ResponseEntity<?> registry(@RequestBody FormReg formReg){
        MyRespon myRespon = new MyRespon();
        if (userServiceImp.existByUserName(formReg.getUsername())){
            myRespon.setMessage("The user name is already used! Try again!");
            return ResponseEntity.status(400).body(myRespon.getMessage());
        }
        if (userServiceImp.existByEmail(formReg.getEmail())){
            myRespon.setMessage("The email is already!Try again!");
            return  ResponseEntity.status(400).body(myRespon.getMessage());
        }
        userServiceImp.save(formReg);
        myRespon.setMessage("Create user success");
        return new ResponseEntity<>(myRespon.getMessage(), HttpStatus.OK);
    }


    @PostMapping("/login")
    ResponseEntity<?> login(@RequestBody FormLogin formLogin){
        UserPrinciple userPrinciple;
        if (!userServiceImp.existByUserName(formLogin.getUsername())){
            return ResponseEntity.status(400).body("username not found");
        }
        try {
            Authentication authentication =authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(formLogin.getUsername(),formLogin.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String token = jwtProvider.generateToken(authentication);
            userPrinciple = (UserPrinciple) authentication.getPrincipal();
            return ResponseEntity.ok(
                    new JwtResponse(userPrinciple.getId(), userPrinciple.getUsername(),token,userPrinciple.getAuthorities() )
            );
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(400).body(new BCryptPasswordEncoder().matches(formLogin.getPassword(), "$2y$10$zob.ln.csZpcFGflN5yEM.mYIM3QuaBNLwSTpw5XnndCfkOE1sE7u"));
        }

    }


}
