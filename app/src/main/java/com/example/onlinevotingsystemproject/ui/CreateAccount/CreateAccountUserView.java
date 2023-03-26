package com.example.onlinevotingsystemproject.ui.CreateAccount;

import androidx.annotation.Nullable;

import java.io.Serializable;

/**
 * Class exposing new user details to the UI.
 */
public class CreateAccountUserView implements Serializable {
    private String name;
    private String email;
    private String phone;
    private Boolean type;
    //... other data fields that may be accessible to the UI

    public CreateAccountUserView(@Nullable String name, String email, @Nullable String phone, @Nullable Boolean type) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.type = type;
    }

    public String getDisplayName() {
        return name;
    }

    public String getEmail() {
        return email;
    }
}