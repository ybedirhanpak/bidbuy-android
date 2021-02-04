package com.yabepa.bidbuy.network;

import android.os.Handler;
import android.os.Looper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.yabepa.bidbuy.common.Callback;
import com.yabepa.bidbuy.common.Util;
import com.yabepa.bidbuy.dto.Message;


public class Client {

    private static final String SERVER_IP = "192.168.1.67";
    private static final int SERVER_PORT = 9000;

    private static final Gson gson = new Gson();

    private static final HashMap<String, Boolean> subscriptions = new HashMap<>();

    private static <T> Type getResponseType(Class<T> className, boolean isList) {
        if (isList) {
            Type listOfT = TypeToken.getParameterized(List.class, className).getType();
            return TypeToken.getParameterized(Response.class, listOfT).getType();
        }

        return TypeToken.getParameterized(Response.class, className).getType();
    }

    private static <T> void handleResponseJson(
            String responseJson,
            Class<?> responseClass,
            boolean isList,
            Callback.Success<T> success,
            Callback.Error<Message> error
    ) {
        LinkedTreeMap<?, ?> responseTree = gson.fromJson(responseJson, LinkedTreeMap.class);
        int statusCode = (int) ((double) ((Double) responseTree.get("statusCode")));
        if (statusCode <= 200) {
            // Execute callback on main thread
            Type responseType = getResponseType(responseClass, isList);
            Response<T> response = gson.fromJson(responseJson, responseType);
            new Handler(Looper.getMainLooper()).post(() -> success.onSuccess(response.body));
        } else {
            Response<Message> response = gson.fromJson(responseJson, getResponseType(Message.class, false));
            new Handler(Looper.getMainLooper()).post(() -> error.onError(response.body));
        }
    }

    public static <T> void sendRequest(
            String identifier,
            Object body,
            Class<?> responseClass,
            boolean isList,
            Callback.Success<T> success,
            Callback.Error<Message> error
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
                handleResponseJson(responseJson, responseClass, isList, success, error);
                clientSocket.close();
            } catch (IOException e) {
                // Execute callback on main thread
                new Handler(Looper.getMainLooper()).post(() -> error.onError(new Message("Error occurred while sending request")));
                e.printStackTrace();
            }
        }).start();
    }

    public static <T> void sendContinuousRequest(
            String identifier,
            Object body,
            Class<?> responseClass,
            boolean isList,
            Callback.Success<T> success,
            Callback.Error<Message> error
    ) {
        Request request = new Request(identifier, body, true);
        new Thread(() -> {
            try {
                subscriptions.put(identifier, true);
                Socket clientSocket = new Socket(SERVER_IP, SERVER_PORT);

                // Send request
                String requestJson = gson.toJson(request);
                Util.writeJsonToOutputStream(requestJson, clientSocket.getOutputStream());

                // Receive response
                BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

                // Read as long as you want to get response from server
                while (subscriptions.get(identifier) != null && subscriptions.get(identifier)) {
                    String responseJson = reader.readLine();
                    if (responseJson != null) {
                        handleResponseJson(responseJson, responseClass, isList, success, error);
                    } else {
                        stopContinuousRequest(identifier);
                        new Handler(Looper.getMainLooper()).post(() -> error.onError(new Message("Connection is stopped by server")));
                    }
                }
                clientSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public static void stopContinuousRequest(String identifier) {
        subscriptions.remove(identifier);
    }
}
