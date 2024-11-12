package com.example.scheduleservice.controller;

import com.example.scheduleservice.model.InfoCustomer;
import com.example.scheduleservice.service.Impl.InfoCustomerServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer")
public class InfoCustomerController {
    @Autowired
    private InfoCustomerServiceImpl service;

    @PostMapping("/updateYourStatus")
    public ResponseEntity<?> updateYourStatus(@RequestBody InfoCustomer infoCustomer){
        return service.updateStatusCustomer(infoCustomer);
    }
    @GetMapping("/getYourStatus")
    public ResponseEntity<?> getYourStatus(@RequestParam Long userId){
        return service.getInfoCustomer(userId);
    }
}
