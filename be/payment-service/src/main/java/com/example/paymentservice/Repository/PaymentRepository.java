package com.example.paymentservice.Repository;

import com.example.paymentservice.Model.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    @Modifying
    @Query(value = "update payments" +
            " set payment_status =:status,update_at=:updateAt " +
            " where transaction_id =:transactionId",nativeQuery = true)
    int updateStatusPayment(@Param("status")String status,
                             @Param("transactionId")String transactionId,
                             @Param("updateAt")LocalDateTime updateAt);
    @Query(value = "select order_id from payments where transaction_id =:transactionId", nativeQuery = true)
    Long getOrderIdByTransactionId(@Param("transactionId") String transactionId);
    @Query(value = "select course_id from payments where transaction_id =:transactionId",nativeQuery = true)
    Long getCourseIdByTransactionId(@Param("transactionId") String transactionId);
}
