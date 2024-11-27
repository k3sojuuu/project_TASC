package com.example.paymentservice.Dao;

import com.example.paymentservice.Model.DTO.PaymentResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.security.Timestamp;
import java.time.LocalDateTime;

@Component
public class PaymentDao {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    private RowMapper<PaymentResponseDTO> paymentResponseMapper = (rs, rowNum) -> {
        PaymentResponseDTO responseDTO = new PaymentResponseDTO();
        responseDTO.setOrderId(rs.getLong("order_id"));
        responseDTO.setCourseId(rs.getLong("course_id"));
        return responseDTO;
    };
    public int updateStatusPayment(String status, String transactionId, LocalDateTime updateAt) {
        String sql = "UPDATE payments SET payment_status = ?, update_at = ? WHERE transaction_id = ?";
        return jdbcTemplate.update(sql, status, updateAt, transactionId);
    }

    public int updateRefund(String status, Long orderId, LocalDateTime updateAt){
        String sql = "UPDATE payments SET payment_status = ?,update_at = ? where order_id = ?";
        return jdbcTemplate.update(sql, status,updateAt,orderId);
    }

    public PaymentResponseDTO getOrderIdAndCourseByTransactionId(String transactionId) {
        String query = "SELECT order_id, course_id FROM payments WHERE transaction_id = ?";
        return jdbcTemplate.queryForObject(query, paymentResponseMapper, transactionId);
    }
}
