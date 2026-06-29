package com.example.demo.Security;

import java.util.Collection;
import java.util.Collections;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class CustomUserDetails implements UserDetails {

    private String email;
    private String password;
    private String role;

    public CustomUserDetails() {
    }

    public CustomUserDetails(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // ==========================
    // User Role
    // ==========================
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        return Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));
    }

    // ==========================
    // Password
    // ==========================
    @Override
    public String getPassword() {
        return password;
    }

    // ==========================
    // Username (Email)
    // ==========================
    @Override
    public String getUsername() {
        return email;
    }

    // ==========================
    // Account Status
    // ==========================
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    // ==========================
    // Getters & Setters
    // ==========================
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
