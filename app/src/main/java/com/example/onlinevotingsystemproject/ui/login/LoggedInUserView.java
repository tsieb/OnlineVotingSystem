package com.example.onlinevotingsystemproject.ui.login;

/**
 * Class exposing authenticated user details to the UI.
 */
class LoggedInUserView{
    private final String displayName;
    private final String email;
    private final String phone;
    private final String userID;
    private final Boolean type;
    //... other data fields that may be accessible to the UI

    LoggedInUserView(String displayName, String email, String phone, Boolean type, String userID) {
        this.displayName = displayName;
        this.email = email;
        this.phone = phone;
        this.type = type;
        this.userID = userID;
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

    Boolean getType() {
        return type;
    }
    String getUserID() {
        return userID;
    }

}