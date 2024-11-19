package com.example.user_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class Certifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "user_id")
    private Long uid;
    //foreign table user
    private String specialization;
    @Column(name = "degree_date")
    private Date degreeDate;
    @Column(name = "certification_name")
    private String certificationName;
    private Long status;
    @Column(name = "img_certification")
    private String imgCertification;
}
