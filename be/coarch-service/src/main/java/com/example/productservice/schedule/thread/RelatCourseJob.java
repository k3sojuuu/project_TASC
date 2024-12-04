package com.example.productservice.schedule.thread;

import com.example.productservice.dao.statement.CourseDaoImpl;
import com.example.productservice.model.entity.Course;
import com.example.productservice.service.impl.RedisServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class RelatCourseJob implements Runnable{
    private final ObjectMapper mapper = new ObjectMapper();
    private final RedisServiceImpl service;
    private final CourseDaoImpl courseDao;
    List<String> queue = Arrays.asList(new String[]{"gym", "yoga", "pilates"});

    public RelatCourseJob(RedisServiceImpl service, CourseDaoImpl courseDao) {
        this.service = service;
        this.courseDao = courseDao;
    }

    @Override
    public void run() {
        try {
            String cacheKey = "course_type_";
            for (String q: queue) {
                List<Course> list = courseDao.getCourseByType(q);
                if (list != null && !list.isEmpty()) {
                    Map<String, Object> courseMap = new HashMap<>();
                    for (Course course : list) {
                        courseMap.put(String.valueOf(course.getId()), mapper.writeValueAsString(course));
                    }
                    service.setValueRedisHash(cacheKey + q, courseMap, 1, TimeUnit.DAYS);
                }
                Thread.sleep(100);
            }
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("Route: Can't cache item");
        }
    }
}
