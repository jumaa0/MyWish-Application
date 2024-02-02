/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTOs.NotificationDTO;
import client.Client;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

/**
 *
 * @author husse
 */
public class NotificationListener extends Thread {
    private BufferedReader bufferedReader;

    public NotificationListener(BufferedReader bufferedReader) {
        this.bufferedReader = bufferedReader;
    }

    @Override
    public void run() {
        try {
            while (true) {
                String input = bufferedReader.readLine();
                System.out.println(input);
                if (input == null) {
                    // Handle the case when the input stream is closed or the connection is lost
                    break;
                }
                Gson gson = new Gson();
                NotificationDTO dTO = gson.fromJson(input, NotificationDTO.class);
                
               
                Platform.runLater(() -> {
                // This code is now running on the JavaFX Application Thread
                new CustomNotification(dTO);
                });

                //Platform.runLater(() -> new CustomNotification(dTO.getMessage()));
                
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
 private void showAlert(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Message Received");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
