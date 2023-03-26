package com.example.onlinevotingsystemproject.ui.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinevotingsystemproject.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UserVoteActivity extends AppCompatActivity {

    private String topicId;
    private String userId;
    private String topicTitle;
    private String topicDescription;
    private ArrayList<String> topicOptions;
    private RadioGroup optionsRadioGroup;
    private Button voteButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_vote);

        userId = getIntent().getStringExtra("user_id");
        topicId = getIntent().getStringExtra("topic_id");
        topicTitle = getIntent().getStringExtra("topic_title");
        topicDescription = getIntent().getStringExtra("topic_description");
        topicOptions = getIntent().getStringArrayListExtra("topic_options");

        if (userId == null || topicId == null) {
            Toast.makeText(this, "Error loading vote activity. Please try again.", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Initialize your views, for example:
        optionsRadioGroup = findViewById(R.id.options_radio_group);
        voteButton = findViewById(R.id.vote_button);

        // Populate the RadioGroup with the topic options
        for (String option : topicOptions) {
            RadioButton radioButton = new RadioButton(this);
            radioButton.setText(option);
            optionsRadioGroup.addView(radioButton);
        }

        voteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedOptionId = optionsRadioGroup.getCheckedRadioButtonId();
                if (selectedOptionId != -1) {
                    RadioButton selectedOptionButton = findViewById(selectedOptionId);
                    String selectedOption = selectedOptionButton.getText().toString();
                    updateVote(topicId, userId, selectedOption);
                } else {
                    Toast.makeText(UserVoteActivity.this, "Please select an option.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void updateVote(String topicId, String userId, String choice) {
        Log.d("MyApp", "userId: " + userId);
        DatabaseReference votesRef = FirebaseDatabase.getInstance().getReference("topics").child(topicId).child("votes").child(userId);
        votesRef.setValue(choice).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(UserVoteActivity.this, "Vote updated successfully", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(UserVoteActivity.this, "Error updating vote", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
