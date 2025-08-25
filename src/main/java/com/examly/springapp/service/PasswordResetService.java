package com.examly.springapp.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    // Temporary OTP storage (in-memory)
    private final Map<String, OtpDetails> otpStorage = new HashMap<>();

    // Step 1: Request OTP
    public String initiatePasswordReset(String email) {
        System.out.println("Initiating password reset for email: " + email);
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found with this email"));
        
        // User user = userRepository.findByEmail(email)
        // .orElseThrow(() -> new RuntimeException("No registration found with this email"));

        String otp = generateOtp();
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);
        otpStorage.put(email, new OtpDetails(otp, expiryTime));

        String subject = "Password Reset OTP";
        String body = "Your OTP for password reset is: " + otp + ". It is valid for 5 minutes.";

        emailService.sendEmail(email, subject, body);

        return "OTP sent to your email: " + email;
    }

    // Step 2 & 3: Verify OTP and reset password
    public String verifyOtpAndResetPassword(String email, String otp, String newPassword) {
        if (!otpStorage.containsKey(email)) {
            throw new RuntimeException("OTP request not found for this email");
        }

        OtpDetails otpDetails = otpStorage.get(email);

        if (otpDetails.getExpiryTime().isBefore(LocalDateTime.now())) {
            otpStorage.remove(email);
            throw new RuntimeException("OTP expired");
        }

        if (!otpDetails.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        // Update password
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        // Remove OTP after success
        otpStorage.remove(email);

        return "Password reset successful";
    }

    // Generate 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }

    // Inner class for OTP and expiry
    private static class OtpDetails {
        private final String otp;
        private final LocalDateTime expiryTime;

        public OtpDetails(String otp, LocalDateTime expiryTime) {
            this.otp = otp;
            this.expiryTime = expiryTime;
        }

        public String getOtp() {
            return otp;
        }

        public LocalDateTime getExpiryTime() {
            return expiryTime;
        }
    }
}
