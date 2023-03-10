package com.example.onlinevotingsystemproject.data.model;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class Account {

    private String email;
    private String userId;
    private String displayName;

    public Account(String userId, String displayName, String email) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
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
}