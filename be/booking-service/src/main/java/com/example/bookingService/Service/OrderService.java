package com.example.orderservice.Service;


import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class OrderService {

    private final RestTemplate restTemplate;

    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> callHelloApi() {
        String url = "http://localhost:9000/api/sv1/auth/hello";

        try {
            // Gọi API sử dụng RestTemplate
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);

            // Kiểm tra xem API có trả về "helloworld" hay không
            if ("helloworld".equals(response.getBody())) {
                return ResponseEntity.ok("Success: helloworld received");
            } else {
                return ResponseEntity.status(500).body("Failed to receive helloworld");
            }
        } catch (Exception e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            return ResponseEntity.status(500).body("Error: " + e.getMessage());
        }
    }
}
