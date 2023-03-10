package com.example.onlinevotingsystemproject.ui.CreateAccount;

import androidx.annotation.Nullable;

/**
 * Data validation state of the login form.
 */
class CreateAccountFormState {
    @Nullable
    private Integer nameError;
    @Nullable
    private Integer emailError;
    @Nullable
    private Integer phoneError;
    @Nullable
    private Integer passwordError;
    @Nullable
    private Integer repeatError;
    private boolean isDataValid;

    CreateAccountFormState(@Nullable Integer nameError, @Nullable Integer emailError, @Nullable Integer phoneError, @Nullable Integer passwordError, @Nullable Integer repeatError) {
        this.nameError = nameError;
        this.emailError = emailError;
        this.phoneError = phoneError;
        this.passwordError = passwordError;
        this.repeatError = repeatError;
        this.isDataValid = false;
    }

    CreateAccountFormState(boolean isDataValid) {
        this.nameError = null;
        this.emailError = null;
        this.phoneError = null;
        this.passwordError = null;
        this.repeatError = null;
        this.isDataValid = isDataValid;
    }
    @Nullable
    Integer getNameError() {
        return nameError;
    }

    @Nullable
    Integer getEmailError() {
        return emailError;
    }

    @Nullable
    Integer getPhoneError() {
        return phoneError;
    }

    @Nullable
    Integer getPasswordError() {
        return passwordError;
    }

    @Nullable
    Integer getRepeatError() {
        return repeatError;
    }

    boolean isDataValid() {
        return isDataValid;
    }
}