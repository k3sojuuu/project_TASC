package com.example.productservice.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.cdi.Eager;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table
@Builder
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "pt_id")
    private Long ptId;
    private String type;
    private Float price;
    @Column(name = "pt_course_name")
    private String ptCourseName;
    private String description;
    @Column(name = "number_session")
    private Long numberSession;
    private String img;
    @Column(name = "name_course")
    private String nameCoarch;
    private Long quantity;
    private String createAt;
}
