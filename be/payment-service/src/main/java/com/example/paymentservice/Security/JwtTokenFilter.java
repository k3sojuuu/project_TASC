package com.example.paymentservice.Security;

import com.example.paymentservice.user_principle.UserPrinciple;
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
import java.util.List;

@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    public static final Logger log = LoggerFactory.getLogger(JwtProvider.class);
    public static String accesstoken;
    @Autowired
    JwtProvider jwtProvider;

    private String getJwt(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer")) {
            accesstoken = authHeader;
            return authHeader.replace("Bearer", "");
        }
        return null;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        final String requestTokenHeader = request.getHeader("Authorization");

        String username = null;
        String jwtToken = null;

        // Kiểm tra token trong header
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = getJwt(request);
            try {
                username = jwtProvider.getUserFromJwt(jwtToken);
            } catch (IllegalArgumentException e) {
                logger.error("JWT Token không hợp lệ: {}" );
            } catch (ExpiredJwtException e) {
                logger.error("JWT Token đã hết hạn: {}");
            } catch (Exception e) {
                logger.error("Lỗi khi xử lý JWT Token: {}");
            }
        } else {
            logger.warn("JWT Token không đúng định dạng Bearer hoặc không tồn tại.");
        }

        // Nếu lấy được username và chưa có Authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            try {
                // Xác thực token
                if (jwtProvider.validateToken(jwtToken)) {
                    List<String> roles = jwtProvider.getRoleFromToken(jwtToken);
                    UserDetails userDetails = new UserPrinciple(username, jwtToken, roles);

                    // Thiết lập thông tin xác thực
                    UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities());
                    authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                }
            } catch (Exception e) {
                logger.error("Lỗi xác thực token: {}");
            }
        }

        // Tiếp tục chuỗi filter
        chain.doFilter(request, response);
    }
}

