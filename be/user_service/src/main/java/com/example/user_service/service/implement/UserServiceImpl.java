package com.example.user_service.service.implement;

import com.example.user_service.model.DTO.request.FormReg;
import com.example.user_service.model.entity.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

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

    @Override
    @Transactional
    public ResponseEntity<?> save(FormReg formReg) {
        Users users = new Users();
        users.setUsername(formReg.getUsername());
        users.setPassword(passwordEncoder.encode(formReg.getPassword()));
        users.setFirstName(formReg.getFirst_name());
        users.setLastName(formReg.getLast_name());
        users.setCreate_at(new Date());
        users.setEmail(formReg.getEmail());
        users.setPhone_number(formReg.getPhone_number());
        users.setAddress(formReg.getAddress());
        Users u = userRepository.save(users);
        int c = 0;
        int[] roles = formReg.getRole();
//        for (Long r: roles){
//            UserRoles ur = new UserRoles();
//            ur.setRid(r);
//            ur.setUid(Math.toIntExact(u.getUserId()));
//            userRoleRepo.save(ur);
//            c++;
//        }
        if(c == formReg.getRole().length){
            return ResponseEntity.status(200).body("Success");
        }
        return ResponseEntity.status(400).body("Fail");

    }
}
