package com.example.scheduleservice.repository;

import com.example.scheduleservice.model.InfoCustomer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InfoCustomerRepository extends JpaRepository<InfoCustomer,Long> {
    @Query(value = "select * from info_customer where user_id = :userId ",nativeQuery = true)
    List<InfoCustomer> getInfoCustomer(@Param("userId")Long userId);
}
