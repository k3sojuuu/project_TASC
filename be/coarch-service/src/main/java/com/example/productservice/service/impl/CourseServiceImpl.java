package com.example.productservice.service.impl;

import com.example.productservice.dao.statement.CourseDaoImpl;
import com.example.productservice.model.dto.CourseDetailDTO;
import com.example.productservice.model.dto.Users;
import com.example.productservice.model.dto.request.BuySuccessDTO;
import com.example.productservice.model.dto.response.ResponseCourseSort;
import com.example.productservice.model.entity.Course;
import com.example.productservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CourseServiceImpl implements CourseService {
    @Autowired
    private RestTemplate restTemplate;
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
    public List<CourseDetailDTO> getCourseByType(String type) {
        List<Course> list = courseDao.getCourseByType(type);
        List<CourseDetailDTO> courseDetailDTOS = new ArrayList<>();
        for (Course res : list) {
            String url = "http://localhost:8000/api/sv1/user/getUser?userId=" + res.getPtId();
            Users user = null;
            try {
                user = restTemplate.getForObject(url, Users.class);
            } catch (Exception e) {
                user = new Users();
                user.setFirstName("Unknown");
                user.setLastName("");
            }
            CourseDetailDTO course = CourseDetailDTO.builder()
                    .ptName(user.getFirstName() + " " + user.getLastName())
                    .nameCoarch(res.getNameCoarch())
                    .numberSession(res.getNumberSession())
                    .description(res.getDescription())
                    .img(res.getImg())
                    .price(res.getPrice())
                    .type(res.getType())
                    .build();
            courseDetailDTOS.add(course);
        }
        return courseDetailDTOS;
    }

    @Override
    public ResponseEntity<?> buySuccess(BuySuccessDTO buySuccessDTO) {
        try {
            RestTemplate restTemplate = new RestTemplate();
            String url = "http://localhost:8080/api/your-endpoint";
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


}
