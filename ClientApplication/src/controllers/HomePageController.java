/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTOs.LoginDTO;
import DTOs.MyWishlistDTO;
import client.Client;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIcon;
import de.jensd.fx.glyphs.materialdesignicons.MaterialDesignIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
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
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ahmed Samy
 */
public class HomePageController implements Initializable {

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
    private TableView<MyWishlistDTO> Table;
    @FXML
    private TableColumn<MyWishlistDTO, String> Id;
    @FXML
    private TableColumn<MyWishlistDTO, String> Name;
    @FXML
    private TableColumn<MyWishlistDTO, String> Desc;
    @FXML
    private TableColumn<MyWishlistDTO, String > Price;
    @FXML
    private TableColumn<MyWishlistDTO, String> Cat;
    @FXML
     private TableColumn<MyWishlistDTO, String> CA;
    
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TableColumn<MyWishlistDTO, Void> removeCol;
    @FXML
    private Pane mainPane;
    
    ObservableList<MyWishlistDTO> list;
    @FXML
    private Text hiUserTxt;
    @FXML
    private Text currentBalanceTxt;
    @FXML
    private Text notifyCountTxt;

    
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        BufferedReader br = SocketHolder.getInstance().getBufferedReader();
        PrintStream ps = SocketHolder.getInstance().getPrintStream();
        hiUserTxt.setText("Hi, " + Client.username + "!");
        currentBalanceTxt.setText(String.valueOf(Client.balance));
        notifyCountTxt.setText(String.valueOf(Client.notifyCount));
        Client.balanceText = currentBalanceTxt;
        Client.notifyCountTxt = notifyCountTxt;
        MyWishlistDTO wishitems =new MyWishlistDTO("getMylist",Client.username);
        Gson gson = new Gson();
        String request = gson.toJson(wishitems, MyWishlistDTO.class);       
        ps.println(request);
        System.out.println("request sent");
       try {
            String mylist =br.readLine();
             System.out.println(mylist);
            ArrayList<MyWishlistDTO> myWishList = gson.fromJson(mylist, new TypeToken<ArrayList<MyWishlistDTO>>(){}.getType());
            list =FXCollections.observableArrayList(myWishList);
          
            Id.setCellValueFactory(new PropertyValueFactory<MyWishlistDTO, String>("itemid"));
            Name.setCellValueFactory(new PropertyValueFactory<MyWishlistDTO, String>("itemname"));
            Desc.setCellValueFactory(new PropertyValueFactory<MyWishlistDTO, String>("description")); 
            Cat.setCellValueFactory(new PropertyValueFactory<MyWishlistDTO, String>("category"));
            Price.setCellValueFactory(new PropertyValueFactory<MyWishlistDTO, String>("price"));
            CA.setCellValueFactory(new PropertyValueFactory<MyWishlistDTO, String>("collectedAmount"));
            Table.setItems(list);
        } catch (IOException ex) {
            Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
        }
       
       MyFriends.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent newContent = FXMLLoader.load(getClass().getResource("/views/MyFriends.fxml"));
                    mainPane.getChildren().setAll(newContent);
//                    Parent root = FXMLLoader.load(getClass().getResource("/views/MyFriends.fxml"));
//                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       Notifications.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent newContent = FXMLLoader.load(getClass().getResource("/views/Notification.fxml"));
                    mainPane.getChildren().setAll(newContent);
//                    Parent root = FXMLLoader.load(getClass().getResource("/views/Notification.fxml"));
//                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
       
