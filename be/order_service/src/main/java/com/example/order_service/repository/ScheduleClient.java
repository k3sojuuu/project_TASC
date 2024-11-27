package com.example.order_service.repository;

//import org.springframework.cloud.openfeign.FeignClient;
import com.example.order_service.model.entity.Schedules;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.method.P;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "schedule-service",url = "http://localhost:8085/sv4/schedules")
public interface ScheduleClient {
    @PostMapping("/createSchedule")
    ResponseEntity<?> createSchedule(@RequestBody Schedules schedules);
}
