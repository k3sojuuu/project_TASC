package com.example.scheduleservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "info_customer")
@Entity
@Builder
public class InfoCustomer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long userId;
    private Float bust; //v1
    private Float waist;//v2
    private Float hips;//v3
    private Float weight;//cân
    @Column(name = "update_at")
    private Date updateAt;
    @Column(name = "fat_rate")
    private Float fatRate;
    @Column(name = "muscle_rate")
    private Float muscleRate;
}
