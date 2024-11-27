package com.example.productservice.model.dto;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseDTO {
    private Long id;
    private Long ptId;
    private String type;
    private Float price;
    private String ptCourseName;
    private String description;

    private Long numberSession;
    private String img;

    private String nameCoarch;
    private Long quantity;

    private String createAt;
}
