package com.yabepa.bidbuy.ui.register;

import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.User;
import com.yabepa.bidbuy.dto.UserAuth;
import com.yabepa.bidbuy.network.Client;

public class RegisterViewModel extends ViewModel {

    public void register(String username, String password) {
        UserAuth userAuthBody = new UserAuth(username, password);
        Client.<User>sendRequest("register", userAuthBody, User.class, false,
                System.out::println,
                System.out::println);
    }

}