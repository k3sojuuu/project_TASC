package com.example.user_service.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Collection;
    @Setter
    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    public class UserDTO {
        private Long id;
        private String username;
        private String email;
        private String password;
        private Collection<String> roles;
    }

