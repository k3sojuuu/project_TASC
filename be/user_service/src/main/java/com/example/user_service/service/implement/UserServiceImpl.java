package com.example.user_service.service.implement;

import com.example.user_service.model.DTO.request.ChangePasswordDTO;
import com.example.user_service.model.DTO.request.FormLogin;
import com.example.user_service.model.DTO.request.FormReg;
import com.example.user_service.model.DTO.response.MyRespon;
import com.example.user_service.model.entity.UserRoles;
import com.example.user_service.model.entity.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.repository.UserRoleRepository;
import com.example.user_service.security.JwtProvider;
import com.example.user_service.security.JwtResponse;
import com.example.user_service.service.UserService;
import com.example.user_service.service.userDetailService.UserDetailService;
import com.example.user_service.user_principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRoleRepository roleRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtProvider jwtProvider;

    private UserDetailService userDetailService;


    //login
    @Override
    public ResponseEntity<?> Login(FormLogin formLogin) {
        UserPrinciple userPrinciple;
        if (!existByUserName(formLogin.getUsername())){
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

    @Override
    @Transactional
    public ResponseEntity<?> save(FormReg formReg) {
        MyRespon myRespon = new MyRespon();
        Users users = new Users();
        UserRoles roles = new UserRoles();
        if (existByUserName(formReg.getUsername())){
            myRespon.setMessage("The user name is already used! Try again!");
            return ResponseEntity.status(400).body(myRespon.getMessage());
        }else {
            users.setUsername(formReg.getUsername());
        }
        if (existByEmail(formReg.getEmail())){
            myRespon.setMessage("The email is already!Try again!");
            return  ResponseEntity.status(400).body(myRespon.getMessage());
        }else {
            users.setEmail(formReg.getEmail());
        }
        users.setPassword(passwordEncoder.encode(formReg.getPassword()));
        users.setFirstName(formReg.getFirst_name());
        users.setLastName(formReg.getLast_name());
        users.setCreate_at(new Date());
        users.setPhone_number(formReg.getPhone_number());
        users.setAddress(formReg.getAddress());
        Users savedUser = userRepository.save(users);
        if (savedUser == null) {
            myRespon.setMessage("Failed to save user. Please try again.");
            return ResponseEntity.status(500).body(myRespon.getMessage());
        }
        roles.setRid(1L);
        Long userId = getUserIdByUsername(formReg.getUsername());
        if (userId == null) {
            myRespon.setMessage("User ID retrieval failed. Please try again.");
            return ResponseEntity.status(500).body(myRespon.getMessage());
        }
        roles.setUid(userId);
        UserRoles savedRole = roleRepository.save(roles);
        if (savedRole == null) {
            myRespon.setMessage("Failed to assign role to user. Please try again.");
            return ResponseEntity.status(500).body(myRespon.getMessage());
        }
        myRespon.setMessage("User registered successfully!");
        return ResponseEntity.status(200).body(myRespon);
    }

    @Override
    public ResponseEntity<?> changePassword(Long userId, ChangePasswordDTO changePasswordDTO) {
        Users user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Old password is incorrect");
        }
        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getConfirmPassword())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("New password and confirmation do not match");
        }
        user.setPassword(passwordEncoder.encode(changePasswordDTO.getNewPassword()));
        userRepository.save(user);
        return ResponseEntity.ok("Password changed successfully");
    }


    @Override
    public Boolean existByUserName(String username) {
        return userRepository.existsByUsername(username);
    }


    @Override
    public Boolean existByEmail(String email) {
        return null;
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return null;
    }






}
