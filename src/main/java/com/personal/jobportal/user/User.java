package com.personal.jobportal.user;

import java.security.AuthProvider;

import jakarta.annotation.Generated;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users") // Assuming you want to create a table named 'users'
public class User {

    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;

    @Column(nullable=false)
    private String name;

    @Column(nullable=false, unique=true)
    private String email;

    @Column(nullable=true) // OAuth providers may not require a password
    private String password; 

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Store enum as a string in the database
    private AuthProvider authProvider;

    public enum AuthProvider {
        GOOGLE, GITHUB, LOCAL
    }

    @Column(nullable = false)
    @Enumerated(EnumType.STRING) // Store enum as a string in the database
    private Role role;

    public enum Role {
        USER, ADMIN
    }

    /* ------------------------- NO ARGUMENT CONSTRUCTOR ------------------------ */
    public User() {
        // Default constructor for JPA
    }

    /* ------------------------- ALL ARGUMENT CONSTRUCTOR ------------------------ */
    // contructor for normal users (requring email and password)
    public User(String name, String email, String password, AuthProvider provider, Role role) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.authProvider = provider;
        this.role = role;
    }

    // constructor for OAuth users (no password required)
    public User(String name, String email, AuthProvider provider, Role role) {
        this.name = name;
        this.email = email;
        this.password = null; // No password for OAuth users
        this.authProvider = provider;
        this.role = role;
    }
    
    
}
