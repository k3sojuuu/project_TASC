package com.example.user_service.service;

import com.example.user_service.model.DTO.response.RegisPtResponse;
import org.springframework.http.ResponseEntity;

public interface PtService {
   ResponseEntity<?> rejectPt(RegisPtResponse response);
   ResponseEntity<?> approvalPt(RegisPtResponse response);
}
