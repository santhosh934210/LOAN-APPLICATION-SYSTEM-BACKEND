package com.examly.springapp.dto;

import lombok.Data;

@Data
public class PasswordResetRequest {
    private String email;
    private String newPassword;
}
