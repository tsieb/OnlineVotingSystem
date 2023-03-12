package com.example.onlinevotingsystemproject.ui.CreateAccount;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinevotingsystemproject.R;
import com.example.onlinevotingsystemproject.databinding.ActivityCreateAccountBinding;
import com.example.onlinevotingsystemproject.databinding.ActivityLoginBinding;
import com.example.onlinevotingsystemproject.ui.login.LoginActivity;
import com.example.onlinevotingsystemproject.ui.login.LoginViewModel;
import com.example.onlinevotingsystemproject.ui.login.LoginViewModelFactory;
import com.example.onlinevotingsystemproject.ui.topics.TopicActivity;

public class CreateAccountActivity extends AppCompatActivity {

    private CreateAccountViewModel createAccountViewModel;
    private ActivityCreateAccountBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCreateAccountBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        createAccountViewModel = new ViewModelProvider(this, new CreateAccountViewModelFactory())
                .get(CreateAccountViewModel.class);

        final EditText userNameEditText = binding.editTextName;
        final EditText userEmailEditText= binding.editTextEmailAddress;
        final EditText userPhoneEditText= binding.editTextPhone;
        final EditText userPasswordEditText= binding.editTextPassword;
        final EditText userRepeatEditText= binding.editTextConfirmationPassword;
        final Button submitButton = binding.buttonComplete;
        final Button cancelButton = binding.buttonCancel;

        CreateAccountUserView createAccountUserView = (CreateAccountUserView) getIntent().getSerializableExtra("create_account_user_view");
        // TODO Doesn't currently set the text
        userNameEditText.setText(createAccountUserView.getDisplayName());

        createAccountViewModel.getCreateAccountFormState().observe(this, new Observer<CreateAccountFormState>() {
            @Override
            public void onChanged(@Nullable CreateAccountFormState createAccountFormState) {
                if (createAccountFormState == null) {
                    return;
                }
                submitButton.setEnabled(createAccountFormState.isDataValid());
                if (createAccountFormState.getNameError() != null) {
                    userNameEditText.setError(getString(createAccountFormState.getNameError()));
                }
                if (createAccountFormState.getEmailError() != null) {
                    userEmailEditText.setError(getString(createAccountFormState.getEmailError()));
                }
                if (createAccountFormState.getPhoneError() != null) {
                    userPhoneEditText.setError(getString(createAccountFormState.getPhoneError()));
                }
                if (createAccountFormState.getPasswordError() != null) {
                    userPasswordEditText.setError(getString(createAccountFormState.getPasswordError()));
                }
                if (createAccountFormState.getRepeatError() != null) {
                    userRepeatEditText.setError(getString(createAccountFormState.getRepeatError()));
                }
            }
        });

        createAccountViewModel.getCreateAccountResult().observe(this, new Observer<CreateAccountResult>() {
            @Override
            public void onChanged(@Nullable CreateAccountResult createAccountResult) {
                if (createAccountResult == null) {
                    return;
                }
                Log.d("MyApp", "Got the update!");
                if (createAccountResult.getError() != null) {
                    Log.d("MyApp", "Error: " + createAccountResult.getError());
                    showCreateAccountFailed(createAccountResult.getError());
                    return;
                }
                if (createAccountResult.getSuccess() != null) {
                    updateUiWithUser(createAccountResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });

        TextWatcher afterTextChangedListener = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // ignore
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // ignore
            }

            @Override
            public void afterTextChanged(Editable s) {
                createAccountViewModel.createAccountDataChanged(userNameEditText.getText().toString(),
                        userEmailEditText.getText().toString(), userPhoneEditText.getText().toString(),
                        userPasswordEditText.getText().toString(), userRepeatEditText.getText().toString());
            }
        };

        userNameEditText.addTextChangedListener(afterTextChangedListener);
        userEmailEditText.addTextChangedListener(afterTextChangedListener);
        userPhoneEditText.addTextChangedListener(afterTextChangedListener);
        userPasswordEditText.addTextChangedListener(afterTextChangedListener);
        userRepeatEditText.addTextChangedListener(afterTextChangedListener);

        userRepeatEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    createAccountViewModel.createAccount(userNameEditText.getText().toString(),
                            userEmailEditText.getText().toString(), userPhoneEditText.getText().toString(),
                            userPasswordEditText.getText().toString(), userRepeatEditText.getText().toString());
                }
                return false;
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createAccountViewModel.createAccount(userNameEditText.getText().toString(),
                        userEmailEditText.getText().toString(), userPhoneEditText.getText().toString(),
                        userPasswordEditText.getText().toString(), userRepeatEditText.getText().toString());
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateAccountActivity.this, LoginActivity.class);
                startActivity(intent);

                // Finish the current activity
                finish();
            }
        });
    }

    private void updateUiWithUser(CreateAccountUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();

        Log.d("MyApp", "Made it to the update!");
        // Start the new activity here
        Intent intent = new Intent(CreateAccountActivity.this, TopicActivity.class);
        intent.putExtra("create_account_user_view", model);
        startActivity(intent);

        // Finish the current activity
        finish();
    }

    private void showCreateAccountFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}