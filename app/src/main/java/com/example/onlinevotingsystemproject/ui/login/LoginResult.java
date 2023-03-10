package com.example.onlinevotingsystemproject.ui.login;

import androidx.annotation.Nullable;

import com.example.onlinevotingsystemproject.ui.CreateAccount.CreateAccountUserView;

/**
 * Authentication result : success (user details) or error message.
 */
class LoginResult {
    @Nullable
    private LoggedInUserView success;
    @Nullable
    private CreateAccountUserView createAccount;
    @Nullable
    private Integer error;

    LoginResult(@Nullable Integer error) {
        this.error = error;
    }

    LoginResult(@Nullable LoggedInUserView success) {
        this.success = success;
    }

    LoginResult(@Nullable CreateAccountUserView createAccount) {
        this.createAccount = createAccount;
    }

    @Nullable
    LoggedInUserView getSuccess() {
        return success;
    }

    @Nullable
    CreateAccountUserView getCreateAccount() {
        return createAccount;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}