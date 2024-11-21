package com.example.order_service.service.impl;

import com.example.order_service.contant.OrderStatus;
import com.example.order_service.model.OrderUpdate;
import com.example.order_service.model.dto.MyResponse;
import com.example.order_service.model.entity.Order;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.service.OrderService;
import com.example.order_service.store_procedure.OrderStoreProcedure;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String PAYMENT_TOPIC = "payment-topic";

    private final OrderStoreProcedure orderStoreProcedure;
    private final OrderRepository orderRepository;
    @Autowired
    public OrderServiceImpl(OrderStoreProcedure orderStoreProcedure, OrderRepository orderRepository) {
        this.orderStoreProcedure = orderStoreProcedure;
        this.orderRepository = orderRepository;
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
        if (key != null) {
//            String paymentMessage = createPaymentMessage(order);
//            kafkaTemplate.send(PAYMENT_TOPIC, paymentMessage);
//            System.out.println(paymentMessage);
            return new MyResponse(200L, "Create success", key);
        }
        return new MyResponse(500L, "Failed to create order", order);
    }



    @Override
    public MyResponse updateOrder(OrderUpdate orderUpdate) {
        MyResponse myResponse = new MyResponse();
        if (orderUpdate.getStatus().equals("SUCCESS")) {
            String newStatus = OrderStatus.SUCCESS;
            orderStoreProcedure.successOrder(orderUpdate.getOrderId(), newStatus,LocalDateTime.now());
                myResponse.setStatus(200L);
                myResponse.setMessage("update success order");
        }
        if (orderUpdate.getStatus().equals("FALSE")) {
            String newStatus = OrderStatus.FALSE;
            Integer result = orderStoreProcedure.falseOrder(orderUpdate.getOrderId(), newStatus);
            if (result > 0) {
                myResponse.setStatus(200L);
                myResponse.setMessage("order has been cancelled");
            } else {
                myResponse.setStatus(400L);
                myResponse.setMessage("update false");
            }
        }
        return myResponse;
    }

    @Override
    public Order getOrderById(Long orderId) {
        return orderRepository.getOrderById(orderId);
    }

    //    private String createPaymentMessage(Order order) {
//        return "{\"orderCode\": " + order.getId() +
//                ", \"status\": " + order.getStatus() + ", \"amount\": " + order.getTotalPrice() + "}";
//    }
}
