package com.example.onlinevotingsystemproject.ui.update.state;

import androidx.annotation.Nullable;

public class UpdateAccountPasswordState {
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer repeatError;
    private boolean isDataValid;

    public UpdateAccountPasswordState(Integer passwordError, Integer repeatError) {
        this.passwordError = passwordError;
        this.repeatError = repeatError;
        this.isDataValid = false;
    }

    public UpdateAccountPasswordState(boolean isDataValid) {
        this.passwordError = null;
        this.repeatError = null;
        this.isDataValid = isDataValid;
    }

    @Nullable
    public Integer getPasswordError() {
        return passwordError;
    }
    @Nullable
    public Integer getRepeatError() {
        return repeatError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}
