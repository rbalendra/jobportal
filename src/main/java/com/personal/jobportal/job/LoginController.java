package com.personal.jobportal.job;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.personal.jobportal.util.JwtUtil;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public Map<String, String> login(Principal principal) {
        String username = principal.getName(); // fetched from spring security context
        // Generate JWT token using the username
        String token = jwtUtil.generateToken(username);
        //Return as JSON response
        Map<String, String> response = new HashMap<>();
        response.put("token", token);
        return response;

    }
}
