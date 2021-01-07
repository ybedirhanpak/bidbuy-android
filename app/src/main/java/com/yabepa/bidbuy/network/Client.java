package com.yabepa.bidbuy.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.yabepa.bidbuy.common.Callback;
import com.yabepa.bidbuy.common.Util;
import com.yabepa.bidbuy.data.Product;


public class Client {

    private static final String SERVER_IP = "192.168.1.67";
    private static final int SERVER_PORT = 9000;

    private static final Gson gson = new Gson();

    public static <T> void sendRequest(
            String identifier,
            Object body,
            Class<?> responseClass,
            boolean isList,
            Callback.Success<T> success,
            Callback.Error<String> error
    ) {
        Request request = new Request(identifier, body);
        new Thread(() -> {
            try {
                Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);

                // Send request
                String requestJson = gson.toJson(request);
                Util.writeJsonToOutputStream(requestJson, clientSocket.getOutputStream());

                // Receive response
                String responseJson = Util.inputStreamToJson(clientSocket.getInputStream());
                Type responseType = getResponseType(responseClass, isList);
                Response<T> response = gson.fromJson(responseJson, responseType);

                // Execute callback on main thread
                new Handler(Looper.getMainLooper()).post(() -> success.onSuccess(response.body));

                clientSocket.close();
            } catch (IOException e) {
                // Execute callback on main thread
                new Handler(Looper.getMainLooper()).post(() -> error.onError("Error occurred while sending request"));
                e.printStackTrace();
            }
        }).start();
    }

    private static <T> Type getResponseType(Class<T> className, boolean isList) {
        if(isList) {
            Type listOfT = TypeToken.getParameterized(List.class, className).getType();
            return TypeToken.getParameterized(Response.class, listOfT).getType();
        }

        return TypeToken.getParameterized(Response.class, className).getType();
    }
}
