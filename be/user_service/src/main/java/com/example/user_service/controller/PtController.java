package com.example.user_service.controller;

import com.example.user_service.model.DTO.response.RegisPtResponse;
import com.example.user_service.service.implement.PtServiceImpl;
import com.example.user_service.service.implement.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sv1/pt")
public class PtController {
    private final UserServiceImpl userService;
    private final PtServiceImpl ptService;
    public PtController(UserServiceImpl userService, PtServiceImpl ptService) {
        this.userService = userService;
        this.ptService = ptService;
    }

    @PostMapping("/reject")
    public ResponseEntity<?> rejectPT(@RequestBody RegisPtResponse response){
        return ptService.rejectPt(response);
    }
    @PostMapping("/accepts")
    public ResponseEntity<?> acceptsPT(@RequestBody RegisPtResponse response){
        return ptService.approvalPt(response);
    }
}
