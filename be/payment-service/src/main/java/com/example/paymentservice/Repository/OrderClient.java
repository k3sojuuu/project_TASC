package com.example.paymentservice.Repository;

import org.springframework.cloud.openfeign.FeignClient;

@FeignClient(name = "orderClient")
public interface OrderClient {
}
