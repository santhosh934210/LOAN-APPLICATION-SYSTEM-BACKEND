package com.examly.springapp.util;

import java.util.Date;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.examly.springapp.model.Role;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtUtil {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expirationMillis}")
    private long expirationMillis;

    public String generateToken(String username, Role role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role.name())
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(SignatureAlgorithm.HS256, secret.getBytes())
                .compact();
    }

    public String extractUsername(String token) { return getClaim(token, Claims::getSubject); }
    public String extractRole(String token) { return (String) getAllClaims(token).get("role"); }

    public boolean isTokenValid(String token, String username) {
        final String extracted = extractUsername(token);
        return extracted.equals(username) && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token) { return getExpiration(token).before(new Date()); }
    private Date getExpiration(String token) { return getClaim(token, Claims::getExpiration); }

    private <T> T getClaim(String token, Function<Claims, T> resolver) {
        final Claims claims = getAllClaims(token);
        return resolver.apply(claims);
    }
    private Claims getAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody();
    }
}
