package com.example.paymentservice.Model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Data {
    private String bin;
    private String accountNumber;
    private String accountName;
    private int amount;
    private String description;
    private long orderCode;
    private String currency;
    private String paymentLinkId;
    private String status;
    private long expiredAt;
    private String checkoutUrl;
    private String qrCode;
}
