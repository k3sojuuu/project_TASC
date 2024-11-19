package com.example.user_service.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Table
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "file_name")
    private String fileName;
    @Column(name = "path_name")
    private String pathName;
    @Column(name = "type_img")
    private Long typeImg;
    @Column(name = "user_id")
    private Long userId;
    //foreignKey table users
    @Column(name ="upload_time")
    private Date uploadTime;
}
