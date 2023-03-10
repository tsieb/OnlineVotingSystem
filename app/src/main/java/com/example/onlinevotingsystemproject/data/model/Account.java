package com.example.onlinevotingsystemproject.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class Account {


    private String userId;
    private String displayName;
    private String email;
    private String phone;

    public Account(String userId, String displayName, String email, String phone) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
    }

    public String getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }
}