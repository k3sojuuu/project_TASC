package com.example.gatewave.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsWebFilter;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
public class AppConfig {
    @Bean
    public RestTemplate template(){
        return new RestTemplate();
    }
    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http){
        http.csrf(csrf->csrf.disable()).cors(corsSpec -> corsSpec.disable());
        return http.build();
    }

    @Bean
    public CorsWebFilter filter(){
        org.springframework.web.cors.CorsConfiguration config= new CorsConfiguration();
        config.setAllowCredentials(false);
        config.setAllowedOrigins(Arrays.asList("*","*"));
        config.addExposedHeader("Authorization");
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "HEAD"));
        config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type",
                "Content-Disposition", "Content-Length",
                "Accept", "Authorization", "Cookie"));
        config.setMaxAge(3600L);
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", config);
        return new CorsWebFilter(source);
    }
    //Cấu hình chi tiết cho CORS, cho phép yêu cầu từ mọi nguồn,
    // với các phương thức HTTP và header được chỉ định,
    // đồng thời cấu hình thời gian cache cho các kết quả CORS.
}
