package com.jfbueno.httpserver;

import java.io.IOException;
import java.net.Socket;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import com.jfbueno.httpserver.messages.*;

public class Server implements Runnable {

    private Socket connectionSocket;

    public Server(Socket connectionSocket) throws IOException {
        this.connectionSocket = connectionSocket;
    }

    @Override
    public void run() {
        try (BufferedReader inputReader = new BufferedReader(new InputStreamReader(connectionSocket.getInputStream()))) {
            String input = inputReader.readLine();
            Request request = new Request(input);

            Response response = request.createResponse()
                    .configureOutputStream(connectionSocket.getOutputStream())
                    .addHeader("Server", "Server boladasso vem tranquilo")
                    .addHeader("Date", String.valueOf(new Date()))
                    .build();

            response.send();
            inputReader.close();
        } catch (IOException ioex) {
            //TODO:
        }
    }
}
