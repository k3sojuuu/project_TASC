package com.example.productservice.dao.statement.interfaces;

import com.example.productservice.model.entity.Course;

import java.util.List;

public interface CourseDao {
    public List<Course> getAllCourse();
    public List<Course> getCourseByType(String type);
    public Long createCourse(Course course);
}
