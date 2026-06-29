package com.example.demo.Configuration;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {

    // Secret Key (Minimum 32 Characters)
    private static final String SECRET_KEY
            = "OptiHealthHospitalManagementSystemJWTSecretKey2026";

    // Token Validity (24 Hours)
    private static final long EXPIRATION_TIME
            = 1000 * 60 * 60 * 24;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes());
    }

    // ==============================
    // Generate JWT Token
    // ==============================
    public String generateToken(String email, String role) {

        Map<String, Object> claims = new HashMap<>();

        claims.put("role", role);

        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(
                        new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    // ==============================
    // Extract All Claims
    // ==============================
    public Claims extractAllClaims(String token) {

        return Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    // ==============================
    // Extract Email
    // ==============================
    public String extractEmail(String token) {

        return extractAllClaims(token).getSubject();
    }

    // ==============================
    // Extract Role
    // ==============================
    public String extractRole(String token) {

        return extractAllClaims(token)
                .get("role", String.class);
    }

    // ==============================
    // Extract Expiration
    // ==============================
    public Date extractExpiration(String token) {

        return extractAllClaims(token).getExpiration();
    }

    // ==============================
    // Check Token Expired
    // ==============================
    public boolean isTokenExpired(String token) {

        return extractExpiration(token)
                .before(new Date());
    }

    // ==============================
    // Validate Token
    // ==============================
    public boolean validateToken(String token, String email) {

        String extractedEmail = extractEmail(token);

        return extractedEmail.equals(email)
                && !isTokenExpired(token);
    }

}
