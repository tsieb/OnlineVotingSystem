package com.example.onlinevotingsystemproject.ui.topics;

import android.os.Bundle;

import com.example.onlinevotingsystemproject.R;
import com.example.onlinevotingsystemproject.ui.CreateAccount.CreateAccountUserView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.onlinevotingsystemproject.databinding.ActivityTopicBinding;

public class TopicActivity extends AppCompatActivity {

    private AppBarConfiguration appBarConfiguration;
    private ActivityTopicBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO May not work because abject type is different
        // May need to combine all user view classes
        TopicsUserView TopicsUserView = (TopicsUserView) getIntent().getSerializableExtra("create_account_user_view");
        binding = ActivityTopicBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_topic);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_topic);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }
}