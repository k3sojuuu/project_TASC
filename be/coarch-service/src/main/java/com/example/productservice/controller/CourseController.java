package com.example.productservice.controller;

import com.example.productservice.model.dto.request.BuySuccessDTO;
import com.example.productservice.model.entity.Course;
import com.example.productservice.service.CourseService;
import com.example.productservice.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sv2/course")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;

    @GetMapping("/getAllCourse")
    public ResponseEntity<?> getAllCourse(@RequestParam Integer page,
                                          @RequestParam Integer size){
        return courseService.getAllCourse(page,size);
    }
    @GetMapping("/getCourseFilter")
    ResponseEntity<?> getCourseByType(@RequestParam String type){
        return ResponseEntity.ok(courseService.getCourseByType(type));
    }

    @PostMapping("/createCourseOnline")
    ResponseEntity<?> createCourseOnline(@RequestBody Course course){
        return ResponseEntity.ok(courseService.createCourse(course));
    }

    @PostMapping("/buySuccesCourse")
    ResponseEntity<?> buySuccess(@RequestBody BuySuccessDTO successDTO){
        return null;
    }
}
