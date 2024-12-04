package com.example.productservice.schedule;

import com.example.productservice.dao.statement.CourseDaoImpl;
import com.example.productservice.dao.statement.interfaces.CourseDao;
import com.example.productservice.model.entity.Course;
import com.example.productservice.schedule.thread.CourseJob;
import com.example.productservice.schedule.thread.RelatCourseJob;
import com.example.productservice.service.impl.RedisServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

//import static jdk.internal.logger.LoggerFinderLoader.service;

@Component
public class ScheduleService {
    private final CourseDaoImpl dao;
    private final RedisServiceImpl service;
    @Autowired
    CourseDao courseDao;

    public ScheduleService(CourseDaoImpl dao, RedisServiceImpl service) {
        this.dao = dao;
        this.service = service;
    }

    @Scheduled(fixedDelay = 1, timeUnit = TimeUnit.DAYS)
    void doProcess() {
        System.out.println("do task");
        String cacheKey = "course_list";
        List<Course> list = courseDao.getAllCourse();
        if (list == null || list.isEmpty()) {
            System.out.println("No courses found to cache.");
            return;
        }
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
        service.setValueRedisHash(cacheKey, courseMap, 1, TimeUnit.DAYS);
        ExecutorService executor = Executors.newFixedThreadPool(3);

        executor.submit(new RelatCourseJob(service, dao));
        executor.submit(new CourseJob(service,dao));
    }

}
