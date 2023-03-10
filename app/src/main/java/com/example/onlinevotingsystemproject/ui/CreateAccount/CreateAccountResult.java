package com.example.onlinevotingsystemproject.ui.CreateAccount;

import androidx.annotation.Nullable;

/**
 * Authentication result : success (user details) or error message.
 */
class CreateAccountResult {
    @Nullable
    private CreateAccountUserView success;
    @Nullable
    private Integer error;

    CreateAccountResult(@Nullable Integer error) {
        this.error = error;
    }

    CreateAccountResult(@Nullable CreateAccountUserView success) {
        this.success = success;
    }

    @Nullable
    CreateAccountUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}