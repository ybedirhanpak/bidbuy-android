package com.yabepa.bidbuy.ui.register;

import androidx.lifecycle.ViewModel;

import com.yabepa.bidbuy.common.Callback;
import com.yabepa.bidbuy.data.User;
import com.yabepa.bidbuy.dto.Message;
import com.yabepa.bidbuy.dto.UserAuth;
import com.yabepa.bidbuy.network.Client;

public class RegisterViewModel extends ViewModel {

    public void register(String username, String password, Callback.Success<User> success, Callback.Error<Message> error) {
        UserAuth userAuthBody = new UserAuth(username, password);
        Client.sendRequest("register", userAuthBody, User.class, false, success, error);
    }

}