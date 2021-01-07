package com.yabepa.bidbuy.network;

public class Response<T> {
    T body;

    public Response(T body) {
        this.body = body;
    }
}
