package controllers;

import java.io.*;
import java.net.Socket;

public class SocketHolder {

    private static SocketHolder instance;

    private Socket socket;
    private PrintStream printStream;
    private BufferedReader bufferedReader;
    private Socket notificationSocket;
    private PrintStream NprintStream;
    private BufferedReader NbufferedReader;
    

    // Private constructor to prevent instantiation outside the class
    private SocketHolder() {
        try {
            // Connect to the server at 127.0.0.1:5005
            this.socket = new Socket("127.0.0.1", 5005);
            this.notificationSocket = new Socket ("127.0.0.1", 5006);

            // Create a PrintStream for writing to the server
            this.printStream = new PrintStream(socket.getOutputStream(), true);
            this.NprintStream = new PrintStream(notificationSocket.getOutputStream(), true);

            // Create a BufferedReader for reading from the server
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.NbufferedReader = new BufferedReader(new InputStreamReader(notificationSocket.getInputStream()));
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }

    // Method to get the instance of SocketHolder
    public static synchronized SocketHolder getInstance() {
        if (instance == null) {
            instance = new SocketHolder();
        }
        return instance;
    }

    // Getter for the PrintStream
    public PrintStream getPrintStream() {
        return printStream;
    }
    public PrintStream getNPrintStream() {
        return NprintStream;
    }

    // Getter for the BufferedReader
    public BufferedReader getBufferedReader() {
        return bufferedReader;
    }
    public BufferedReader getNBufferedReader() {
        return NbufferedReader;
    }

    // Close the socket connection
    public void closeSocket() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
                notificationSocket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
            // Handle the exception as needed
        }
    }
}
