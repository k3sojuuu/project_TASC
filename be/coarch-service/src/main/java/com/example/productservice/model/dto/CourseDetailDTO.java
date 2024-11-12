package com.example.productservice.model.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CourseDetailDTO {
    private String ptName;
    private String type;
    private Float price;
    private String description;

    private Long numberSession;
    private String img;

    private String nameCoarch;
}
