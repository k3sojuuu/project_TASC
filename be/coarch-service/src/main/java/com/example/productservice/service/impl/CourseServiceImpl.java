package com.example.productservice.service.impl;

import com.example.productservice.constan.OrderStatus;
import com.example.productservice.dao.statement.CourseDaoImpl;
import com.example.productservice.model.MyRespone;
import com.example.productservice.model.dto.request.BuySuccessDTO;
import com.example.productservice.model.dto.request.OrderUpdate;
import com.example.productservice.model.dto.response.ResponseCourseSort;
import com.example.productservice.model.entity.Course;
import com.example.productservice.repository.OrderClient;
import com.example.productservice.repository.PaymentClient;
import com.example.productservice.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final RestTemplate restTemplate;
    private final CourseDaoImpl courseDao;
    private final OrderClient orderClient;



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
                myRespone.setData(course);
            }else {
                myRespone.setStatus(400);
                myRespone.setMessage("Not enough stock");
                myRespone.setData(null);

            }
        }catch (Exception e){
            myRespone.setStatus(500);
            myRespone.setMessage("Internal server error:" + e.getMessage());
            myRespone.setData(null);
        }
        return myRespone;
    }


}
