package com.example.onlinevotingsystemproject.data.model;

import android.text.BoringLayout;

/**
 * Data class that captures user information for logged in users retrieved from LoginRepository
 */
public class Account {


    private String userId;
    private String displayName;
    private String email;
    private String phone;
    private Boolean userType;

    public Account() {
    }
    public Account(String userId, String displayName, String email, String phone, Boolean userType) {
        this.userId = userId;
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.userType = userType;
    }

    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public Boolean getType() {
        return userType;
    }
    public void setType(Boolean userType) {
        this.userType = userType;
    }
}