package com.example.onlinevotingsystemproject.ui.update.state;

import androidx.annotation.Nullable;

public class UpdateAccountNameState{
    @Nullable
    private Integer nameError;
    private boolean isDataValid;

    public UpdateAccountNameState(Integer nameError) {
        this.nameError = nameError;
        this.isDataValid = false;
    }

    public UpdateAccountNameState(boolean isDataValid) {
        this.nameError = null;
        this.isDataValid = isDataValid;
    }
    @Nullable
    public Integer getNameError() {
        return nameError;
    }

    public boolean isDataValid() {
        return isDataValid;
    }
}