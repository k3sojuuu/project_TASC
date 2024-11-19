package com.example.order_service.store_procedure;

import com.example.order_service.model.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
@Repository
public interface OrderStoreProcedure extends JpaRepository<Order,Long> {
    @Procedure(name = "GetAllOrders")
    List<Order> getAllOrders();

    @Procedure(name = "createOrder")
    Long createOrder(@Param("courseId")Long courseId,
                     @Param("userId")Long userId,
                     @Param("quantity")Long quantity,
                     @Param("totalPrice")Float totalPrice,
                     @Param("status")String status,
                     @Param("createAt") LocalDateTime createAt);

}
