package com.example.scheduleservice.controller;

import com.example.scheduleservice.model.DTO.GroupFood;
import com.example.scheduleservice.model.Food;
import com.example.scheduleservice.service.Impl.FoodService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/food")
public class FoodController {
    private final FoodService foodService;

    public FoodController(FoodService foodService) {
        this.foodService = foodService;
    }
    @CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/getGroup")
    public ResponseEntity<?> getFoodByGroup(@RequestParam String foodType){
       return ResponseEntity.ok();
   }
}
