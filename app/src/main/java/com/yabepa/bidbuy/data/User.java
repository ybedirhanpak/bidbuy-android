package com.yabepa.bidbuy.data;

public class User {
    public int id;
    public String username;
    public int passwordHash;

    public User() {
    }

    public User(int id, String username, int passwordHash) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
    }
}