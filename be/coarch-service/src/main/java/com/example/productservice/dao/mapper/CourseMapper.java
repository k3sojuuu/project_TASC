package com.example.productservice.dao.mapper;

import com.example.productservice.model.entity.Course;
import org.springframework.jdbc.core.RowMapper;

public class CourseMapper {
     private RowMapper<Course> courseMap = ((rs, rowNum) -> {
        Course course = new Course();
        course.setId(rs.getLong("id"));
        course.setPtId(rs.getLong("pt_id"));
        course.setImg(rs.getString("img"));
        course.setDescription(rs.getString("description"));
        course.setPrice(rs.getFloat("price"));
        course.setType(rs.getString("type"));
        course.setCreateAt(rs.getString("createAt"));
        course.setNameCoarch(rs.getString("name_course"));
        return course;
    });

    public RowMapper<Course> getCourseMap() {
        return courseMap;
    }
}
