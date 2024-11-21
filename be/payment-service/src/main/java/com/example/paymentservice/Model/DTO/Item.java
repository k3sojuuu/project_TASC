package com.example.paymentservice.Model.DTO;

import lombok.Data;

@Data
public class Item {
    private Long orderId;
    private String name;
    private int quantity;
    private int price;
}