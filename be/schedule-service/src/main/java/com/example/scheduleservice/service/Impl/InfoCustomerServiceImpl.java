package com.example.scheduleservice.service.Impl;

import com.example.scheduleservice.model.DTO.InfoCustomerDTO;
import com.example.scheduleservice.model.InfoCustomer;
import com.example.scheduleservice.repository.InfoCustomerRepository;
import com.example.scheduleservice.service.InfoCustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class InfoCustomerServiceImpl implements InfoCustomerService {
    @Autowired
    private InfoCustomerRepository repository;
    @Override
    public ResponseEntity<?> updateStatusCustomer(InfoCustomer infoCustomer) {
        InfoCustomer info = InfoCustomer.builder()
                .userId(infoCustomer.getUserId())
                .bust(infoCustomer.getBust())
                .waist(infoCustomer.getWaist())
                .hips(infoCustomer.getHips())
                .weight(infoCustomer.getWeight())
                .updateAt(new Date())
                .fatRate(infoCustomer.getFatRate())
                .muscleRate(infoCustomer.getMuscleRate()).build();
        repository.save(info);
        Long key = info.getId();
        if (key != null){
            return ResponseEntity.ok("Create success");
        }else {
            return ResponseEntity.ok("fail");
        }
    }

    @Override
    public ResponseEntity<?> getInfoCustomer(Long userId) {
        List<InfoCustomer> list = repository.getInfoCustomer(userId);
        InfoCustomerDTO dto = new InfoCustomerDTO();
        dto.setUserId(userId);
        dto.setListInfo(list);
        if (list != null){
            return ResponseEntity.ok(dto);
        }else {
            return ResponseEntity.status(404).body("not found");
        }
    }
}
