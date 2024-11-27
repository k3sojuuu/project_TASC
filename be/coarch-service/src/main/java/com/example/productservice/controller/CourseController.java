package com.example.productservice.controller;

import com.example.productservice.model.MyRespone;
import com.example.productservice.model.dto.request.BuySuccessDTO;
import com.example.productservice.model.dto.response.PaymentResponse;
import com.example.productservice.model.entity.Course;
import com.example.productservice.repository.OrderClient;
import com.example.productservice.service.impl.CourseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sv2/course")
public class CourseController {
    @Autowired
    private CourseServiceImpl courseService;
    @GetMapping( "/hello")
    public ResponseEntity<?> getHello(){
        String hello = "Hello World";
        return ResponseEntity.ok(hello);
    }

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

    @PostMapping("/check-stock")
    public ResponseEntity<MyRespone> checkAndReduceStock(@RequestBody PaymentResponse paymentResponse) {
        MyRespone response = courseService.checkAndReduceStock(paymentResponse.getCourseId(), paymentResponse.getOrderId());
        return ResponseEntity.status(response.getStatus()).body(response);
    }

    @GetMapping("/getCourseById")
    public ResponseEntity<?> getCourseById(@RequestParam Long courseId){
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }
}
