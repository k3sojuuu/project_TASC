package com.example.mediaservice.model;

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
@Entity
@Table(name = "image")
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
    @Column(name = "upload_time")
    private Date uploadTime;
}

