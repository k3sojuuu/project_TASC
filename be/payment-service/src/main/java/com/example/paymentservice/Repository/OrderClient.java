package com.example.paymentservice.Repository;

import com.example.paymentservice.Model.OrderUpdate;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "orderClient",url = "http://localhost:8088")
public interface OrderClient {
    @PostMapping("/api/order/successOrder")
    ResponseEntity<?> successOrder(@RequestBody OrderUpdate orderUpdate);
}
