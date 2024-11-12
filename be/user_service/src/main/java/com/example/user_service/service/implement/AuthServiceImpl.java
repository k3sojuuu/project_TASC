package com.example.user_service.service.implement;

import com.example.user_service.model.DTO.request.ChangePasswordDTO;
import com.example.user_service.model.DTO.request.FormForgotPassword;
import com.example.user_service.model.DTO.request.FormLogin;
import com.example.user_service.model.DTO.request.FormReg;
import com.example.user_service.model.DTO.response.MyRespon;
import com.example.user_service.model.entity.ForgotPassword;
import com.example.user_service.model.entity.UserRoles;
import com.example.user_service.model.entity.Users;
import com.example.user_service.repository.ForgotPasswordRepo;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.repository.UserRoleRepository;
import com.example.user_service.security.JwtProvider;
import com.example.user_service.security.JwtResponse;
import com.example.user_service.service.AuthService;
import com.example.user_service.service.userDetailService.UserDetailService;
import com.example.user_service.user_principle.UserPrinciple;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;
    private final ForgotPasswordRepo forgotRepo;
    private final UserDetailService userDetailService;
    private final JavaMailSender javaMailSender;

    public AuthServiceImpl(UserRoleRepository roleRepository,
                           UserRepository userRepository,
                           PasswordEncoder passwordEncoder,
                           AuthenticationManager authenticationManager,
                           JwtProvider jwtProvider,
                           ForgotPasswordRepo forgotRepo,
                           UserDetailService userDetailService,
                           JavaMailSender javaMailSender){
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.forgotRepo =forgotRepo;
        this.userDetailService = userDetailService;
        this.javaMailSender = javaMailSender;
    }

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
        } catch (BadCredentialsException e) {
            System.out.println("Bad credentials for username: " + formLogin.getUsername());
            return ResponseEntity.status(400).body("Invalid password");
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
        users.setAge(formReg.getAge());
        users.setPhone_number(formReg.getPhone_number());
        users.setAddress(formReg.getAddress());
        users.setGender(formReg.getGender());
        Users savedUser = userRepository.save(users);
        if (savedUser == null) {
            myRespon.setMessage("Failed to save user. Please try again.");
            return ResponseEntity.status(500).body(myRespon.getMessage());
        }
        roles.setRid(2L);
        Long userId = userRepository.getUserIdByUsername(formReg.getUsername());
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
        myRespon.setMessage("User registered successfully");
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
    public ResponseEntity<?> createCodeForgot(FormForgotPassword formForgotPassword) {
        if (!existByUserName(formForgotPassword.getUsername())) {
            return ResponseEntity.status(400).body("Username not found");
        }
        if (!existByEmail(formForgotPassword.getEmail())) {
            return ResponseEntity.status(400).body("Email not found");
        }
        String codeConfirm = generateRandomCode(6);
        ForgotPassword forgotPassword = new ForgotPassword();
        forgotPassword.setUserName(formForgotPassword.getUsername());
        forgotPassword.setEmail(formForgotPassword.getEmail());
        forgotPassword.setCodeConfirm(codeConfirm);
        forgotPassword.setCreateAt(LocalDateTime.now());


        ExecutorService service = Executors.newFixedThreadPool(2);
        try {
            service.submit(new Runnable() {
                @Override
                public void run() {
                    forgotRepo.save(forgotPassword);
                }
            });
            service.submit(new Runnable() {
                @Override
                public void run() {
                    String msg = "This is your code: " + forgotPassword.getCodeConfirm();
                    String email = formForgotPassword.getEmail();
                    sendMail(msg, email);
                }
            });
        } catch (Exception e) {
            return ResponseEntity.status(500).body("An error occurred while processing your request.");
        } finally {
            service.shutdown();
        }
        return ResponseEntity.ok("Code for password reset created successfully.");
    }

    @Override
    public ResponseEntity<?> claimsPassword(String codeConfirm) {
        ForgotPassword forgotPassword = forgotRepo.findByCodeConfirm(codeConfirm);
        if (forgotPassword == null) {
            return ResponseEntity.status(400).body("Invalid code confirm");
        }
        LocalDateTime createAt = forgotPassword.getCreateAt();
        if (createAt == null) {
            return ResponseEntity.status(400).body("Creation time is missing");
        }
         createAt = forgotPassword.getCreateAt();
        if (createAt.plusMinutes(3).isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(400).body("Code has expired");
        }
        Users user = userRepository.findByUsername(forgotPassword.getUserName());

        String newPassword = generateRandomCode(8);
        String encodedPassword = passwordEncoder.encode(newPassword);
        user.setPassword(encodedPassword);
        String msg = "This is your new password: " + newPassword;
        String email = forgotPassword.getEmail();
        sendMail(msg, email);
        userRepository.save(user);
        forgotRepo.delete(forgotPassword);
        return ResponseEntity.ok("Password updated successfully");
    }

    @Override
    public Boolean existByUserName(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public Boolean existByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    @Override
    public Long getUserIdByUsername(String username) {
        return userRepository.getUserIdByUsername(username);
    }

    void sendMail(String msg,String email){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("MyPT notifications");
        message.setText(msg);
        javaMailSender.send(message);
    }

    @Override
    public String generateRandomCode(int length) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }



}
