package com.yabepa.bidbuy.network;

import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;


import bidbuy.network.Request;
import bidbuy.network.Response;

import com.yabepa.bidbuy.common.Callback;
import com.yabepa.bidbuy.data.Product;

public class Client {
    public void sendMessage(String message) {
        try {
            Socket clientSocket = new Socket("192.168.1.67", 9000);
            DataOutputStream toServer = new DataOutputStream(clientSocket.getOutputStream());
            toServer.writeBytes(message);
            clientSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendRequest(
            String identifier,
            Object body,
            Callback.Success<Response> success,
            Callback.Error<String> error
    ) {
        Request request = new Request(identifier, body);
        Thread requestThread = new Thread(() -> {
            try {
                Socket clientSocket = new Socket("192.168.1.67", 9000);
                ObjectOutputStream objectToServer = new ObjectOutputStream(clientSocket.getOutputStream());
                objectToServer.writeObject(request);
                ObjectInputStream responseFromServer = new ObjectInputStream(clientSocket.getInputStream());
                Response response = (Response) responseFromServer.readObject();
                success.onSuccess(response);
                clientSocket.close();
            } catch (IOException | ClassNotFoundException e) {
                error.onError("Error occurred while sending request");
                e.printStackTrace();
            }
        });
        requestThread.start();
    }
}
