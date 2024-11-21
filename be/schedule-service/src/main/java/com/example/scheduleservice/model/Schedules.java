package com.example.scheduleservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "schedules")
@Builder
public class Schedules {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long uid;
    @Column(name = "pt_id")
    private Long ptId;
    @Column(name = "start_at")
    private Date startAt;
    @Column(name = "end_at")
    private Date endAt;
    @Column(name = "course_id")
    private Long courseId;
    private String descriptions;
    @Column(name = "status_schedules")
    private Long statusSchedules;
    @Column(name = "session_number")
    private Long sessionNumber;
    @Column(name = "schedules_type")
    private Long schedulesType;
    private Float price;
}
