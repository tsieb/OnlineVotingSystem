package com.example.onlinevotingsystemproject.ui.update;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.onlinevotingsystemproject.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateAccountActivity extends AppCompatActivity {

    private EditText displayNameEditText, phoneNumberEditText, newPasswordEditText, confirmPasswordEditText;
    private Button updateDisplayNameButton, updatePhoneNumberButton, updatePasswordButton;

    private SharedPreferences sharedPreferences;

    private String userId;
    private DatabaseReference userRef;

    private UpdateAccountViewModel updateAccountViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        displayNameEditText = findViewById(R.id.display_name_edit_text);
        phoneNumberEditText = findViewById(R.id.phone_number_edit_text);
        newPasswordEditText = findViewById(R.id.new_password_edit_text);
        confirmPasswordEditText = findViewById(R.id.confirm_password_edit_text);

        updateDisplayNameButton = findViewById(R.id.btn_update_name);
        updatePhoneNumberButton = findViewById(R.id.update_phone_number_button);
        updatePasswordButton = findViewById(R.id.update_password_button);

        updateAccountViewModel = new ViewModelProvider(this, new UpdateAccountViewModelFactory())
                .get(UpdateAccountViewModel.class);

        updateAccountViewModel.getUpdateAccountNameState().observe(this, updateAccountNameState -> {
            if (updateAccountNameState == null) {
                return;
            }
            updateDisplayNameButton.setEnabled(updateAccountNameState.isDataValid());
            if (updateAccountNameState.getNameError() != null) {
                displayNameEditText.setError(getString(updateAccountNameState.getNameError()));
            }
        });

        updateAccountViewModel.getUpdateAccountPhoneState().observe(this, updateAccountPhoneState -> {
            if (updateAccountPhoneState == null) {
                return;
            }
            updatePhoneNumberButton.setEnabled(updateAccountPhoneState.isDataValid());
            if (updateAccountPhoneState.getNameError() != null) {
                phoneNumberEditText.setError(getString(updateAccountPhoneState.getNameError()));
            }
        });

        updateAccountViewModel.getUpdateAccountPasswordState().observe(this, updateAccountPasswordState -> {
            if (updateAccountPasswordState == null) {
                return;
            }
            updatePasswordButton.setEnabled(updateAccountPasswordState.isDataValid());
            if (updateAccountPasswordState.getPasswordError() != null) {
                newPasswordEditText.setError(getString(updateAccountPasswordState.getPasswordError()));
            }
            if (updateAccountPasswordState.getRepeatError() != null) {
                confirmPasswordEditText.setError(getString(updateAccountPasswordState.getRepeatError()));
            }
        });

        updateAccountViewModel.getUpdateAccountResult().observe(this, updateAccountResult -> {
            if (updateAccountResult == null) {
                return;
            }
            //loadingProgressBar.setVisibility(View.GONE); // No loading bar implemented here yet
            if (updateAccountResult.getError() != null) {
                Toast.makeText(UpdateAccountActivity.this, "Update failed, please try again.", Toast.LENGTH_SHORT).show();
                return;
            }
            else if (updateAccountResult.getSuccess() != null) {
                Toast.makeText(UpdateAccountActivity.this, "Update success!", Toast.LENGTH_SHORT).show();
            }
            setResult(Activity.RESULT_OK);
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
                updateAccountViewModel.updateAccountDataChanged(displayNameEditText.getText().toString(),
                        phoneNumberEditText.getText().toString(), newPasswordEditText.getText().toString(),
                        confirmPasswordEditText.getText().toString());}
        };

        displayNameEditText.addTextChangedListener(afterTextChangedListener);
        phoneNumberEditText.addTextChangedListener(afterTextChangedListener);
        newPasswordEditText.addTextChangedListener(afterTextChangedListener);
        confirmPasswordEditText.addTextChangedListener(afterTextChangedListener);

        userId = getIntent().getStringExtra("user_id");
        userRef = FirebaseDatabase.getInstance().getReference("accounts").child(userId);

        updateDisplayNameButton.setOnClickListener(v -> {
            //loadingProgressBar.setVisibility(View.VISIBLE);
            updateAccountViewModel.updateName(displayNameEditText.getText().toString(), userId);
        });

        updatePhoneNumberButton.setOnClickListener(v -> {
            //loadingProgressBar.setVisibility(View.VISIBLE);
            updateAccountViewModel.updatePhone(phoneNumberEditText.getText().toString(), userId);
        });

        updatePasswordButton.setOnClickListener(v -> {
            //loadingProgressBar.setVisibility(View.VISIBLE);
            updateAccountViewModel.updatePassword(newPasswordEditText.getText().toString(), userId);
        });


        Button backButton = findViewById(R.id.update_back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}