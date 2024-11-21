package com.example.paymentservice.Model.DTO;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class PaymentRequest {
    private int orderCode;
    private int amount;
    private String description;
    private String buyerName;
    private String buyerEmail;
    private String buyerPhone;
    private String buyerAddress;
//    private List<Item> items;
    private Long orderId;
    private Long courseId;
    private String name;
    private int quantity;
    private int price;
    private String cancelUrl;
    private String returnUrl;
    private long expiredAt;
    private String signature;
}
