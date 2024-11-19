package com.example.paymentservice.Controller;

import com.example.paymentservice.Model.DTO.PaymentRequest;
import com.example.paymentservice.Repository.CourseClient;
import com.example.paymentservice.Security.JwtTokenFilter;
import com.example.paymentservice.Service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import static com.example.paymentservice.Security.JwtTokenFilter.accesstoken;

@RestController
@RequestMapping("/api/payment")
public class PaymentController {
    @Autowired
    private final CourseClient client;
    private final PaymentService paymentService;
    @Autowired
    JwtTokenFilter filter;
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    public PaymentController(CourseClient client, PaymentService paymentService) {
        this.client = client;
        this.paymentService = paymentService;
    }

    @PostMapping("/create")
    public ResponseEntity<String> createPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            String response = paymentService.createPaymentRequest(paymentRequest);
            System.out.println(response);
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
