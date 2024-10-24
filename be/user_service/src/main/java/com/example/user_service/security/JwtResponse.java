package com.example.user_service.security;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Getter
@Setter
@NoArgsConstructor
public class JwtResponse {
    private Long id;
    private String username;
    private String type="Bearer";
    private String token;
    private Collection<? extends GrantedAuthority> roles;

    public JwtResponse(Long id, String username, String token, Collection<? extends GrantedAuthority> roles) {
        this.id = id;
        this.username = username;
        this.token = token;
        this.roles = roles;
    }
}
