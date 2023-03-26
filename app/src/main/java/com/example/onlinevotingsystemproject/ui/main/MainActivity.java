package com.example.onlinevotingsystemproject.ui.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.onlinevotingsystemproject.R;
import com.example.onlinevotingsystemproject.data.model.Account;
import com.example.onlinevotingsystemproject.data.model.Topic;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FloatingActionButton fabCreateTopic;
    private TopicsAdapter topicsAdapter;
    private List<Topic> topics;
    private boolean isManager;
    private FirebaseDatabase database;
    private DatabaseReference topicsRef;
    private ValueEventListener topicsListener;

    private Account loggedInUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        fabCreateTopic = findViewById(R.id.fab_create_topic);

        loggedInUser = new Account();
        loggedInUser.setEmail((String) getIntent().getStringExtra("user_email"));
        loggedInUser.setDisplayName((String) getIntent().getStringExtra("user_name"));
        loggedInUser.setUserId((String) getIntent().getStringExtra("user_id"));
        loggedInUser.setType((Boolean) getIntent().getBooleanExtra("user_type", false));

        isManager = loggedInUser.getType();
        if (isManager) {
            fabCreateTopic.setVisibility(View.VISIBLE);
        } else {
            fabCreateTopic.setVisibility(View.GONE);
        }

        topics = new ArrayList<>();
        topicsAdapter = new TopicsAdapter(this, topics, new TopicsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Topic selectedTopic = topics.get(position);

                Intent intent;
                if (isManager) {
                    intent = new Intent(MainActivity.this, ManagerStatsActivity.class);
                } else {
                    intent = new Intent(MainActivity.this, UserVoteActivity.class);
                }

                intent.putExtra("user_id",  loggedInUser.getUserId());
                intent.putExtra("topic_id", selectedTopic.getId());
                intent.putExtra("topic_title", selectedTopic.getTitle());
                intent.putExtra("topic_description", selectedTopic.getDescription());
                intent.putExtra("topic_options", new ArrayList<>(selectedTopic.getOptions()));
                startActivity(intent);
            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(topicsAdapter);

        fabCreateTopic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCreateTopicDialog();
            }
        });
    }

    private void fetchTopics() {
        DatabaseReference topicsRef = FirebaseDatabase.getInstance().getReference("topics");
        topicsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                topics.clear();
                for (DataSnapshot topicSnapshot : dataSnapshot.getChildren()) {
                    Topic topic = topicSnapshot.getValue(Topic.class);
                    if (topic != null) {
                        topics.add(topic);
                    }
                }
                topicsAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Handle error
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        fetchTopics();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (topicsListener != null) {
            topicsRef.removeEventListener(topicsListener);
        }
    }

    private void updateVote(String topicId, String userId, String choice) {
        DatabaseReference votesRef = FirebaseDatabase.getInstance().getReference("topics").child(topicId).child("votes").child(userId);
        votesRef.setValue(choice).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Vote updated successfully, update the view
                } else {
                    // Handle error
                }
            }
        });
    }

    private void createNewTopic(String title, String description, List<String> options) {
        DatabaseReference newTopicRef = FirebaseDatabase.getInstance().getReference("topics").push();
        String topicId = newTopicRef.getKey();

        Map<String, String> initialVotes = new HashMap<>();

        Topic newTopic = new Topic(topicId, title, description, options, initialVotes);
        newTopicRef.setValue(newTopic).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    // Topic created successfully, update the view
                } else {
                    // Handle error
                }
            }
        });
    }

    private void showCreateTopicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.create_topic_dialog, null);

        EditText titleEditText = view.findViewById(R.id.topic_title);
        EditText descriptionEditText = view.findViewById(R.id.topic_description);
        LinearLayout optionsLayout = view.findViewById(R.id.options_layout);
        Button addOptionButton = view.findViewById(R.id.add_option_button);

        List<String> options = new ArrayList<>();

        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText optionEditText = new EditText(MainActivity.this);
                optionEditText.setHint("Option " + (options.size() + 1));
                optionsLayout.addView(optionEditText);
                options.add("");
            }
        });

        builder.setView(view)
                .setTitle("Create a new topic")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        String title = titleEditText.getText().toString().trim();
                        String description = descriptionEditText.getText().toString().trim();

                        for (int i = 0; i < optionsLayout.getChildCount(); i++) {
                            EditText optionEditText = (EditText) optionsLayout.getChildAt(i);
                            options.set(i, optionEditText.getText().toString().trim());
                        }

                        createNewTopic(title, description, options);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        if (isManager) {
                            String title = titleEditText.getText().toString().trim();
                            String description = descriptionEditText.getText().toString().trim();

                            for (int i = 0; i < optionsLayout.getChildCount(); i++) {
                                EditText optionEditText = (EditText) optionsLayout.getChildAt(i);
                                options.set(i, optionEditText.getText().toString().trim());
                            }

                            createNewTopic(title, description, options);
                        } else {
                            Toast.makeText(MainActivity.this, "Only managers can create a new topic.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }


}


