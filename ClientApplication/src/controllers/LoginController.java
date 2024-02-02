/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTOs.LoginDTO;
import client.Client;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author husse
 */
public class LoginController implements Initializable {

    @FXML
    private TextField username;
    @FXML
    private Hyperlink registerLink;
    @FXML
    private PasswordField psswd;
    @FXML
    private Button loginBtn;
    
    @FXML
    private Label msgLbl;
    
    private Stage stage;
    private Scene scene;
    private Parent root;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        

        BufferedReader br = SocketHolder.getInstance().getBufferedReader();
        PrintStream ps = SocketHolder.getInstance().getPrintStream();
        
        registerLink.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Register.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        loginBtn.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (username.getText().isEmpty() || psswd.getText().isEmpty()){
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Incomplete info");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please complete your info");
                    alert.show();
                }
                else{
                LoginDTO loginDTO = new LoginDTO("LoginRequest", username.getText(), 
                psswd.getText());
                Gson gson = new Gson();
                String request = gson.toJson(loginDTO, LoginDTO.class);       
                ps.println(request);
                System.out.println("still working in button handling");
                try {
                    String response = br.readLine();
                    loginDTO = gson.fromJson(response, LoginDTO.class);
                    if ("Success".equals(loginDTO.getStatus())){
                    Client.username = loginDTO.getUsername();
                    Client.balance = loginDTO.getBalance();
                    Client.notifyCount = loginDTO.getNotiCount();
                    System.out.println("this is " + Client.username + "'s Balance: " + Client.balance);
                    System.out.println("this is " + Client.username + "'s noti count: " + Client.notifyCount);
//                    Alert alert = new Alert(AlertType.INFORMATION);
//                    alert.setTitle("Login Result");
//                    alert.setHeaderText("Login successful");
//                    alert.setContentText("Welcome!");
//                    alert.show();
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.initStyle(StageStyle.TRANSPARENT);
                    alert.setTitle(null);
                    alert.setHeaderText(null);
                    alert.setGraphic(null);
                    Label label = new Label("Login successful, Welcome!");
                    label.setStyle("-fx-font-weight: bold; -fx-text-fill: #f7bc50;");
                    DialogPane dialogPane = alert.getDialogPane();
                    dialogPane.setContent(label);
                    dialogPane.setStyle("-fx-background-color: #35415A;");
                    Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
                    okButton.setCursor(javafx.scene.Cursor.HAND);
                    okButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #f7bc50; -fx-font-weight: bold;");
                    alert.show();
                    //switch to home page
                    root = FXMLLoader.load(getClass().getResource("/views/HomePage.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    }
                    else {msgLbl.setText("Incorrect login info");}
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
                } 
            }
        });
    }    
    
}
