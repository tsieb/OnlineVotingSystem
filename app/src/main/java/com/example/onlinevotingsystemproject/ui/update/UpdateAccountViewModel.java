package com.example.onlinevotingsystemproject.ui.update;

import android.os.AsyncTask;
import android.util.Patterns;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.onlinevotingsystemproject.R;
import com.example.onlinevotingsystemproject.data.Result;
import com.example.onlinevotingsystemproject.data.UserRepository;
import com.example.onlinevotingsystemproject.data.model.Account;
import com.example.onlinevotingsystemproject.ui.update.state.UpdateAccountNameState;
import com.example.onlinevotingsystemproject.ui.update.state.UpdateAccountPasswordState;
import com.example.onlinevotingsystemproject.ui.update.state.UpdateAccountPhoneState;

public class UpdateAccountViewModel extends ViewModel {
    private MutableLiveData<UpdateAccountNameState> updateAccountNameState = new MutableLiveData<>(new UpdateAccountNameState(false));
    private MutableLiveData<UpdateAccountPhoneState> updateAccountPhoneState = new MutableLiveData<>(new UpdateAccountPhoneState(false));
    private MutableLiveData<UpdateAccountPasswordState> updateAccountPasswordState = new MutableLiveData<>(new UpdateAccountPasswordState(false));
    private MutableLiveData<UpdateAccountResult> updateAccountResult = new MutableLiveData<>();
    private UserRepository userRepository;

    UpdateAccountViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    LiveData<UpdateAccountNameState> getUpdateAccountNameState() {
        return updateAccountNameState;
    }
    LiveData<UpdateAccountPhoneState> getUpdateAccountPhoneState() {
        return updateAccountPhoneState;
    }
    LiveData<UpdateAccountPasswordState> getUpdateAccountPasswordState() {
        return updateAccountPasswordState;
    }

    LiveData<UpdateAccountResult> getUpdateAccountResult() {
        return updateAccountResult;
    }

    public void updateName(String name, String userId) {
        new UpdateAccountViewModel.UpdateNameTask(userRepository, updateAccountResult).execute(name, userId);
    }

    private static class UpdateNameTask extends AsyncTask<String, Void, Result<Account>> {
        private UserRepository userRepository;
        private MutableLiveData<UpdateAccountResult> updateAccountResult;

        UpdateNameTask(UserRepository userRepository, MutableLiveData<UpdateAccountResult> updateResult) {
            this.userRepository = userRepository;
            this.updateAccountResult = updateResult;
        }

        @Override
        protected Result<Account> doInBackground(String... params) {
            return userRepository.updateName(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Result<Account> result) {
            if (result instanceof Result.Success) {
                Account data = ((Result.Success<Account>) result).getData();
                updateAccountResult.setValue(new UpdateAccountResult(new UpdateAccountUserView(null, null, null, null)));
            } else {
                updateAccountResult.setValue(new UpdateAccountResult(R.string.update_failed));
            }
        }
    }

    public void updatePhone(String phone, String userId) {
        new UpdateAccountViewModel.UpdatePhoneTask(userRepository, updateAccountResult).execute(phone, userId);
    }

    private static class UpdatePhoneTask extends AsyncTask<String, Void, Result<Account>> {
        private UserRepository userRepository;
        private MutableLiveData<UpdateAccountResult> updateAccountResult;

        UpdatePhoneTask(UserRepository userRepository, MutableLiveData<UpdateAccountResult> updateResult) {
            this.userRepository = userRepository;
            this.updateAccountResult = updateResult;
        }

        @Override
        protected Result<Account> doInBackground(String... params) {
            return userRepository.updatePhone(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Result<Account> result) {
            if (result instanceof Result.Success) {
                Account data = ((Result.Success<Account>) result).getData();
                updateAccountResult.setValue(new UpdateAccountResult(new UpdateAccountUserView(null, null, null, null)));
            } else {
                updateAccountResult.setValue(new UpdateAccountResult(R.string.update_failed));
            }
        }
    }

    public void updatePassword(String password, String userId) {
        new UpdateAccountViewModel.UpdatePasswordTask(userRepository, updateAccountResult).execute(password, userId);
    }

    private static class UpdatePasswordTask extends AsyncTask<String, Void, Result<Account>> {
        private UserRepository userRepository;
        private MutableLiveData<UpdateAccountResult> updateAccountResult;

        UpdatePasswordTask(UserRepository userRepository, MutableLiveData<UpdateAccountResult> updateResult) {
            this.userRepository = userRepository;
            this.updateAccountResult = updateResult;
        }

        @Override
        protected Result<Account> doInBackground(String... params) {
            return userRepository.updatePassword(params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Result<Account> result) {
            if (result instanceof Result.Success) {
                Account data = ((Result.Success<Account>) result).getData();
                updateAccountResult.setValue(new UpdateAccountResult(new UpdateAccountUserView(null, null, null, null)));
            } else {
                updateAccountResult.setValue(new UpdateAccountResult(R.string.update_failed));
            }
        }
    }

    public void updateAccountDataChanged(String name, String phone, String password, String repeat) {
        if (!isNameValid(name)) {
            updateAccountNameState.setValue(new UpdateAccountNameState(R.string.invalid_name));
        } else {
            updateAccountNameState.setValue(new UpdateAccountNameState(true));
        }
        if (!isPhoneValid(phone)) {
            updateAccountPhoneState.setValue(new UpdateAccountPhoneState(R.string.invalid_phone));
        } else {
            updateAccountPhoneState.setValue(new UpdateAccountPhoneState(true));
        }
        if (!isPasswordValid(password)) {
            updateAccountPasswordState.setValue(new UpdateAccountPasswordState(R.string.invalid_password, null));
        } else if (!isRepeatValid(password, repeat)) {
            updateAccountPasswordState.setValue(new UpdateAccountPasswordState(null, R.string.invalid_repeat));
        } else {
            updateAccountPasswordState.setValue(new UpdateAccountPasswordState(true));
        }
    }

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

    private boolean isPasswordValid(String password) {
        return password != null && password.trim().length() > 5;
    }

    private boolean isRepeatValid(String password, String repeat) {
        return ((password != null) && (password.trim().length() > 5) && (password.equals(repeat)));
    }
}