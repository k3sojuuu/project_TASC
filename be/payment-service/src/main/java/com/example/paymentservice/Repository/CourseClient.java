package com.example.paymentservice.Repository;

import com.example.paymentservice.Model.DTO.MyResponse;
import com.example.paymentservice.Model.DTO.PaymentResponse;
import com.example.paymentservice.Model.DTO.PaymentResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "courseClient",url = "http://localhost:8081/sv2/course")
public interface CourseClient {
    @GetMapping("/hello")
    String getHelloWorld();
    @PostMapping("/check-stock")
    ResponseEntity<MyResponse> checkAndReduceStock(@RequestBody PaymentResponseDTO paymentResponse);
}
