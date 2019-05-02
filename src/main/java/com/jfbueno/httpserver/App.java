package com.jfbueno.httpserver;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.jfbueno.httpserver.config.*;

public class App {
    public static void main(String[] args) throws IOException {
        try {
            loadConfiguration();
        }catch(FileNotFoundException fnf) {
            System.out.println("Could not find a application configuration file");
            return;
        }

        int port = AppConfig.getInstance().getConfig().getPort();
        port = port <= 0 ? 9001 : port;
        
        ServerSocket serverConnect = new ServerSocket(port);
        
        while (true) {
            try {
                Server server = new Server(serverConnect.accept());

                Thread thread = new Thread(server);
                thread.start();
            } catch (IOException ioex) {
                System.out.println("Erro de conexão no servidor");
                ioex.printStackTrace();
            }
        }
    }

    public static void loadConfiguration() throws FileNotFoundException {
        File configJson = new File("./target/classes/config.json");              
        JsonReader reader = new JsonReader(new FileReader(configJson));                            
        Configuration config = new Gson().fromJson(reader, Configuration.class);
        AppConfig.init(config);
    }
}
