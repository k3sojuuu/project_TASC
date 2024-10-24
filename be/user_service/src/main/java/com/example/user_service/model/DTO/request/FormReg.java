package com.example.user_service.model.DTO.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class FormReg {
    private String first_name;
    private String last_name;
    private String username;
    private String password;
    private String email;
    private String phone_number;
    private String address;
    private Date create_at;
    private Integer age;
    private String certifications;
    private String specialization;
    private Integer gender;
    private int[] role;
}

