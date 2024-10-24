package com.example.user_service.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
public class JwtEntryPoint implements AuthenticationEntryPoint {
    public static final Logger log = LoggerFactory.getLogger(JwtEntryPoint.class);
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException)
            throws IOException, ServletException {
        log.error("Unauthorized error Message:{}",authException);
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED,"Error -> Unauthorized");
    }
}

//    **Lớp JwtEntryPoint được sử dụng để xử lý các tình huống khi một yêu cầu
//        bị từ chối do lỗi xác thực JWT, ghi lại chi tiết lỗi và trả về mã trạng thái 401 cho khách hàng.
//    **Phương thức commence() được gọi khi có lỗi xác thực xảy ra. Nó nhận ba tham số:
//       * HttpServletRequest request: yêu cầu HTTP mà khách hàng gửi.
//       * HttpServletResponse response: phản hồi HTTP mà ứng dụng gửi lại.
//       * AuthenticationException authException: ngoại lệ xác thực.
//       * Phương thức này có thể ném ra hai ngoại lệ: IOException và ServletException.
