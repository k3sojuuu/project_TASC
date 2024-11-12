package com.example.productservice.model.dto;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Users {
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone_number;
    private String address;
    private String username;
    private String password;
    private Date create_at;
    private Integer age;
    private Long certificationId;
    private Integer video_id;
    private Long imgId;
    private Integer gender;
}
