package com.example.spring.model;

import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.spring.config.Role;

import java.util.Collection;
import java.util.List;

@Entity
public class Users implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(unique = true, nullable = false)
    private String username;

    private String password;

    @Enumerated(EnumType.STRING) // Store the role as a string in the database
    private Role role;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(() -> role.name()); // Return the role without "ROLE_" prefix
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Users{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    // Implement remaining UserDetails methods
    @Override
    public boolean isAccountNonExpired() {
        return true; // Customize based on your requirements
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Customize based on your requirements
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Customize based on your requirements
    }

    @Override
    public boolean isEnabled() {
        return true; // Customize based on your requirements
    }
}
