package com.example.user_service.model.DTO.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PtResponse {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone_number;
    private String address;
    private Integer age;
    private String imgCertification;
    private String certificationName;
    private String imgAvatar;
    //image.status = 1
    private Integer gender;
    private String dateOfBirth;
    private String specialization;
}
