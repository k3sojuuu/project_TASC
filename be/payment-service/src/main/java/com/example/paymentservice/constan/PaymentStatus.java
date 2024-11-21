package com.example.paymentservice.constan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class PaymentStatus {
    public static final String PENDING = "PENDING";
    public static final String PAID = "PAID";
    public static final String FALSE = "FALSE";

    public PaymentStatus(){}
}
