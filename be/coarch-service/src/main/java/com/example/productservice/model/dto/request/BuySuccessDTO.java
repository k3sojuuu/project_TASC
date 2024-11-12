package com.example.productservice.model.dto.request;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BuySuccessDTO {
    private Long userId;
    private Long ptId;
    private Date startAt;
    private Long schedulesType;
    private Long sessionNumber;
    private Float price;
}
