/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTOs.MyWishlistDTO;
import DTOs.NotificationDTO;
import client.Client;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ahmed Samy
 */
public class NotificationController implements Initializable {

    @FXML
    private TableView<NotificationDTO> notificationsTable;
    @FXML
    private TableColumn<NotificationDTO, String> notClm;
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            Client.notifyCount = 0;
            Client.notifyCountTxt.setText(String.valueOf(Client.notifyCount));
            BufferedReader br = SocketHolder.getInstance().getBufferedReader();
            PrintStream ps = SocketHolder.getInstance().getPrintStream();
            NotificationDTO myNotification =new NotificationDTO("myNotifications",Client.username);
            Gson gson = new Gson();
            String request = gson.toJson(myNotification, NotificationDTO.class);
            ps.println(request);
            String myNotificationList1;
            
            myNotificationList1 = br.readLine();
            System.out.println(myNotificationList1);
            ArrayList<NotificationDTO> myNotificationList = gson.fromJson(myNotificationList1, new TypeToken<ArrayList<NotificationDTO>>(){}.getType());
            ObservableList<NotificationDTO> list =FXCollections.observableArrayList(myNotificationList);
            notClm.setCellValueFactory(new PropertyValueFactory<NotificationDTO, String>("message"));
            notificationsTable.setItems(list);
        } catch (IOException ex) {
            Logger.getLogger(NotificationController.class.getName()).log(Level.SEVERE, null, ex);
        }
       notificationsTable.getColumns().forEach(column -> {
        column.setStyle(null);
        });
        String cssFile = getClass().getResource("blueTable.css").toExternalForm();
        notificationsTable.getStylesheets().add(cssFile);
       
        
        
        
        
       
    }    
    
}
