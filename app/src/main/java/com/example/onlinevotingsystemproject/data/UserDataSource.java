package com.example.onlinevotingsystemproject.data;

import android.util.Log;

import com.example.onlinevotingsystemproject.data.model.Account;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.Tasks;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class UserDataSource {

    DatabaseReference accountsRef = FirebaseDatabase.getInstance().getReference("accounts");

    public Result<Account> login(String email, String password) {
        try {
            Log.d("MyApp", "Attempting to check the DB");
            Boolean user_exists = false;
            String emailFromDatabase = null;
            Task<DataSnapshot> dataSnapshotTask = accountsRef.get();
            Tasks.await(dataSnapshotTask);
            DataSnapshot dataSnapshot = dataSnapshotTask.getResult();
            Account loggedInAccount = null;
            Log.d("MyApp", "Checking the DB");
            // Loop through all the users in the database
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                // Check the values of the children
                String passwordFromDatabase = userSnapshot.child("password").getValue(String.class);
                emailFromDatabase = userSnapshot.child("email").getValue(String.class);
                if (!emailFromDatabase.equals((email))) {
                    continue;
                }
                user_exists = true;
                if (!passwordFromDatabase.equals(password)) {
                    continue;
                }
                // The email and password combo exists in the database
                String userId = userSnapshot.getKey();
                String userName = userSnapshot.child("name").getValue(String.class);
                String userEmail = userSnapshot.child("email").getValue(String.class);
                String userPhone = userSnapshot.child("phone").getValue(String.class);
                Boolean userType = userSnapshot.child("manager").getValue(Boolean.class);
                loggedInAccount = new Account(userId, userName, userEmail, userPhone, userType);
                break;
            }
            if (loggedInAccount != null) {
                Log.d("MyApp", "Found user");
                return new Result.Success<>(loggedInAccount);
            }
            else if (!user_exists){
                Log.d("MyApp", "User to be created");
                return new Result.Create<String>(email);
            } else {
                Log.d("MyApp", "Failed to find user");
                return new Result.Error(new Exception("Invalid email or password"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(e);
        }
    }

    public Result<Account> createAccount(String name, String email, String phone, String password, String repeat, Boolean type) {
        try {
            // Check if account with same email already exists
            Boolean user_exists = false;
            Task<DataSnapshot> dataSnapshotTask = accountsRef.get();
            Tasks.await(dataSnapshotTask);
            DataSnapshot dataSnapshot = dataSnapshotTask.getResult();
            for (DataSnapshot userSnapshot : dataSnapshot.getChildren()) {
                String emailFromDatabase = userSnapshot.child("email").getValue(String.class);
                if (emailFromDatabase.equals(email)) {
                    user_exists = true;
                    break;
                }
            }
            if (user_exists) {
                Log.d("MyApp", "Account already exists");
                return new Result.Error(new Exception("Account already exists"));
            } else {
                // Create a new account entry in the database
                String userId = UUID.randomUUID().toString();
                accountsRef.child(userId).child("name").setValue(name);
                accountsRef.child(userId).child("email").setValue(email);
                accountsRef.child(userId).child("phone").setValue(phone);
                accountsRef.child(userId).child("password").setValue(password);
                accountsRef.child(userId).child("manager").setValue(type);
                Account newAccount = new Account(userId, name, email, phone, type);
                Log.d("MyApp", "New account created");
                return new Result.Success<>(newAccount);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new Result.Error(e);
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }

    public Result<Account> updateName(String name, String userId) {
        Log.d("MyApp", "Update name: " + userId + name);
        accountsRef.child(userId).child("name").setValue(name);
        Account newAccount = new Account();
        newAccount.setUserId(userId);
        newAccount.setDisplayName(name);
        return new Result.Success<>(newAccount);
    }

    public Result<Account> updatePhone(String phone, String userId) {
        Log.d("MyApp", "Update name: " + userId + phone);
        accountsRef.child(userId).child("phone").setValue(phone);
        Account newAccount = new Account();
        newAccount.setUserId(userId);
        newAccount.setPhone(phone);
        return new Result.Success<>(newAccount);
    }

    public Result<Account> updatePassword(String password, String userId) {
        Log.d("MyApp", "Update name: " + userId + password);
        accountsRef.child(userId).child("password").setValue(password);
        Account newAccount = new Account();
        newAccount.setUserId(userId);
        return new Result.Success<>(newAccount);
    }
}