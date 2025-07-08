package com.personal.jobportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.personal.jobportal.util.JwtUtil;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;

@Configuration
public class SecurityConfig {


    // Step 1: Define users (in-memory for demo)
    @Bean
    UserDetailsService UserDetailsService() {
        UserDetails admin = User.withUsername("admin")
                .password("{noop}admin123")
                .roles("ADMIN")
                .build();

        UserDetails applicant = User.withUsername("user")
                .password("{noop}user123")
                .roles("APPLICANT")
                .build();

        return new InMemoryUserDetailsManager(admin, applicant);
    }

      // Step 1.5: Create JWT Auth Filter Bean
    @Bean
    public JwtAuthFilter jwtAuthFilter(JwtUtil jwtUtil, UserDetailsService userDetailsService) {
        return new JwtAuthFilter(jwtUtil, userDetailsService);
    }
    
    // Step 2: Configure security rules
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http, JwtAuthFilter jwtAuthFilter) throws Exception {
        return http.csrf(csrf -> csrf.disable()) // Disable CSRF for JWT
                .authorizeHttpRequests(auth -> auth
                    // ADMIN role required to create jobs
                    .requestMatchers(HttpMethod.POST, "/api/jobs").hasRole("ADMIN")
                    // Anyone can view all jobs (public endpoint)
                    .requestMatchers(HttpMethod.GET, "/api/jobs/all").permitAll()
                    // APPLICANT role required to apply for jobs
                    .requestMatchers(HttpMethod.POST, "/api/applications/apply/**").hasRole("APPLICANT")
                    // Must be authenticated to get JWT token (login endpoint)
                    .requestMatchers(HttpMethod.POST, "/api/auth/login").authenticated()
                    // All other requests require authentication
                    .anyRequest().authenticated())
                // ADD JWT FILTER BEFORE USERNAME/PASSWORD FILTER
                // This processes JWT tokens BEFORE basic auth
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(Customizer.withDefaults()) // Enable basic auth for login
                .build();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    //     return http.build();
    // }
}
