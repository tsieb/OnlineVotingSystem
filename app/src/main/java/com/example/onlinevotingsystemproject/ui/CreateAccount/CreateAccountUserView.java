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
    //... other data fields that may be accessible to the UI

    public CreateAccountUserView(@Nullable String name, String email, @Nullable String phone) {
        this.name = name;
        this.email = name;
        this.phone = phone;
    }

    String getDisplayName() {
        return email;
    }
}