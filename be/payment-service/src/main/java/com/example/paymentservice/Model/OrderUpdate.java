package com.example.paymentservice.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderUpdate {
    private Long orderId;
    private String status;

}

