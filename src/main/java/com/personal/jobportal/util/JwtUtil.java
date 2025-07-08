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
    private String secret; //Secret Key from application properties
    
    //Step 1: Create a signing key from the secret
    private SecretKey getSigningKey() {
    byte[] keyBytes = Decoders.BASE64.decode(secret);
    return Keys.hmacShaKeyFor(keyBytes);
    }

    //Step 2: GENERATE JWT Token for LoggedIn User
    public String generateToken(String email) {
        return Jwts.builder()
                .subject(email) // who the token is for
                .issuedAt(new Date()) // when it was created
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // When it expires
                .signWith(getSigningKey()) // Sign with secret key
                .compact(); // Convert to string
    }

    //Step 3: EXTRACT email from token
    public String extractEmail(String token) {
        return Jwts.parser()                 
                  .verifyWith(getSigningKey())
                  .build()
                  .parseSignedClaims(token)  
                  .getPayload()              
                  .getSubject();
    }

    //Step 4: Validate JWT Token
    public boolean validateToken(String token, String email) {
        return extractEmail(token).equals(email);
    }

  

}