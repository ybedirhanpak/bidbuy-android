package com.yabepa.bidbuy.common;

public interface Callback {
    interface Success<T> {
        void onSuccess(T payload);
    }

    interface Error<T> {
        void onError(T error);
    }
}
