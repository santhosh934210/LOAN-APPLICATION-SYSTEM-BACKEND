package com.examly.springapp.dto;


import com.examly.springapp.model.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SignupRequest {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
    @NotBlank @Email
    private String email;
    private Role role; // ADMIN / AGENT / APPLICANT
}
