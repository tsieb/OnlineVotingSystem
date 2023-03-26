package com.example.onlinevotingsystemproject.ui.main;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.onlinevotingsystemproject.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ManagerStatsActivity extends AppCompatActivity {

    private String topicId;
    private String topicTitle;
    private String topicDescription;
    private ArrayList<String> topicOptions;
    private LinearLayout statsLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_stats);

        topicId = getIntent().getStringExtra("topic_id");
        topicTitle = getIntent().getStringExtra("topic_title");
        topicDescription = getIntent().getStringExtra("topic_description");
        topicOptions = getIntent().getStringArrayListExtra("topic_options");

        // Initialize your views, for example:
        statsLayout = findViewById(R.id.stats_layout);

        fetchVotingStats();
    }

    private void displayStats(Map<String, Integer> stats) {
        statsLayout.removeAllViews(); // Clear previous stats

        for (String option : topicOptions) {
            Integer count = stats.getOrDefault(option, 0);

            TextView statsTextView = new TextView(this);
            statsTextView.setText(option + ": " + count);
            statsLayout.addView(statsTextView);
        }
    }

    private void fetchVotingStats() {
        DatabaseReference votesRef = FirebaseDatabase.getInstance().getReference("topics").child(topicId).child("votes");
        votesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Integer> stats = new HashMap<>();
                for (DataSnapshot voteSnapshot : dataSnapshot.getChildren()) {
                    String vote = voteSnapshot.getValue(String.class);
                    if (vote != null) {
                        stats.put(vote, stats.getOrDefault(vote, 0) + 1);
                    }
                }
                displayStats(stats);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }
}
