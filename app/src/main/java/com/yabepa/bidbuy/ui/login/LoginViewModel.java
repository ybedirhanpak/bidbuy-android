package com.yabepa.bidbuy.ui.login;

import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.common.Callback;
import com.yabepa.bidbuy.data.User;
import com.yabepa.bidbuy.dto.Message;
import com.yabepa.bidbuy.dto.UserAuth;
import com.yabepa.bidbuy.network.Client;

public class LoginViewModel extends ViewModel {


    public void login(String username, String password, Callback.Success<User> success, Callback.Error<Message> error) {
        UserAuth userAuthBody = new UserAuth(username, password);
        Client.sendRequest("login", userAuthBody, User.class, false, success, error);
    }

}