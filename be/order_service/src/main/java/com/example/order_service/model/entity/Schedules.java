package com.example.order_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedules {
    private Long id;
    private Long uid;
    private Long ptId;
    private Date startAt;
    private Date endAt;
    private Long courseId;
    private String descriptions;
    private String statusSchedules;
    private Long sessionNumber;
    private String schedulesType;
    private Float price;
}
