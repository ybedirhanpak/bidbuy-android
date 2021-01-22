package com.yabepa.bidbuy.dto;

import androidx.annotation.NonNull;

public class Message {
    public String message;

    public Message(String message) {
        this.message = message;
    }

    @NonNull
    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}