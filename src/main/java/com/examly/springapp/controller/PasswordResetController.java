package com.examly.springapp.controller;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.examly.springapp.dto.PasswordResetRequest;
import com.examly.springapp.dto.PasswordResetVerifyRequest;
import com.examly.springapp.service.PasswordResetService;

@RestController
@RequestMapping("/api/password-reset")
@RequiredArgsConstructor
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    // Step 1: Request OTP
    // @PostMapping("/request-otp")
    // public ResponseEntity<String> requestOtp(@RequestBody PasswordResetRequest request) {
    //     String response = passwordResetService.initiatePasswordReset(request.getEmail());
    //     return ResponseEntity.ok(response);
    // }

    @PostMapping("/request-otp")
   public ResponseEntity<String> requestOtp(@RequestBody PasswordResetRequest request) {
    try {
        String response = passwordResetService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok(response);
    } catch (RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}


    // // Step 2 & 3: Verify OTP and Reset Password
    // @PostMapping("/verify-otp")
    // public ResponseEntity<String> verifyOtp(@RequestBody PasswordResetVerifyRequest request) {
    //     String response = passwordResetService.verifyOtpAndResetPassword(
    //             request.getEmail(),
    //             request.getOtp(),
    //             request.getNewPassword()
    //     );
    //     return ResponseEntity.ok(response);
    // }

    // Step 2 & 3: Verify OTP and Reset Password
@PostMapping("/verify-otp")
public ResponseEntity<String> verifyOtp(@RequestBody PasswordResetVerifyRequest request) {
    try {
        String response = passwordResetService.verifyOtpAndResetPassword(
                request.getEmail(),
                request.getOtp(),
                request.getNewPassword()
        );
        return ResponseEntity.ok(response); // success
    } catch (RuntimeException ex) {
        // Send the specific error message to frontend
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }
}

}
