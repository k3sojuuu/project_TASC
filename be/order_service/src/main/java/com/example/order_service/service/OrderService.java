package com.example.order_service.service;

import com.example.order_service.model.dto.MyResponse;
import com.example.order_service.model.entity.Order;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface OrderService {
    List<Order> getAllOrder();

    MyResponse createOrder(Order order);
}
