package com.example.order_service.service.impl;

import com.example.order_service.contant.OrderStatus;
import com.example.order_service.model.dto.MyResponse;
import com.example.order_service.model.entity.Order;
import com.example.order_service.service.OrderService;
import com.example.order_service.store_procedure.OrderStoreProcedure;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String PAYMENT_TOPIC = "payment-topic";

    OrderStoreProcedure orderStoreProcedure;
    @Autowired
    public OrderServiceImpl(OrderStoreProcedure orderStoreProcedure) {
        this.orderStoreProcedure = orderStoreProcedure;
    }

    @Override
    @Transactional()
    public List<Order> getAllOrder() {
        List<Order> orders = orderStoreProcedure.getAllOrders();
        return orders;
    }

    @Override
    @Transactional
    public MyResponse createOrder(Order order) {
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        Long key = orderStoreProcedure.createOrder(
                order.getCourseId(),
                order.getUserId(),
                order.getQuantity(),
                order.getTotalPrice(),
                order.getStatus(),
                order.getCreatedAt());
        if (key != null){
            order.setId(key);
            String paymentMessage = createPaymentMessage(order);
            kafkaTemplate.send(PAYMENT_TOPIC, paymentMessage);
            System.out.println(paymentMessage);
            return new MyResponse(200L,"Create success",order);
        }
        return new MyResponse(500L, "Failed to create order", order);
    }


    private String createPaymentMessage(Order order) {
        return "{\"orderCode\": " + order.getId() +
                ", \"status\": " + order.getStatus() + ", \"amount\": " + order.getTotalPrice() + "}";
    }
}
