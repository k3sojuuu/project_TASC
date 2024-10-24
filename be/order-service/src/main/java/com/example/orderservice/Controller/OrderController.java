package com.example.orderservice.Controller;

import com.example.orderservice.Service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/sv2/")
public class OrderController {

    private final OrderService orderService;

    @Autowired
    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @GetMapping("/hello")
    ResponseEntity<?> logger(){
        return ResponseEntity.ok("helloworld");
    }
    @GetMapping("/call-hello")
    public ResponseEntity<String> callHelloService() {
        return orderService.callHelloApi();
    }
}
