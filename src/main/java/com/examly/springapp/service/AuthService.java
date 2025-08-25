package com.examly.springapp.service;

import com.examly.springapp.dto.AuthRequest;
import com.examly.springapp.dto.AuthResponse;
import com.examly.springapp.dto.SignupRequest;
import com.examly.springapp.model.Role;
import com.examly.springapp.model.User;
import com.examly.springapp.repository.UserRepository;
import com.examly.springapp.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authManager;
    private final PasswordEncoder encoder;
    private final UserRepository userRepo;
    private final JwtUtil jwtUtil;

    public AuthResponse signup(SignupRequest req) {
        if (userRepo.existsByUsername(req.getUsername()))
            throw new IllegalArgumentException("Username already taken");
        if (userRepo.existsByEmail(req.getEmail()))
            throw new IllegalArgumentException("Email already in use");

        Role role = req.getRole() == null ? Role.APPLICANT : req.getRole();

        User user = User.builder()
                .username(req.getUsername())
                .email(req.getEmail())
                .password(encoder.encode(req.getPassword()))
                .role(role)
                .build();
        userRepo.save(user);

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse(user.getId(), user.getUsername(), user.getRole(), token);
    }

    // public AuthResponse login(AuthRequest req) {
    //     authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    //     User user = userRepo.findByUsernameOrEmail(req.getUsername())
    //             .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
    //     String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
    //     return new AuthResponse(user.getId(), user.getUsername(), user.getRole(),token);
    //     // return new AuthResponse(token, user.getUsername(), user.getRole());
    // }

    public AuthResponse login(AuthRequest req) {
    authManager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
    User user = userRepo.findByUsernameOrEmail(req.getUsername())
            .orElseThrow(() -> new BadCredentialsException("Invalid credentials"));
    String token = jwtUtil.generateToken(user.getUsername(), user.getRole());
    
    // Pass user ID in AuthResponse
    return new AuthResponse(user.getId(), user.getUsername(), user.getRole(), token);
}



}
