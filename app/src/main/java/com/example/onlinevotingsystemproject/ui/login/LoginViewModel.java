package com.example.onlinevotingsystemproject.ui.login;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import android.os.AsyncTask;
import android.util.Patterns;

import com.example.onlinevotingsystemproject.data.LoginRepository;
import com.example.onlinevotingsystemproject.data.Result;
import com.example.onlinevotingsystemproject.data.model.Account;
import com.example.onlinevotingsystemproject.R;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<LoginFormState> loginFormState = new MutableLiveData<>();
    private MutableLiveData<LoginResult> loginResult = new MutableLiveData<>();
    private LoginRepository loginRepository;

    LoginViewModel(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    LiveData<LoginFormState> getLoginFormState() {
        return loginFormState;
    }

    LiveData<LoginResult> getLoginResult() {
        return loginResult;
    }

    public void login(String username, String password) {
        // can be launched in a separate asynchronous job
        new LoginTask(loginRepository, loginResult).execute(username, password);
    }

    private static class LoginTask extends AsyncTask<String, Void, Result<Account>> {

        private LoginRepository loginRepository;
        private MutableLiveData<LoginResult> loginResult;

        LoginTask(LoginRepository loginRepository, MutableLiveData<LoginResult> loginResult) {
            this.loginRepository = loginRepository;
            this.loginResult = loginResult;
        }

        @Override
        protected Result<Account> doInBackground(String... params) {
            return loginRepository.login(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Result<Account> result) {
            if (result instanceof Result.Success) {
                Account data = ((Result.Success<Account>) result).getData();
                loginResult.setValue(new LoginResult(new LoggedInUserView(data.getDisplayName())));
            } else {
                loginResult.setValue(new LoginResult(R.string.login_failed));
            }
        }
    }

    public void loginDataChanged(String username, String password) {
        if (!isUserNameValid(username)) {
            loginFormState.setValue(new LoginFormState(R.string.invalid_username, null));
        } else if (!isPasswordValid(password)) {
            loginFormState.setValue(new LoginFormState(null, R.string.invalid_password));
        } else {
            loginFormState.setValue(new LoginFormState(true));
        }
    }

    // A placeholder username validation check
    private boolean isUserNameValid(String username) {
        if (username == null) {
            return false;
        }
        if (username.contains("@")) {
            return Patterns.EMAIL_ADDRESS.matcher(username).matches();
        } else {
            return !username.trim().isEmpty();
        }
    }

    // A placeholder password validation check
    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }
}