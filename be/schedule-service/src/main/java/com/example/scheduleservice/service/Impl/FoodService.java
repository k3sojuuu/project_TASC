package com.example.scheduleservice.service.Impl;

import com.example.scheduleservice.model.DTO.GroupFood;
import com.example.scheduleservice.model.Food;
import com.example.scheduleservice.model.Response.MyResponse;
import com.example.scheduleservice.repository.FoodRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FoodService {
    private final FoodRepository foodRepository;

    public FoodService(FoodRepository foodRepository) {
        this.foodRepository = foodRepository;
    }

    public ResponseEntity<?> listGroupFood(String foodType){
        MyResponse response = new MyResponse();
        List<Food> list = foodRepository.getFoodByGroup(foodType);
        response.setStatus(200);
        response.setMessage("ok nhé");
        response.setData(list);
        return ResponseEntity.ok(response);
    }
}
