package com.example.onlinevotingsystemproject.data;

import android.util.Log;

import com.example.onlinevotingsystemproject.data.model.Account;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class UserRepository {

    private static volatile UserRepository instance;

    private UserDataSource dataSource;

    private Account account = null;

    // private constructor : singleton access
    private UserRepository(UserDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static UserRepository getInstance(UserDataSource dataSource) {
        if (instance == null) {
            instance = new UserRepository(dataSource);
        }
        return instance;
    }

    public boolean isLoggedIn() {
        return account != null;
    }

    public void logout() {
        account = null;
        dataSource.logout();
    }

    private void setLoggedInUser(Account account) {
        this.account = account;
    }

    public Result<Account> login(String username, String password) {
        Result<Account> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<Account>) result).getData());
        }
        return result;
    }

    public Result<Account> createAccount(String name, String email, String phone, String password, String repeat, Boolean type) {
        Result<Account> result = dataSource.createAccount(name, email, phone, password, repeat, type);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<Account>) result).getData());
        }
        return result;
    }

    public Result<Account> updateName(String name, String userID) {
        Result<Account> result = dataSource.updateName(name, userID);
        return result;
    }

    public Result<Account> updatePhone(String phone, String userID) {
        Result<Account> result = dataSource.updatePhone(phone, userID);
        return result;
    }

    public Result<Account> updatePassword(String password, String userID) {
        Result<Account> result = dataSource.updatePassword(password, userID);
        return result;
    }
}