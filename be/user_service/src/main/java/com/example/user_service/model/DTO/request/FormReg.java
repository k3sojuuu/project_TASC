package com.example.user_service.model.DTO.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotNull(message = "Firstname not null")
    private String first_name;
    @NotNull(message = "Lastname not null")
    private String last_name;
    @NotNull(message = "Username not null")
    private String username;
    @NotNull(message = "Password not null")
    private String password;
    @NotNull(message = "Email not null")
    @Email(message = "Invalid email format")
    private String email;
    @NotNull(message = "Phone Number not null")
    @Pattern(
            regexp = "0[0-9]{9,11}",
            message = "Phone number must be 10-12 digits long and start with 0"
    )
    private String phone_number;
    private String address;
    private Date create_at;
    private Integer age;
    private String certifications;
    private String specialization;
    private Integer gender;
    private int[] role;
}

