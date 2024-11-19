package com.example.paymentservice.Service;

import com.example.paymentservice.Model.DTO.PaymentRequest;

public interface PaymentService {
    String createPaymentRequest(PaymentRequest paymentRequest);
}

