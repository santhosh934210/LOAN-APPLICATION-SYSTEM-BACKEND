package com.examly.springapp.repository;

import com.examly.springapp.model.Otp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OtpRepository extends JpaRepository<Otp, Long> {
    Optional<Otp> findTopByEmailOrderByExpiryTimeDesc(String email);
    Optional<Otp> findByEmailAndOtpCodeAndUsedFalse(String email, String otpCode);
}
