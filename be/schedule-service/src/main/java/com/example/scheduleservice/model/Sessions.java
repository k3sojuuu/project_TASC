package com.example.scheduleservice.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "sessions")
@Builder
public class Sessions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "schedules_id")
    private Long schedulesId;
    @Column(name = "session_name")
    private String sessionName;
    @Column(name = "sessions_time")
    private String sessionTime;
    private String nutrition;
    @Column(name = "status_sessions")
    private Long statusSessions;
    @Column(name = "session_no")
    private Long session_no;
    private String location;
    private String description;
    @Column(name = "start_at")
    private LocalDateTime startAt;
    @Column(name = "end_at")
    private LocalDateTime endAt;

}