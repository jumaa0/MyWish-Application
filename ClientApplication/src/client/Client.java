/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package client;

import controllers.SocketHolder;
import controllers.NotificationListener;
import DTOs.NotificationDTO;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;


/**
 *
 * @author husse
 */
public class Client extends Application {
    static NotificationListener  listener;
    public static String username;
    public static int balance;
    public static int notifyCount;
    public static Text balanceText;
    public static Text notifyCountTxt;
    @Override
    public void start(Stage stage) throws Exception {
        SocketHolder.getInstance();
        Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
        Platform.setImplicitExit(false);
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent event) {
                listener.stop();
                SocketHolder.getInstance().closeSocket();
                Platform.exit();
                System.exit(0);
            }
        });
        
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
    
            BufferedReader nBufferedReader = SocketHolder.getInstance().getNBufferedReader();
            listener = new NotificationListener(nBufferedReader);
            listener.start();
        launch(args);
        
        
    }
    
}
