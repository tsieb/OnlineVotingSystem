package com.example.onlinevotingsystemproject.ui.update;

import androidx.annotation.Nullable;

public class UpdateAccountUserView {
    private String name;
    private String email;
    private String phone;
    private Boolean type;
    //... other data fields that may be accessible to the UI

    public UpdateAccountUserView(@Nullable String name, @Nullable String email, @Nullable String phone, @Nullable Boolean type) {
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