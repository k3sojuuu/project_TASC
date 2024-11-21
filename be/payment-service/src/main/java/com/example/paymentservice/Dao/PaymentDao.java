package com.example.paymentservice.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Component
public class PaymentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public int updateStatusPayment(String status, String transactionId, LocalDateTime updateAt) {
        String sql = "UPDATE payments SET payment_status = ?, update_at = ? WHERE transaction_id = ?";
        return jdbcTemplate.update(sql, status, updateAt, transactionId);
    }
}
