package com.example.productservice.service.impl;

import com.example.productservice.constan.OrderStatus;
import com.example.productservice.dao.statement.CourseDaoImpl;
import com.example.productservice.model.MyRespone;
import com.example.productservice.model.dto.request.BuySuccessDTO;
import com.example.productservice.model.dto.request.OrderUpdate;
import com.example.productservice.model.dto.response.ResponseCourseSort;
import com.example.productservice.model.entity.Course;
import com.example.productservice.repository.CourseRepository;
import com.example.productservice.repository.OrderClient;
import com.example.productservice.repository.PaymentClient;
import com.example.productservice.service.CourseService;
import com.example.productservice.service.RedisService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final ObjectMapper redisMapper;
    private final RedisServiceImpl template;
    private final CourseDaoImpl courseDao;
    private final OrderClient orderClient;
    private final CourseRepository courseRepository;

    private static final Logger log = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final RedisTemplate<String, String> redisTemplate;


    public ResponseEntity<?> getAllCourse(int page, int size) {
        List<Course> courses = courseDao.getAllCourse();
        ResponseCourseSort<Course> response = new ResponseCourseSort<>(page, size, courses);
        return ResponseEntity.ok(response);
    }

    public String catchLog() {
        log.debug("This is a debug message");
        log.info("This is an info message");
        log.error("This is an error message");
        return "captured log";
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
        String cacheKey = "course_type_" + type;
        Map<Object, Object> redisHash = template.getAllHash(cacheKey);
        if (redisHash != null && !redisHash.isEmpty()) {
            return redisHash.values().stream()
                    .map(value -> redisMapper.convertValue(value, Course.class))
                    .collect(Collectors.toList());
        }
        List<Course> list = courseDao.getCourseByType(type);
        if (list != null && !list.isEmpty()) {
            Map<String, Object> courseMap = new HashMap<>();
            for (Course course : list) {
                courseMap.put(String.valueOf(course.getId()), course);
            }
            template.setValueRedisHash(cacheKey, courseMap, 1, TimeUnit.DAYS);
        }
        return list;
    }

    @Override
    public ResponseEntity<?> buySuccess(BuySuccessDTO buySuccessDTO) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/api/";
            BuySuccessDTO request = BuySuccessDTO.builder()
                    .userId(buySuccessDTO.getUserId())
                    .ptId(buySuccessDTO.getPtId())
                    .schedulesType(buySuccessDTO.getSchedulesType())
                    .startAt(new Date())
                    .sessionNumber(buySuccessDTO.getSessionNumber())
                    .price(buySuccessDTO.getPrice())
                    .build();

            BuySuccessDTO response = restTemplate.postForObject(url, request, BuySuccessDTO.class);

            if (response == null) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                        .body("Failed to complete the purchase, please try again later.");
            }

            return ResponseEntity.ok("Success");

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An error occurred while processing your request: " + e.getMessage());
        }
    }

    @Override
    public MyRespone checkAndReduceStock(Long courseId,Long orderId) {
        MyRespone myRespone = new MyRespone();
        try {
            Course course = courseDao.getCourseById(courseId);
            if(course == null){
                myRespone.setStatus(404);
                myRespone.setMessage("Course not found");
                myRespone.setData(null);
                return myRespone;
            }
            int updateRows = courseDao.updateCourseQuantity(courseId,1);
            if (updateRows > 0){
                OrderUpdate orderUpdate = OrderUpdate.builder().orderId(orderId)
                                         .status(OrderStatus.SUCCESS).build();
                orderClient.successOrder(orderUpdate);
                myRespone.setStatus(200);
                myRespone.setMessage("Stock reduced successfully");

                Course newCourse = courseRepository.getCourseById(courseId);
                String redisKey = getCourseRedisKey(newCourse);
                template.deleteCourse(redisKey, courseId);
                template.updateCourse(redisKey, courseId, newCourse);
                myRespone.setData(course);
            }else {
                sendOutOfStockMessage(courseId, orderId);
                myRespone.setStatus(400);
                myRespone.setMessage("Not enough stock");
                myRespone.setData(null);

            }
        }catch (Exception e){
            sendOutOfStockMessage(courseId, orderId);
            myRespone.setStatus(500);
            myRespone.setMessage("Internal server error:" + e.getMessage());
            myRespone.setData(null);
        }
        return myRespone;
    }

    private void sendOutOfStockMessage(Long courseId, Long orderId) {
        String message = String.format("Course ID: %d, Order ID: %d is out of stock.", courseId, orderId);
        kafkaTemplate.send("out-of-stock", message);
    }

    private String getCourseRedisKey(Course course) {
        String courseType = course.getType();
        switch (courseType.toLowerCase()) {
            case "gym":
                return "course_type_gym";
            case "yoga":
                return "course_type_yoga";
            case "pilates":
                return "course_type_pilates";
            default:
                throw new IllegalArgumentException("Unknown course type: " + courseType);
        }
    }

    @Override
    public Course getCourseById(Long courseId) {
        return courseRepository.getCourseById(courseId);
    }


}
