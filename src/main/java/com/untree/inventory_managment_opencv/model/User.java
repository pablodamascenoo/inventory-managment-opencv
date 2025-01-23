package com.untree.inventory_managment_opencv.model;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import jakarta.annotation.Generated;

@Document(collection = "users")
public class User implements UserDetails {
    @Id
    @Generated(value = "uuid")
    private String id;
    private String email;
    private String password;
    private String role;

    public User(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (role.equals("admin")) {
            return List.of(new SimpleGrantedAuthority("ADMIN_ROLE"), new SimpleGrantedAuthority("USER_ROLE"));
        } else {
            return List.of(new SimpleGrantedAuthority("USER_ROLE"));
        }
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
}
