package com.untree.inventory_managment_opencv.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.annotation.Generated;

@Document(collection = "users")
public class User {
    @Id
    @Generated(value = "uuid")
    private String id;
    private String email;
    private String password;
    private String faceHash;

    public User(String email, String password, String faceHash) {
        this.email = email;
        this.password = password;
        this.faceHash = faceHash;
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getFaceHash() {
        return faceHash;
    }
}