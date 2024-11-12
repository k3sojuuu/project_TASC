package com.example.productservice.service;

import com.example.productservice.model.dto.CourseDetailDTO;
import com.example.productservice.model.dto.request.BuySuccessDTO;
import com.example.productservice.model.entity.Course;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface CourseService {
    ResponseEntity<?> getAllCourse(int page,int size);
    ResponseEntity<?> createCourse(Course course);
    List<CourseDetailDTO> getCourseByType(String type);

    ResponseEntity<?> buySuccess(BuySuccessDTO buySuccessDTO);
}
