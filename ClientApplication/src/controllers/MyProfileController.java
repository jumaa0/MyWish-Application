/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTOs.ItemListDTO;
import DTOs.MyProfileDTO;
import client.Client;
import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

/**
 * FXML Controller class
 *
 * @author JUMAA
 */
public class MyProfileController implements Initializable {

    @FXML
    private Button MyProfile;
    @FXML
    private Button Help;
    @FXML
    private Button Logout;
    @FXML
    private Button HomePage;
    @FXML
    private Button AddFriend;
    @FXML
    private Button MyFriends;
    @FXML
    private Button Market;
    @FXML
    private Button Notifications;
    @FXML
    private TextField txtName;
    @FXML
    private TextField txtUsername;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtPhone;
    @FXML
    private TextField txtCredit;
    @FXML
    private TextField txtBalance;
    @FXML
    private TextField txtWishList;
    @FXML
    private TextField txtFriends;
    @FXML
    private TextField txtPassword;
    
    
    BufferedReader br;
    PrintStream ps;
    @FXML
    private Button BtnEdit;
    @FXML
    private Button BtnEditPh;
    @FXML
    private Button BtnEditEmail;
    
    
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        br = SocketHolder.getInstance().getBufferedReader();
        ps = SocketHolder.getInstance().getPrintStream();
        
        loadFromServer();
        
        BtnEdit.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                MyProfileDTO myprofileDTO = new MyProfileDTO("EditPassword", Client.username , txtPassword.getText());
                Gson gson = new Gson();
                String request = gson.toJson(myprofileDTO, MyProfileDTO.class);
                ps.println(request);
            }
        });
         BtnEditPh.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                MyProfileDTO myprofileDTO = new MyProfileDTO("EditPhone", Client.username );
                myprofileDTO.setPhone(txtPhone.getText());
                Gson gson = new Gson();
                String request = gson.toJson(myprofileDTO, MyProfileDTO.class);
                ps.println(request);
            }
        });
          BtnEditEmail.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>(){
            @Override
            public void handle(ActionEvent event) {
                MyProfileDTO myprofileDTO = new MyProfileDTO("EditEmail", Client.username );
                myprofileDTO.setEmail(txtEmail.getText());
                Gson gson = new Gson();
                String request = gson.toJson(myprofileDTO, MyProfileDTO.class);
                ps.println(request);
            }
        });
    }    
    private void loadFromServer() {
            MyProfileDTO itemlist = new MyProfileDTO("Myprofile", Client.username);
            Gson gson = new Gson();
            String request = gson.toJson(itemlist, MyProfileDTO.class);
            ps.println(request);
            
            
        try {
            String result = br.readLine();
            MyProfileDTO myProfile = gson.fromJson(result, MyProfileDTO.class);
            updateUI(myProfile);
        } catch (IOException ex) {
            Logger.getLogger(MyProfileController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        }
    private void updateUI(MyProfileDTO myProfile) {
    // Update the UI elements with the profile information
    txtName.setText(myProfile.getName());
    txtUsername.setText(myProfile.getUsername());
    txtEmail.setText(myProfile.getEmail());
    txtPhone.setText(myProfile.getPhone());
    txtCredit.setText(myProfile.getCredit());
    txtBalance.setText(String.valueOf(myProfile.getBalance()));
    txtWishList.setText(String.valueOf(myProfile.getWishlist()));
    txtFriends.setText(String.valueOf(myProfile.getFriends()));
    //txtPassword.setText(myProfile.getPassword());
    // ... (update other UI elements as needed)
}
}
