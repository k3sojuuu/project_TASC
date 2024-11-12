package com.example.scheduleservice.service;

import com.example.scheduleservice.model.InfoCustomer;
import org.springframework.http.ResponseEntity;

public interface InfoCustomerService {
    ResponseEntity<?> updateStatusCustomer(InfoCustomer infoCustomer);
    ResponseEntity<?> getInfoCustomer(Long userId);

}
