package com.yabepa.bidbuy.network;

public class Request {
    String identifier;
    Object body;

    public Request(String identifier, Object body) {
        this.identifier = identifier;
        this.body = body;
    }
}
