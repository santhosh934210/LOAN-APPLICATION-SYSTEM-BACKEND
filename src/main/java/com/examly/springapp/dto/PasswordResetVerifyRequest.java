package com.examly.springapp.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordResetVerifyRequest {
    private String email;
    private String otp;
    private String newPassword;
}

