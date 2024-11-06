package com.example.user_service.repository;

import com.example.user_service.model.entity.ForgotPassword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPasswordRepo extends JpaRepository<ForgotPassword,Long> {
    ForgotPassword findByCodeConfirm(String codeConfirm);
}
