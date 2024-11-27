package com.example.order_service.service.impl;

import com.example.order_service.contant.OrderStatus;
import com.example.order_service.model.OrderUpdate;
import com.example.order_service.model.dto.MyResponse;
import com.example.order_service.model.dto.RefundRequest;
import com.example.order_service.model.entity.Course;
import com.example.order_service.model.entity.Order;
import com.example.order_service.model.entity.Schedules;
import com.example.order_service.repository.CourseClient;
import com.example.order_service.repository.OrderRepository;
import com.example.order_service.repository.ScheduleClient;
import com.example.order_service.service.OrderService;
import com.example.order_service.store_procedure.OrderStoreProcedure;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.kafka.annotation.KafkaListener;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import static com.example.order_service.security.JwtEntryPoint.log;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;
    private static final String PAYMENT_TOPIC = "payment-topic";

    private final OrderStoreProcedure orderStoreProcedure;
    private final OrderRepository orderRepository;
    private final CourseClient courseClient;
    private final ScheduleClient scheduleClient;
    @Autowired
    public OrderServiceImpl(OrderStoreProcedure orderStoreProcedure, OrderRepository orderRepository, CourseClient courseClient, ScheduleClient scheduleClient) {
        this.orderStoreProcedure = orderStoreProcedure;
        this.orderRepository = orderRepository;
        this.courseClient = courseClient;
        this.scheduleClient = scheduleClient;
    }

    public void sendKafkaMessage(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message);
            System.out.println("Kafka message sent to topic: " + topic + ", message: " + message);
        } catch (Exception e) {
            System.err.println("Failed to send Kafka message: " + e.getMessage());
        }
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
                order.getCreatedAt(),
                order.getPtId());
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
        try {
            Order order = orderRepository.getOrderById(orderUpdate.getOrderId());
            if (order == null) {
                myResponse.setStatus(404L);
                myResponse.setMessage("Order not found");
                return myResponse;
            }
            Course course;
            try {
                course = courseClient.getCourseById(order.getCourseId());
            } catch (Exception e) {
                myResponse.setStatus(500L);
                myResponse.setMessage("Failed to fetch course data: " + e.getMessage());
                return myResponse;
            }
            if (orderUpdate.getStatus().equals("SUCCESS")) {
                String newStatus = OrderStatus.SUCCESS;
                orderStoreProcedure.successOrder(orderUpdate.getOrderId(), newStatus, LocalDateTime.now());
                myResponse.setStatus(200L);
                myResponse.setMessage("Update success order");
                Schedules schedules = Schedules.builder()
                        .ptId(order.getPtId())
                        .uid(order.getUserId())
                        .price(order.getTotalPrice())
                        .sessionNumber(course.getNumberSession())
                        .courseId(order.getCourseId())
                        .build();
                try {
                    scheduleClient.createSchedule(schedules);
                } catch (Exception e) {
                    myResponse.setStatus(500L);
                    myResponse.setMessage("Failed to create schedule: " + e.getMessage());
                    return myResponse;
                }
            } else if (orderUpdate.getStatus().equals("FALSE")) {
                String newStatus = OrderStatus.FALSE;
                Integer result = orderStoreProcedure.falseOrder(orderUpdate.getOrderId(), newStatus);
                if (result > 0) {
                    myResponse.setStatus(200L);
                    myResponse.setMessage("Order has been cancelled");
                } else {
                    myResponse.setStatus(400L);
                    myResponse.setMessage("Failed to cancel order");
                }
            }
        } catch (Exception e) {
            myResponse.setStatus(500L);
            myResponse.setMessage("An error occurred: " + e.getMessage());
        }
        return myResponse;
    }
    @KafkaListener(topics = "out-of-stock", groupId = "order-payment-group")
    public void listenOutOfStock(String message) {
        // Xử lý thông báo khi khóa học hết hàng
        log.warn("Received out-of-stock message: {}", message);
        // Cập nhật trạng thái đơn hàng hoặc thông báo cho khách hàng
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
