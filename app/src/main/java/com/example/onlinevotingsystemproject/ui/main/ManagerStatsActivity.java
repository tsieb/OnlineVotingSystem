package com.example.onlinevotingsystemproject.ui.main;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import java.util.Locale;
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

        statsLayout = findViewById(R.id.stats_layout);

        fetchVotingStats();

        Button backButton = findViewById(R.id.return_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void displayStats(Map<String, Integer> stats, int totalVotes) {
        statsLayout.removeAllViews(); // Clear previous stats

        for (Map.Entry<String, Integer> entry : stats.entrySet()) {
            String option = entry.getKey();
            int count = entry.getValue();
            String percentage = getPercentageString(count, totalVotes);

            TextView optionStatsTextView = new TextView(this);
            optionStatsTextView.setText(option + ": " + count + " (" + percentage + ")");
            statsLayout.addView(optionStatsTextView);
        }
    }

    private void fetchVotingStats() {
        DatabaseReference votesRef = FirebaseDatabase.getInstance().getReference("topics").child(topicId).child("votes");
        votesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, Integer> stats = new HashMap<>();
                int totalVotes = 0;

                for (DataSnapshot voteSnapshot : dataSnapshot.getChildren()) {
                    String vote = voteSnapshot.getValue(String.class);
                    if (vote != null) {
                        stats.put(vote, stats.getOrDefault(vote, 0) + 1);
                        totalVotes++;
                    }
                }
                displayStats(stats, totalVotes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    private String getPercentageString(int votes, int totalVotes) {
        if (totalVotes == 0) {
            return "0%";
        }

        float percentage = (float) votes / totalVotes * 100;
        return String.format(Locale.getDefault(), "%.1f%%", percentage);
    }
}
