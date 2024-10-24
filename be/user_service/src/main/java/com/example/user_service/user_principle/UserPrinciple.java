package com.example.user_service.user_principle;

import com.example.user_service.model.DTO.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UserPrinciple implements UserDetails {
    private Long id;
    private String username;
    private String password;
    private String email;

    private Collection<? extends GrantedAuthority> roles;


    public static UserPrinciple build(UserDTO userDTO){
        List<GrantedAuthority> authorities;

        authorities = userDTO.getRoles().stream().
                map((s) -> new SimpleGrantedAuthority(s)).collect(Collectors.toList());
        return new UserPrinciple(
                userDTO.getId(),
                userDTO.getUsername(),
                userDTO.getPassword(),
                userDTO.getEmail(),
                authorities
        );
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
    @Override
    public boolean isEnabled() {
        return true;
    }

}
