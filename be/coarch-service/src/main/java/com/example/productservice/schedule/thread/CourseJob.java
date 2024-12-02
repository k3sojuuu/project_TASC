package com.example.productservice.schedule.thread;

import com.example.productservice.dao.statement.interfaces.CourseDao;
import com.example.productservice.model.entity.Course;
import com.example.productservice.service.impl.RedisServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class CourseJob implements Runnable{
    private final ObjectMapper mapper = new ObjectMapper();
    private final RedisServiceImpl redisService;
    private final CourseDao courseDao;

    private final String PREFIX_REDIS_KEY = "course_id_";
    public CourseJob(RedisServiceImpl redisService, CourseDao courseDao) {
        this.redisService = redisService;
        this.courseDao = courseDao;
    }
    @Override
    public void run() {
        try {
            List<Course> list = courseDao.getAllCourse();
            for (Course res: list){
                redisService.setValueRedis(PREFIX_REDIS_KEY + res.getId()
                                               ,mapper.writeValueAsString(res)
                                               ,1, TimeUnit.DAYS);
            }
        }  catch (Exception e){
            e.printStackTrace();
            System.out.println("Course: Can't cache item");
        }
    }
}
