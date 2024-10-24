package com.example.user_service.security;

import com.example.user_service.service.userDetailService.UserDetailService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    public static final Logger log = LoggerFactory.getLogger(JwtEntryPoint.class);

    @Autowired
    JwtProvider jwtProvider;
    @Autowired
    UserDetailService userDetailService;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
       final String requestTokenHeader = request.getHeader("Authorization");
       String username = null;
       String jwtToken = null;
        if (!request.getRequestURI().contains("/login")) {
            if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer")) {
                jwtToken = getJwt(request);
                try {
                    username = jwtProvider.getUserFromJwt(jwtToken);
                } catch (IllegalArgumentException e) {
                    logger.error("Can't find token JWT");
                } catch (ExpiredJwtException e) {
                    logger.error("Token JWT had epired");
                }
            } else {
                logger.warn("Jwt Token is not type Bearer");
            }
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = this.userDetailService.loadUserByUsername(username);
                try {
                    if (jwtProvider.validateToken(jwtToken)) {
                        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                                userDetails, null, userDetails.getAuthorities());
                        usernamePasswordAuthenticationToken
                                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        filterChain.doFilter(request,response);
    }

    private String getJwt(HttpServletRequest request) {
        String authHeader=request.getHeader("Authorization");
        if (authHeader !=null && authHeader.startsWith("Bearer")){
            return authHeader.replace("Bearer","");
        }
        return null;
    }
}
