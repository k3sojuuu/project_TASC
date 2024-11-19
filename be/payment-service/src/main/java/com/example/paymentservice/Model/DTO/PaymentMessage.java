package com.example.paymentservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMessage {
    private Long orderCode;
    private String status;
    private Double amount;


}