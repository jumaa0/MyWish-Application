/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package server;

import DTOs.NotificationDTO;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author husse
 */
public class NotificationServer {
    public static String notiMessage = null;
    private ArrayList<ClientHandler> clients = new ArrayList<>();

    public void startServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(5006);
            System.out.println("I'm Created HERE");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New Notification client connected: " + clientSocket);
                ClientHandler clientHandler = new ClientHandler(clientSocket, this);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void notifyClients(String message) {
        for (ClientHandler client : clients) {
            client.sendMessage(message);
        }
    }
}

class ClientHandler implements Runnable {
    private Socket clientSocket;
    private NotificationServer server;
    private BufferedReader br;
    private PrintStream ps;

    public ClientHandler(Socket clientSocket, NotificationServer server) {
        this.clientSocket = clientSocket;
        this.server = server;

        try {
            br = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            ps = new PrintStream(clientSocket.getOutputStream(), true);
            System.out.println("I get streams");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
            try{
             String request;
                while ((request = br.readLine()) != null){
                    //Gson gson = new Gson();
                    //NotificationDTO notificationDTO = gson.fromJson(request, NotificationDTO.class);
                    
                    System.out.println("notify server got this request" + request);
                    server.notifyClients(request);
                
                }
            } catch (IOException ex) {
            Logger.getLogger(ClientHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
            
    }

    // Method to send a message to the client
    public void sendMessage(String message) {
        System.out.println("notify servre is sending this message: " + message);
        ps.println(message);
    }
}