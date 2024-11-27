package com.example.productservice.repository;

import com.example.productservice.model.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    Course getCourseById(Long courseId);
}
