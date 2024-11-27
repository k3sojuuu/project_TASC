package com.example.productservice.schedule;

import com.example.productservice.dao.statement.interfaces.CourseDao;
import com.example.productservice.model.entity.Course;
import com.example.productservice.service.impl.RedisServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduleService {
    @Autowired
    CourseDao courseDao;
    @Autowired
    RedisServiceImpl template;

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    void doProcessList() {
        System.out.println("do task");
        String cacheKey = "course_list";
        List<Course> list = courseDao.getAllCourse();
        ObjectMapper objectMapper = new ObjectMapper();

        Map<String,Object> courseMap = new HashMap<>();
        for (Course course : list) {
            try {
                String courseJson = objectMapper.writeValueAsString(course);
                courseMap.put(String.valueOf(course.getId()), courseJson);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        template.setValueRedisHash(cacheKey, courseMap, 1, TimeUnit.DAYS);
    }

}
