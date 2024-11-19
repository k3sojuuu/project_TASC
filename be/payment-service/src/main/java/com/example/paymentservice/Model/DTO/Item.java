package com.example.paymentservice.Model.DTO;

import lombok.Data;

@Data
public class Item {
    private String name;
    private int quantity;
    private int price;
}