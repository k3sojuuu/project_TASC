package com.example.user_service.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@ToString
@Table(name = "users")
public class Users {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    private String email;
    private String phone_number;
    private String address;
    private String username;
    private String password;
    private Date create_at;
    private Integer age;
       //foreign table c
    @Column(name = "")
    private Integer video_id;
       //foreignKey table user;
    private Integer gender;
    private String description;
    @Column(name = "date_birth")
    private String dateOfBirth;
}