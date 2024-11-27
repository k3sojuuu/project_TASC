package com.example.order_service.repository;

import com.example.order_service.model.entity.Course;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "course-id",url = "http://localhost:8081/sv2/course")
public interface CourseClient {
    @GetMapping("/getCourseById")
    Course getCourseById(@RequestParam Long courseId);
}
