package com.example.scheduleservice.repository;

import com.example.scheduleservice.model.Food;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodRepository extends JpaRepository<Food,Long> {

    List<Food> getFoodByGroup(String group);
}
