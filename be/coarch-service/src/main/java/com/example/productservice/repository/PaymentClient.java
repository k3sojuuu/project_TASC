package com.example.productservice.repository;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "orderClient",url = "http://localhost:8088")
public interface PaymentClient {
}
