package com.example.onlinevotingsystemproject.ui.CreateAccount;

import android.os.AsyncTask;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinevotingsystemproject.R;
import com.example.onlinevotingsystemproject.data.UserRepository;
import com.example.onlinevotingsystemproject.data.Result;
import com.example.onlinevotingsystemproject.data.model.Account;

public class CreateAccountViewModel extends ViewModel {

    private MutableLiveData<CreateAccountFormState> createAccountFormState = new MutableLiveData<>();
    private MutableLiveData<CreateAccountResult> createAccountResult = new MutableLiveData<>();
    private UserRepository userRepository;

    CreateAccountViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<CreateAccountFormState> getCreateAccountFormState() {
        return createAccountFormState;
    }

    LiveData<CreateAccountResult> getCreateAccountResult() {
        return createAccountResult;
    }

    public void createAccount(String name, String email, String phone, String password, String repeat, Boolean type) {
        // can be launched in a separate asynchronous job
        new CreateAccountTask(userRepository, createAccountResult).execute(name, email, phone, password, repeat, String.valueOf(type)); // Very lazy way to pass bool
    }

    private static class CreateAccountTask extends AsyncTask<String, Void, Result<Account>> {
        //TODO Check database for user details and add details

        private UserRepository userRepository;
        private MutableLiveData<CreateAccountResult> createAccountResult;

        CreateAccountTask(UserRepository userRepository, MutableLiveData<CreateAccountResult> loginResult) {
            this.userRepository = userRepository;
            this.createAccountResult = loginResult;
        }

        @Override
        protected Result<Account> doInBackground(String... params) {
            return userRepository.createAccount(params[0], params[1], params[2], params[3], params[4], Boolean.parseBoolean(params[4]));
        }

        @Override
        protected void onPostExecute(Result<Account> result) {
            if (result instanceof Result.Success) {
                Account data = ((Result.Success<Account>) result).getData();
                createAccountResult.setValue(new CreateAccountResult(new CreateAccountUserView(data.getDisplayName(), data.getEmail(), data.getPhone(), data.getType())));
            } else {
                createAccountResult.setValue(new CreateAccountResult(R.string.login_failed));
            }
        }
    }

    public void createAccountDataChanged(String name, String email, String phone, String password, String repeat) {
        if (!isNameValid(name)) {
            createAccountFormState.setValue(new CreateAccountFormState(R.string.invalid_username, null, null, null, null));
        } else if (!isEmailValid(email)) {
            createAccountFormState.setValue(new CreateAccountFormState(null, R.string.invalid_password, null, null, null));
        } else if (!isPhoneValid(phone)) {
            createAccountFormState.setValue(new CreateAccountFormState(null, null, R.string.invalid_password, null, null));
        } else if (!isPasswordValid(password)) {
            createAccountFormState.setValue(new CreateAccountFormState(null, null, null, R.string.invalid_password, null));
        } else if (!isRepeatValid(password, repeat)) {
            createAccountFormState.setValue(new CreateAccountFormState(null, null, null, null, R.string.invalid_password));
        } else {
            createAccountFormState.setValue(new CreateAccountFormState(true));
        }
    }

    // A placeholder name validation check
    private boolean isNameValid(String name) {
        if (name == null) {
            return false;
        }
        if (name.matches("[a-zA-Z]+")) {
            return true;
        } else {
            return !name.trim().isEmpty();
        }
    }

    // A placeholder email validation check
    private boolean isEmailValid(String email) {
        if (email == null) {
            return false;
        }
        if (email.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(email).matches();
        } else {
            return !email.trim().isEmpty();
        }
    }

    // A placeholder phone validation check
    private boolean isPhoneValid(String phone) {
        if (phone == null) {
            return false;
        }
        if (phone.matches("[0-9]+")) {
            return Patterns.PHONE.matcher(phone).matches();
        } else {
            return !phone.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    // A placeholder repeat validation check
    private boolean isRepeatValid(String password, String repeat) {
        return ((password != null) && (password.trim().length() > 5) && (password.equals(repeat)));
    }
}