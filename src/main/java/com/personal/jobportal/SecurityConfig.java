package com.personal.jobportal;

import org.springframework.boot.actuate.autoconfigure.observation.ObservationProperties.Http;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.core.userdetails.User;

@Configuration
public class SecurityConfig {

    // http basic auth where your username and password is sent everytime

    @Bean
    UserDetailsService UserDetailsService() {

        UserDetails admin = User.withUsername("admin").password("{noop}admin123").roles("ADMIN").build();

        UserDetails applicant = User.withUsername("user").password("{noop}user123").roles("APPLICANT").build();

        return new InMemoryUserDetailsManager(admin, applicant);

    }
    
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth->auth.requestMatchers(HttpMethod.POST, "/api/jobs").hasRole("ADMIN")
        .requestMatchers(HttpMethod.GET, "/api/jobs/all").permitAll()
        .requestMatchers(HttpMethod.POST, "/api/applications/apply/**").hasRole("APPLICANT").requestMatchers(HttpMethod.POST, "/api/auth/login").authenticated().anyRequest().authenticated())
        .httpBasic(Customizer.withDefaults())
                .build();
    }

    // @Bean
    // public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    //     http.csrf(csrf -> csrf.disable())
    //         .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());
    //     return http.build();
    // }
}
