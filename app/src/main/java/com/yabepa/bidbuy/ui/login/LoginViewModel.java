package com.yabepa.bidbuy.ui.login;

import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.User;
import com.yabepa.bidbuy.dto.UserAuth;
import com.yabepa.bidbuy.network.Client;

public class LoginViewModel extends ViewModel {

    public void login(String username, String password) {
        UserAuth userAuthBody = new UserAuth(username, password);
        Client.<User>sendRequest("login", userAuthBody, User.class, false,
                System.out::println,
                System.out::println);
    }

}