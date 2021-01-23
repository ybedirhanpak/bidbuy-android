package com.yabepa.bidbuy.ui.login;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.data.User;
import com.yabepa.bidbuy.dto.UserAuth;
import com.yabepa.bidbuy.network.Client;

public class LoginViewModel extends ViewModel {

    private MutableLiveData<User> user;

    public MutableLiveData<User> getUser() {
        if (user == null) {
            user = new MutableLiveData<>(null);
        }
        return user;
    }

    public void login(String username, String password) {
        UserAuth userAuthBody = new UserAuth(username, password);
        Client.<User>sendRequest("login", userAuthBody, User.class, false,
                user -> getUser().setValue(user),
                System.out::println);
    }

}