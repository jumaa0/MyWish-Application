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
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Ahmed Samy
 */
public class MyWishListController implements Initializable {

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
   
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        BufferedReader br = SocketHolder.getInstance().getBufferedReader();
        PrintStream ps = SocketHolder.getInstance().getPrintStream();
        
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
       
        Table.getColumns().forEach(column -> {
        column.setStyle(null);
        });
        String cssFile = getClass().getResource("blueTable.css").toExternalForm();
        Table.getStylesheets().add(cssFile);
    
   
    
    
        
        
        

    }    
    
}
