package com.example.paymentservice.Service.Impl;

import com.example.paymentservice.Dao.PaymentDao;
import com.example.paymentservice.Model.DTO.PaymentRequest;
import com.example.paymentservice.Model.DTO.PaymentResponseDTO;
import com.example.paymentservice.Model.DTO.PaymentSuccess;
import com.example.paymentservice.Model.DTO.RefundRequest;
import com.example.paymentservice.Model.Entity.Payment;
import com.example.paymentservice.Repository.PaymentRepository;
import com.example.paymentservice.Service.PaymentService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;

@Service
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final WebClient webClient;
    private final PaymentDao paymentDao;
    private final PaymentRepository paymentRepository;

    @Value("${payos.client-id}")
    private String clientId;

    @Value("${payos.api-key}")
    private String apiKey;

    @Value("${payos.checksum-key}")
    private String checksumKey;

    public PaymentServiceImpl(WebClient.Builder webClientBuilder, PaymentDao paymentDao, PaymentRepository paymentRepository) {
        this.webClient = webClientBuilder.baseUrl("https://api-merchant.payos.vn").build();
        this.paymentDao = paymentDao;
        this.paymentRepository = paymentRepository;
    }

//    @KafkaListener(topics = "order-status-topic", groupId = "payment-group")
//    public void listenRefundRequest(String message) {
//        log.info("Received message from Kafka: {}", message);
//
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            RefundRequest refundRequest = objectMapper.readValue(message, RefundRequest.class);
//            log.info("Processing refund for Order ID: {}, Reason: {}", refundRequest.getOrderId(), refundRequest.getReason());
//
//            processRefund(refundRequest);
//
//        } catch (JsonProcessingException e) {
//            log.error("Failed to parse message: {}", e.getMessage());
//        } catch (Exception e) {
//            log.error("Failed to process refund: {}", e.getMessage());
//        }
//    }

    @KafkaListener(topics = "out-of-stock", groupId = "order-payment-group")
    public void listenOutOfStock(String message) {
        // Xử lý thông báo khi khóa học hết hàng
        log.warn("Received out-of-stock message: {}", message);
        // Cập nhật trạng thái thanh toán hoặc thông báo người dùng
    }

    private void processRefund(RefundRequest refundRequest) {
        log.info("Refunding money for Order ID: {}", refundRequest.getOrderId());

        boolean success = false;
        try {
            success = refundPaymentGateway(refundRequest.getOrderId());
            int check = paymentDao.updateRefund("REFUND", refundRequest.getOrderId(), LocalDateTime.now());
            if (success && check > 0) {
                log.info("Refund completed for Order ID: {}", refundRequest.getOrderId());
            } else {
                log.error("Refund failed for Order ID: {}. Database update returned: {}", refundRequest.getOrderId(), check);
            }
        } catch (Exception e) {
            log.error("An error occurred while processing the refund for Order ID: {}: {}", refundRequest.getOrderId(), e.getMessage(), e);
        }
    }

    private boolean refundPaymentGateway(Long orderId) {
        log.info("Connecting to payment gateway for refund...");
        return true;
    }

    @Override
    @Transactional
    public String createPaymentRequest(PaymentRequest paymentRequest) {
        paymentRequest.setOrderCode(generateRandomNumber());
        paymentRequest.setExpiredAt(Instant.now().plusSeconds(1800).getEpochSecond());
        paymentRequest.setReturnUrl("http://localhost:8002/api/payment/payment-success");
        paymentRequest.setSignature(generateSignature(paymentRequest));
        return webClient.post()
                .uri("/v2/payment-requests")
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .header("x-client-id", clientId)
                .header("x-api-key", apiKey)
                .body(BodyInserters.fromValue(paymentRequest))
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }

    @Override
    @Transactional
    public ResponseEntity<?> saveInforPayment(Payment payment) {
        paymentRepository.save(payment);
        Long key = payment.getId();
        if (key != null){
            return ResponseEntity.ok("add ss");
        }else {
            return ResponseEntity.status(400).body("add false");
        }
    }

    @Override
    public ResponseEntity<?> paymentSuccess(PaymentSuccess paymentSuccess){
        paymentSuccess.setUpdateAt(LocalDate.now());
        int rowUpdate = paymentDao.updateStatusPayment(paymentSuccess.getPaymentStatus(),
                paymentSuccess.getTransactionId(),
                paymentSuccess.getUpdateAt().atStartOfDay());
        if (rowUpdate > 0){
            return ResponseEntity.ok("Update ss");
        } else {
            return ResponseEntity.ok("falseeeee");
        }
    }

    @Override
    public Long getOrderIdFromPayment(String transactionId) {
        return paymentRepository.getOrderIdByTransactionId(transactionId);
    }

    @Override
    public Long getCourseIdFromPayment(String transactionId) {
        return paymentRepository.getCourseIdByTransactionId(transactionId);
    }

    public static int generateRandomNumber() {
        Random random = new Random();
        int result = 0;
        for (int i = 0; i < 8; i++) {
            result = result * 10 + random.nextInt(10); // Tạo số và thêm vào kết quả
        }
        return result;
    }




//    @KafkaListener(topics = "payment-topic", groupId = "payment-group")
//    public void listenPaymentTopic(String message) {
//        ObjectMapper objectMapper = new ObjectMapper();
//        try {
//            PaymentMessage paymentMessage = objectMapper.readValue(message, PaymentMessage.class);
//            if ("PENDING".equals(paymentMessage.getStatus())) {
//                // Xử lý thanh toán cho đơn hàng với orderCode
//                Long orderCode = paymentMessage.getOrderCode();
//                processPayment(orderCode);
//                System.out.println(paymentMessage.getStatus());
//            }
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
//    }


    private void processPayment(Long orderCode) {
        // Logic xử lý thanh toán cho đơn hàng
        System.out.println("Processing payment for order: " + orderCode);

        // Gọi phương thức processPayment trong PaymentService sau khi thanh toán thành công
        // Đây là nơi bạn sẽ gửi thông điệp "SUCCESS" từ producer
    }

    private String generateSignature(PaymentRequest paymentRequest) {
        Map<String, String> signatureMap = new TreeMap<>();
        signatureMap.put("amount", String.valueOf(paymentRequest.getAmount()));
        signatureMap.put("cancelUrl", paymentRequest.getCancelUrl());
        signatureMap.put("description", paymentRequest.getDescription());
        signatureMap.put("orderCode", String.valueOf(paymentRequest.getOrderCode()));
        signatureMap.put("returnUrl", paymentRequest.getReturnUrl());

        String signatureData = signatureMap.entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue())
                .reduce((s1, s2) -> s1 + "&" + s2)
                .orElse("");

        try {
            Mac sha256_HMAC = Mac.getInstance("HmacSHA256");
            SecretKeySpec secret_key = new SecretKeySpec(checksumKey.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            sha256_HMAC.init(secret_key);

            return toHexString(sha256_HMAC.doFinal(signatureData.getBytes(StandardCharsets.UTF_8)));
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            e.printStackTrace();
            return null;
        }
    }



    private String toHexString(byte[] bytes) {
        Formatter formatter = new Formatter();
        for (byte b : bytes) {
            formatter.format("%02x", b);
        }
        return formatter.toString();
    }
}
