package com.example.onlinevotingsystemproject.data;

import android.util.Log;

import com.example.onlinevotingsystemproject.data.model.Account;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("accounts");

    public Result<Account> login(String email, String password) {
        try {
            Task<DataSnapshot> dataSnapshotTask = accountsRef.get();
            Tasks.await(dataSnapshotTask);
            DataSnapshot dataSnapshot = dataSnapshotTask.getResult();
            Log.d("TAG", "onDataChange: dataSnapshot key: " + dataSnapshot.getKey());
            Log.d("TAG", "onDataChange: dataSnapshot exists: " + dataSnapshot.exists());
            Account loggedInAccount = null;
            Log.d("MyApp", "Checking the DB");
            // Loop through all the users in the database
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                // Check the values of the children
                String passwordFromDatabase = userSnapshot.child("password").getValue(String.class);
                String emailFromDatabase = userSnapshot.child("email").getValue(String.class);
                if (!emailFromDatabase.equals((email))) {
                    continue;
                }
                if (!passwordFromDatabase.equals(password)) {
                    continue;
                }
                // The email and password combo exists in the database
                String userId = userSnapshot.getKey();
                String userName = userSnapshot.child("name").getValue(String.class);
                String userEmail = userSnapshot.child("email").getValue(String.class);
                loggedInAccount = new Account(userId, userName, userEmail);
                break;
            }
            if (loggedInAccount != null) {
                Log.d("MyApp", "Found user");
                return new Result.Success<>(loggedInAccount);
            } else {
                Log.d("MyApp", "Failed to find user");
                return new Result.Error(new Exception("Invalid email or password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(e);
        }
    }


    public void logout() {
        // TODO: revoke authentication
    }
}