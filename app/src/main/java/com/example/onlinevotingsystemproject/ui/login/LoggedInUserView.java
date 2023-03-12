package com.example.onlinevotingsystemproject.ui.login;

import java.io.Serializable;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView implements Serializable {
    private String displayName;
    private String email;
    private String phone;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String email, String phone) {
        this.displayName = displayName;
    }

    String getDisplayName() {
        return displayName;
    }
    String getEmail() {
        return email;
    }
    String getPhone() {
        return phone;
    }

}