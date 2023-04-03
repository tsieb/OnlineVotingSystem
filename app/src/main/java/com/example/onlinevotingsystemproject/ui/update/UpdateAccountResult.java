package com.example.onlinevotingsystemproject.ui.update;

import androidx.annotation.Nullable;

public class UpdateAccountResult {
    @Nullable
    private UpdateAccountUserView success;
    @Nullable
    private Integer error;

    UpdateAccountResult(@Nullable Integer error) {
        this.error = error;
    }

    UpdateAccountResult(@Nullable UpdateAccountUserView success) {
        this.success = success;
    }

    @Nullable
    UpdateAccountUserView getSuccess() {
        return success;
    }

    @Nullable
    Integer getError() {
        return error;
    }
}
