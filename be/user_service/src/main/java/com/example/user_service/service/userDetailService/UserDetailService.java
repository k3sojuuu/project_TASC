package com.example.user_service.service.userDetailService;

import com.example.user_service.model.DTO.UserDTO;
import com.example.user_service.model.entity.Users;
import com.example.user_service.repository.UserRepository;
import com.example.user_service.user_principle.UserPrinciple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserDetailService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user = userRepository.findByUsername(username);
        List<String> roles = userRepository.findRoles(username);
        UserDTO userDTO = new UserDTO(
                user.getUserId(),
                user.getUsername(),
                user.getEmail(),
                user.getPassword(),
                roles
        );
        if (!userRepository.existsByUsername(username))
            throw new UsernameNotFoundException("Username not found");
        return UserPrinciple.build(userDTO);
    }
}
