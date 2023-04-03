package com.example.onlinevotingsystemproject.ui.update;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinevotingsystemproject.data.UserDataSource;
import com.example.onlinevotingsystemproject.data.UserRepository;

public class UpdateAccountViewModelFactory  implements ViewModelProvider.Factory {

    @NonNull
    @Override
    @SuppressWarnings("unchecked")
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(UpdateAccountViewModel.class)) {
            return (T) new UpdateAccountViewModel(UserRepository.getInstance(new UserDataSource()));
        } else {
            throw new IllegalArgumentException("Unknown ViewModel class");
        }
    }
}