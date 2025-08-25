package com.examly.springapp.dto;

import com.examly.springapp.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AuthResponse {

     private Long id;       // add user ID
    private String username;
    private Role role;
    private String token;
    

}
