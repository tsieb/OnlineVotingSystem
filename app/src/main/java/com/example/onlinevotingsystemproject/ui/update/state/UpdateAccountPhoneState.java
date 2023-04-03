package com.example.onlinevotingsystemproject.ui.update.state;

import androidx.annotation.Nullable;

public class UpdateAccountPhoneState{
    @Nullable
    private Integer phoneError;
    private boolean isDataValid;

    public UpdateAccountPhoneState(Integer phoneError) {
        this.phoneError = phoneError;
        this.isDataValid = false;
    }

    public UpdateAccountPhoneState(boolean isDataValid) {
        this.phoneError = null;
        this.isDataValid = isDataValid;
    }
    @Nullable
    public Integer getNameError() {
        return phoneError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}