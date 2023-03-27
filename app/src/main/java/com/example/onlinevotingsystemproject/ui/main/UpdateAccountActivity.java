package com.example.onlinevotingsystemproject.ui.main;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinevotingsystemproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UpdateAccountActivity extends AppCompatActivity {

    private EditText etDisplayName, etPhoneNumber;
    private Button btnUpdateAccount;
    private String userId;
    private DatabaseReference userRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_account);

        etDisplayName = findViewById(R.id.et_display_name);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        btnUpdateAccount = findViewById(R.id.btn_update_account);

        userId = getIntent().getStringExtra("user_id");

        userRef = FirebaseDatabase.getInstance().getReference("accounts").child(userId);

        btnUpdateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateAccount();
            }
        });
    }

    private void updateAccount() {
        String displayName = etDisplayName.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        if (TextUtils.isEmpty(displayName)) {
            etDisplayName.setError("Display name is required.");
            return;
        }

        if (TextUtils.isEmpty(phoneNumber)) {
            etPhoneNumber.setError("Phone number is required.");
            return;
        }

        userRef.child("name").setValue(displayName);
        userRef.child("phone").setValue(phoneNumber)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(UpdateAccountActivity.this, "Account updated successfully.", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Toast.makeText(UpdateAccountActivity.this, "Error updating account. Please try again.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}