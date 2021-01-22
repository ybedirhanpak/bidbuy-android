package com.yabepa.bidbuy.network;

public class Response<T> {
    int statusCode;
    T body;

    public Response(T body) {
        this.body = body;
    }
}
