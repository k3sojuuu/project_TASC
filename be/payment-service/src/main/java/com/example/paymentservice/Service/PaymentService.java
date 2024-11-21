package com.example.paymentservice.Service;

import com.example.paymentservice.Model.DTO.PaymentRequest;
import com.example.paymentservice.Model.DTO.PaymentResponse;
import com.example.paymentservice.Model.DTO.PaymentSuccess;
import com.example.paymentservice.Model.Entity.Payment;
import org.springframework.http.ResponseEntity;

public interface PaymentService {
    String createPaymentRequest(PaymentRequest paymentRequest);

    ResponseEntity<?> saveInforPayment(Payment payment);

    ResponseEntity<?> paymentSuccess(PaymentSuccess paymentSuccess);

    Long getOrderIdFromPayment(String transactionId);
    Long getCourseIdFromPayment(String transactionId);
}

