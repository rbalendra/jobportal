package com.personal.jobportal.config;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

import com.personal.jobportal.util.JwtUtil;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// REMOVED @Component because we're creating this as a @Bean in SecurityConfig
public class JwtAuthFilter extends OncePerRequestFilter {

    // Use final fields for constructor injection
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;

    // Constructor injection - dependencies provided by SecurityConfig
    public JwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        // Step 1: Check if request has Authorization header
        final String authHeader = request.getHeader("Authorization");
        
        String token = null;
        String username = null;

        // Step 2: Extract token from "Bearer <token>" format
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7);
            try {
                // Step 3: Extract username from token
                username = jwtUtil.extractEmail(token);
            } catch (Exception e) {
                System.out.println("Invalid Token: " + e.getMessage());
            }
        }
        
        // Step 4: If we have a username and no existing authentication
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Step 5: Load user details
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);

            // Step 6: Validate token
            if (jwtUtil.validateToken(token, userDetails.getUsername())) {
                // Step 7: Create authentication token
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails,
                        null, userDetails.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // Step 8: Set authentication in Spring Security context
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        // Step 9: Continue with the request
        filterChain.doFilter(request, response);

    }


    
}
