package com.example.onlinevotingsystemproject.ui.CreateAccount;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinevotingsystemproject.data.UserDataSource;
import com.example.onlinevotingsystemproject.data.UserRepository;

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given CreateAccountViewModel has a non-empty constructor
 */
public class CreateAccountViewModelFactory implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(CreateAccountViewModel.class)) {
            return (T) new CreateAccountViewModel(UserRepository.getInstance(new UserDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}