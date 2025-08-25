package com.examly.springapp.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequest {
    @NotBlank
    private String username; // or email; we’ll accept either
    @NotBlank
    private String password;
}
