package com.example.paymentservice.Controller;

import com.example.paymentservice.Model.DTO.PaymentRequest;
import com.example.paymentservice.Model.DTO.PaymentResponse;
import com.example.paymentservice.Model.DTO.PaymentSuccess;
import com.example.paymentservice.Model.Entity.Payment;
import com.example.paymentservice.Model.OrderUpdate;
import com.example.paymentservice.Repository.CourseClient;
import com.example.paymentservice.Repository.OrderClient;
import com.example.paymentservice.Security.JwtTokenFilter;
import com.example.paymentservice.Service.PaymentService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;

import static com.example.paymentservice.Security.JwtTokenFilter.accesstoken;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private final CourseClient client;
    private final PaymentService paymentService;
    private final OrderClient orderClient;
    private final CourseClient courseClient;
    @Autowired
    JwtTokenFilter filter;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    public PaymentController(CourseClient client, PaymentService paymentService, OrderClient orderClient, CourseClient courseClient) {
        this.client = client;
        this.paymentService = paymentService;
        this.orderClient = orderClient;
        this.courseClient = courseClient;
    }

    @GetMapping("/payment-success")
    public String paymentSuccess(
            @RequestParam String code,
            @RequestParam String id,
            @RequestParam boolean cancel,
            @RequestParam String status,
            @RequestParam Long orderCode) {
        System.out.println("Code: " + code);
        System.out.println("ID: " + id);
        System.out.println("Cancel: " + cancel);
        System.out.println("Status: " + status);
        System.out.println("Order Code: " + orderCode);
        PaymentSuccess success = PaymentSuccess.builder()
                .paymentStatus(status)
                .transactionId(id).build();
        paymentService.paymentSuccess(success);
        Long orderId = paymentService.getOrderIdFromPayment(id);
        OrderUpdate update = OrderUpdate.builder().orderId(orderId)
                .status(status).build();
        if (status.equals("PAID")){
             Long courseId = paymentService.getCourseIdFromPayment(id);
             courseClient.checkAndReduceStock(courseId);
        }
        return "Thanh toán thành công!";
    }
    @PostMapping("/create")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            String response = paymentService.createPaymentRequest(paymentRequest);
            System.out.println(response);
            ObjectMapper objectMapper = new ObjectMapper();
            PaymentResponse paymentResponse = objectMapper.readValue(response, PaymentResponse.class);
            System.out.println("data" + paymentResponse.getData().getPaymentLinkId());
            Payment payment = Payment.builder()
                    .orderId(paymentRequest.getOrderId())
                    .paymentStatus(paymentResponse.getData().getStatus())
                    .paymentMethod("PayOS")
                    .transactionId(paymentResponse.getData().getPaymentLinkId())
                    .createAt(LocalDateTime.now()).build();
            paymentService.saveInforPayment(payment);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error processing request");
        }
    }


    @PutMapping("/paid")
    public void failPayment(@RequestParam long id,@RequestParam int status){
        HttpHeaders headers=new HttpHeaders();
        headers.setBearerAuth(accesstoken);
        HttpEntity entity=new HttpEntity(headers);
        restTemplate.exchange(
                "http://localhost:8083/api/order/updateOrder?id="+id+"&status="+status,
                HttpMethod.PUT,
                entity
                ,String.class);
    }

    @GetMapping("/claimsHelloWorld")
    public ResponseEntity<?> claimsHello(){
        String claims = client.getHelloWorld();
        return ResponseEntity.ok("claims message from course:" + claims);
    };

}
