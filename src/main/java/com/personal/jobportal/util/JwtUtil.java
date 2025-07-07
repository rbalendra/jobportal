package com.personal.jobportal.util;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
@Component
public class JwtUtil {
    
    private static final long EXPIRATION_TIME = 86400000; //1 day (in millisecs)

    @Value("${jwt.secret}")
    private String secret;
    
    private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
    }

    // GENERATE JWT Token for LoggedIn User
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email)
                .issuedAt(new Date())
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(getSigningKey())
                .compact();
    }

    // EXTRACT EMAIL from JWT Token
    public String extractEmail(String token) {
        return Jwts.parser()                 
                  .verifyWith(getSigningKey())
                  .build()
                  .parseSignedClaims(token)  
                  .getPayload()              
                  .getSubject();
    }

    // Validate JWT Token
    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email);
    }

  

}