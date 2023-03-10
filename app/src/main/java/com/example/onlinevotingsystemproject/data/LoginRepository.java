package com.example.onlinevotingsystemproject.data;

import com.example.onlinevotingsystemproject.data.model.Account;

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */
public class LoginRepository {

    private static volatile LoginRepository instance;

    private com.example.onlinevotingsystemproject.data.LoginDataSource dataSource;

    // If user credentials will be cached in local storage, it is recommended it be encrypted
    // @see https://developer.android.com/training/articles/keystore
    private Account account = null;

    // private constructor : singleton access
    private LoginRepository(com.example.onlinevotingsystemproject.data.LoginDataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static LoginRepository getInstance(com.example.onlinevotingsystemproject.data.LoginDataSource dataSource) {
        if (instance == null) {
            instance = new LoginRepository(dataSource);
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
        // If user credentials will be cached in local storage, it is recommended it be encrypted
        // @see https://developer.android.com/training/articles/keystore
    }

    public Result<Account> login(String username, String password) {
        // handle login
        Result<Account> result = dataSource.login(username, password);
        if (result instanceof Result.Success) {
            setLoggedInUser(((Result.Success<Account>) result).getData());
        }
        return result;
    }
}