       Market.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent newContent = FXMLLoader.load(getClass().getResource("/views/Market.fxml"));
                    mainPane.getChildren().setAll(newContent);
//                    Parent root = FXMLLoader.load(getClass().getResource("/views/Market.fxml"));
//                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
                } catch (IOException ex) {
                    Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
       AddFriend.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent newContent = FXMLLoader.load(getClass().getResource("/views/AddFriend.fxml"));
                    mainPane.getChildren().setAll(newContent);
                    //stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    //scene = new Scene(root);
                    //stage.setScene(scene);
                    //stage.show();

                } catch (IOException ex) {
                    Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       HomePage.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent newContent = FXMLLoader.load(getClass().getResource("/views/myWishList.fxml"));
                     mainPane.getChildren().setAll(newContent);
//                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
                    

                } catch (IOException ex) {
                    Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
       MyProfile.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent newContent = FXMLLoader.load(getClass().getResource("/views/MyProfile.fxml"));
                     mainPane.getChildren().setAll(newContent);
//                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
                    

                } catch (IOException ex) {
                    Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       
       Logout.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("/views/Login.fxml"));
                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                    

                } catch (IOException ex) {
                    Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
       Help.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    Parent newContent = FXMLLoader.load(getClass().getResource("/views/Help.fxml"));
                     mainPane.getChildren().setAll(newContent);
//                    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
//                    scene = new Scene(root);
//                    stage.setScene(scene);
//                    stage.show();
                    

                } catch (IOException ex) {
                    Logger.getLogger(HomePageController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

       
       
       
       
       removeCol.setCellFactory(param -> new TableCell<MyWishlistDTO, Void>() {
            final Button removeButton = new Button("Remove");
            

            {
                removeButton.setStyle(
                        "-fx-background-color: #f7bc50; " +
                        "-fx-text-fill: #35415A;" +
                        "-fx-font-weight: bold;"
                );
                
                removeButton.setCursor(javafx.scene.Cursor.HAND);

                MaterialIconView icon = new MaterialIconView(MaterialIcon.REMOVE_CIRCLE_OUTLINE);
                icon.setFill(javafx.scene.paint.Color.valueOf("#35415A"));

                removeButton.setGraphic(icon);

                removeButton.setOnAction(e -> {
                     int index = getIndex();
                    MyWishlistDTO selectedItem = getTableView().getItems().get(index);

                    // Add the selected item to the WishlistDTO
                    MyWishlistDTO item = new MyWishlistDTO("RemoveItem", Client.username, selectedItem.getItemid());
                    list.remove(selectedItem);
                    Table.refresh();
                    

                    // Send the WishlistDTO to the server
                    Gson gson = new Gson();
                    String wishlistJson = gson.toJson(item, MyWishlistDTO.class);
                    ps.println(wishlistJson);
                });
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(removeButton);
                }
            }
        });
       
       // Apply custom styles to the TableView
   
        Table.getColumns().forEach(column -> {
    column.setStyle(null);
});
        String cssFile = getClass().getResource("blueTable.css").toExternalForm();
        Table.getStylesheets().add(cssFile);

    // Apply custom styles to the TableColumns
//    Id.setStyle(
//            "-fx-text-fill: #35415A; "+
//            "-fx-font-weight: bold;"
//            +"-fx-background-color: #f7bc50;"
//            + "-fx-border-color: #f7bc50;"
//            +"-fx-alignment: CENTER;"
//    );
//    Name.setStyle(
//            "-fx-text-fill: #35415A; " +
//            "-fx-font-weight: bold;"
//            +"-fx-background-color: #f7bc50;"
//   + "-fx-border-color: #f7bc50;"
//            +"-fx-alignment: CENTER;"
//    );
//    Desc.setStyle(
//            "-fx-text-fill: #35415A; " 
//            +"-fx-font-weight: bold;"
//            +"-fx-background-color: #f7bc50;"
//   + "-fx-border-color: #f7bc50;"
//            +"-fx-alignment: CENTER;"
//    );
//    Cat.setStyle(
//            "-fx-text-fill: #35415A; " 
//            +"-fx-font-weight: bold;"
//            +"-fx-background-color: #f7bc50;"
//   + "-fx-border-color: #f7bc50;"
//            +"-fx-alignment: CENTER;"
//    );
//    Price.setStyle(
//            "-fx-text-fill: #35415A; "
//            +"-fx-font-weight: bold;"
//            +"-fx-background-color: #f7bc50;"
//   + "-fx-border-color: #f7bc50;"
//            +"-fx-alignment: CENTER;"
//    );
//    CA.setStyle(
//            "-fx-text-fill: #35415A; " 
//            +"-fx-font-weight: bold;"
//            +"-fx-background-color: #f7bc50;"
//   + "-fx-border-color: #f7bc50;"
//            +"-fx-alignment: CENTER;"
//    );
//
//    removeCol.setStyle(
//            "-fx-text-fill: #35415A; " 
//            +"-fx-font-weight: bold;"
//            +"-fx-background-color: #f7bc50;"
//            +"-fx-border-color: #f7bc50;"
//            +"-fx-alignment: CENTER;"
//    );
    
   
    MyProfile.setCursor(javafx.scene.Cursor.HAND);
    Help.setCursor(javafx.scene.Cursor.HAND); 
    Logout.setCursor(javafx.scene.Cursor.HAND);  
    HomePage.setCursor(javafx.scene.Cursor.HAND);
    AddFriend.setCursor(javafx.scene.Cursor.HAND);
    MyFriends.setCursor(javafx.scene.Cursor.HAND);
    Market.setCursor(javafx.scene.Cursor.HAND);
    Notifications.setCursor(javafx.scene.Cursor.HAND);
    
    
        
        
        

    }    
    
}
