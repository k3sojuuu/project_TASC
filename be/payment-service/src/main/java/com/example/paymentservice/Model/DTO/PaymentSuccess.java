package com.example.paymentservice.Model.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PaymentSuccess {
    private String paymentStatus;
    private String transactionId;
    private LocalDate updateAt;
}
