package com.example.productservice.service.impl;

import com.example.productservice.dao.statement.CourseDaoImpl;
import com.example.productservice.model.dto.response.ResponseCourseSort;
import com.example.productservice.model.entity.Course;
import com.example.productservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {


    @Autowired
    private CourseDaoImpl courseDao;

    public ResponseEntity<?> getAllCourse(int page, int size) {
        List<Course> courses = courseDao.getAllCourse(page, size);
        ResponseCourseSort<Course> response = new ResponseCourseSort<>(page, size, courses);
        return ResponseEntity.ok(response);
    }

    @Override
    public ResponseEntity<?> createCourse(Course course) {
        Course course1 = Course.builder()
                .ptId(course.getPtId())
                .type(course.getType())
                .price(course.getPrice())
                .description(course.getDescription())
                .numberSession(course.getNumberSession())
                .img(course.getImg())
                .createAt(course.getCreateAt()).build();
        Long key = courseDao.createCourse(course1);
        if (key != null){
          return ResponseEntity.status(200).body("Create course success");
        }else {
            return ResponseEntity.status(400).body("False");
        }
    }

    @Override
    public List<Course> getCourseByType(String type) {
        List<Course> list = courseDao.getCourseByType(type);
        return list;
    }
}
