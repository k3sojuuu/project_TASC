package com.example.paymentservice.Repository;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "courseClient",url = "http://localhost:8081/sv2/course")
public interface CourseClient {
    @GetMapping("/hello")
    String getHelloWorld();
}
