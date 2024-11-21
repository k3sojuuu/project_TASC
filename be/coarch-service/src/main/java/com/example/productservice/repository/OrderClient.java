package com.example.productservice.repository;

import com.example.productservice.model.dto.request.OrderUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "order-service",url = "http://localhost:8088")
public interface OrderClient {
    @PostMapping("/api/order/successOrder")
    ResponseEntity<?> successOrder(@RequestBody OrderUpdate orderUpdate);

}
