package com.yabepa.bidbuy.network;

import android.os.Handler;
import android.os.Looper;

import java.io.IOException;
import java.net.Socket;

import com.google.gson.Gson;
import com.yabepa.bidbuy.common.Callback;
import com.yabepa.bidbuy.common.Util;


public class Client {

    private static final String SERVER_IP = "192.168.1.67";
    private static final int SERVER_PORT = 9000;

    private static final Gson gson = new Gson();

    public static void sendRequest(
            String identifier,
            Object body,
            Callback.Success<Object> success,
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
                Response response = gson.fromJson(responseJson, Response.class);

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
}
