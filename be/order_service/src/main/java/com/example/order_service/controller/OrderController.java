package com.example.order_service.controller;

import com.example.order_service.model.entity.Order;
import com.example.order_service.service.impl.OrderServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    private final OrderServiceImpl orderService;


    public OrderController(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/getAll")
    ResponseEntity<?> getAllOrder(){
        return ResponseEntity.ok(orderService.getAllOrder());
    }

    @PostMapping("/createOrder")
    ResponseEntity<?> createOrder(@RequestBody Order order){
        return ResponseEntity.ok(orderService.createOrder(order));
    }
}
