/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTOs.ContributeDTO;
import DTOs.FriendDTO;
import DTOs.ItemDTO;
import DTOs.FriendListDTO;
import DTOs.RemoveFriendDTO;
import client.Client;
import com.google.gson.Gson;
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
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 * FXML Controller class
 *
 * @author husse
 */
public class MyFriendsController implements Initializable {

    
    @FXML
    private TableView<FriendDTO> friendsTable;
    @FXML
    private TableColumn<FriendDTO, String> usernameCol;
    @FXML
    private TableView<ItemDTO> wishlistTable;
    @FXML
    private TableColumn<ItemDTO, String> idClm;
    @FXML
    private TableColumn<ItemDTO, String> nameClm;
    @FXML
    private TableColumn<ItemDTO, String> catClm;
    private TableColumn<ItemDTO, String> descClm;
    @FXML
    private TableColumn<ItemDTO, Integer> priceClm;
    @FXML
    private TableColumn<ItemDTO, Integer> collectClm;
    @FXML
    private TableColumn<ItemDTO, Void> contributeClm;
    @FXML
    private TableColumn<FriendDTO, Void> RemoveFriend;
    @FXML
    private Label frndsLbl;
    
    
    private void switchScene(String fxmlFile, ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxmlFile));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            // Handle exception, e.g., print error message or log
            e.printStackTrace();
        }
    }

    /**
     * Initializes the controller class.
     */
   
    BufferedReader br = SocketHolder.getInstance().getBufferedReader();
    PrintStream ps = SocketHolder.getInstance().getPrintStream();
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        
        usernameCol.setCellValueFactory(new PropertyValueFactory<FriendDTO, String>("name"));
        idClm.setCellValueFactory(new PropertyValueFactory<ItemDTO, String>("itemId"));
        //descClm.setCellValueFactory(new PropertyValueFactory<ItemDTO, String>("description"));
        priceClm.setCellValueFactory(new PropertyValueFactory<ItemDTO, Integer>("price"));
        collectClm.setCellValueFactory(new PropertyValueFactory<ItemDTO, Integer>("collectedAmount"));
        nameClm.setCellValueFactory(new PropertyValueFactory<ItemDTO, String>("itemName"));
        catClm.setCellValueFactory(new PropertyValueFactory<ItemDTO, String>("category"));
        
                usernameCol.setCellFactory(column -> {
            return new javafx.scene.control.TableCell<FriendDTO, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (item == null || empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setCursor(javafx.scene.Cursor.HAND);
                    }
                }
            };
        });

        
        
        RemoveFriend.setCellFactory(param -> new TableCell<FriendDTO, Void>() {
            final Button removeButton = new Button("Remove");
            
            {
            removeButton.setStyle(
                        "-fx-background-color: #f7bc50; " +
                        "-fx-text-fill: #35415A;"
                );
                
                removeButton.setCursor(javafx.scene.Cursor.HAND);

                MaterialIconView icon = new MaterialIconView(MaterialIcon.REMOVE_CIRCLE_OUTLINE);
                icon.setFill(javafx.scene.paint.Color.valueOf("#35415A"));

                removeButton.setGraphic(icon);
            
            }

            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);

                if (empty) {
                    setGraphic(null);
                    setText(null);
                } else {
                    setGraphic(removeButton);
                    setText(null);
                    // Handle button click event if needed
                  removeButton.setOnAction(event -> handleRemoveButtonClick(getTableView().getItems().get(getIndex())));

                }
            }
        });
        
        FriendListDTO fListDTO = new FriendListDTO("FriendListRequest", Client.username);
        Gson gson = new Gson();
        String request = gson.toJson(fListDTO, FriendListDTO.class);
        ps.println(request);
        String result;
        try {
            result = br.readLine();
            fListDTO = gson.fromJson(result, FriendListDTO.class);
            ObservableList<FriendDTO> friendList = 
                    FXCollections.observableArrayList(fListDTO.getFriends());
            
        friendsTable.setItems(friendList);
        } catch (IOException ex) {
            Logger.getLogger(MyFriendsController.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        friendsTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
    if (newValue != null && newValue.getWishlist() != null) {
        ObservableList<ItemDTO> wishlist = FXCollections.observableArrayList(newValue.getWishlist());
        wishlistTable.setItems(wishlist);
    }
    });  
        
      
    Label customPlaceholder = new Label("Select a friend to display their wishlist.");
    wishlistTable.setPlaceholder(customPlaceholder);
      
    contributeClm.setCellFactory((TableColumn<ItemDTO, Void> param) -> 
            new TableCell<ItemDTO, Void>() {
    final Button contributeButton = new Button("Contribute");

    {
        contributeButton.setStyle(
                        "-fx-background-color: #35415A; " +
                        "-fx-text-fill: #EFF6EE;"
                );
                
                contributeButton.setCursor(javafx.scene.Cursor.HAND);

                MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.TAG_HEART);
                icon.setFill(javafx.scene.paint.Color.valueOf("#f7bc50"));
                contributeButton.setGraphic(icon);
        String contributorName = Client.username;
        if (friendsTable.getSelectionModel().getSelectedItem() != null){
        String wishingName = friendsTable.getSelectionModel().getSelectedItem().getName();
        contributeButton.setOnAction(event -> showContributeDialog(contributorName, wishingName, getIndex()));}
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(contributeButton);
        }
    }
    });
    
//    friendsTable.setStyle(
//            "-fx-background-color: #35415A; " +
//            "-fx-border-color: #35415A;"+
//            "-fx-font-weight: bold;"+
//            "-fx-border-width: 5px;"+
//            "-fx-border-radius: 1px;"
//    );

    friendsTable.getColumns().forEach(column -> {
        column.setStyle(null);
        });
        String cssFile = getClass().getResource("blueTable.css").toExternalForm();
        friendsTable.getStylesheets().add(cssFile);
        
    wishlistTable.setStyle(
            "-fx-background-color: #f7bc50; " +
            "-fx-border-color: #f7bc50;"+
            "-fx-font-weight: bold;"+
            "-fx-border-width: 5px;"+
            "-fx-border-radius: 1px;"
    );
    // Apply custom styles to the TableColumns
    
    
//    usernameCol.setStyle(
//            "-fx-text-fill: #f7bc50; "+
//            "-fx-font-weight: bold;"
//            +"-fx-background-color: #35415A;"
//            + "-fx-border-color: #35415A;"
//            +"-fx-alignment: CENTER;"
//    );
    
   
    
     idClm.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
    
     nameClm.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
    
     catClm.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
//     descClm.setStyle(
//            "-fx-text-fill: #35415A; "+
//            "-fx-font-weight: bold;"
//            +"-fx-background-color: #f7bc50;"
//            + "-fx-border-color: #f7bc50;"
//            +"-fx-alignment: CENTER;"
//    );
    
     priceClm.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
    
     collectClm.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
    
     contributeClm.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
    
//     RemoveFriend.setStyle(
//            "-fx-text-fill: #f7bc50; "+
//            "-fx-font-weight: bold;"
//            +"-fx-background-color: #35415A;"
//            + "-fx-border-color: #35415A;"
//            +"-fx-alignment: CENTER;"
//    );
     
    
        
    } 
    
//    private void showContributeDialog(String contributor,String wishingUser, int index) {
//        Stage contributeStage = new Stage();
//        contributeStage.initModality(Modality.APPLICATION_MODAL);
//        contributeStage.setTitle("Contribute");
//
//        Label amountLabel = new Label("Enter Contribution:");
//        TextField amountField = new TextField();
//
//        Button confirmButton = new Button("Confirm");
//        confirmButton.setOnAction(event -> {
//            // Get the selected item and its details
//            ItemDTO selectedItem = wishlistTable.getItems().get(index);
//            int price = selectedItem.getPrice();
//            int collectedAmount = selectedItem.getCollectedAmount();
//            String itemName = selectedItem.getItemName();
//
//            // Check if the entered amount is valid
//            int enteredAmount = Integer.parseInt(amountField.getText());
//            int remainingAmount = price - collectedAmount;
//
//            if (enteredAmount > remainingAmount) {
//                // Display an alert for an invalid amount
//                Alert alert = new Alert(AlertType.WARNING);
//                alert.setTitle("Invalid Amount");
//                alert.setHeaderText(null);
//                alert.setContentText(" You are too generous ! please enter the remaining amount only ");
//                alert.showAndWait();
//            } else {
//
//                // Valid amount, proceed with contribution logic
//                String itemId = selectedItem.getItemId();
//                ContributeDTO contributeDTO = new ContributeDTO("contributeRequest", contributor, wishingUser, itemId, price, enteredAmount,collectedAmount,itemName);
//                Gson gson = new Gson();
//                String request = gson.toJson(contributeDTO, ContributeDTO.class);
//                ps.println(request);
//
//                // Update the local data
//                int originalAmount = selectedItem.getCollectedAmount();
//                selectedItem.setCollectedAmount(originalAmount + enteredAmount);
//                Client.balance -= enteredAmount;
//                Client.balanceText.setText(String.valueOf(Client.balance));
//                wishlistTable.refresh();
//
//                contributeStage.close();
//            }
//        });
//
//        Button cancelButton = new Button("Cancel");
//        cancelButton.setOnAction(event -> contributeStage.close());
//
//        GridPane gridPane = new GridPane();
//        gridPane.setHgap(10);
//        gridPane.setVgap(10);
//        gridPane.addRow(0, amountLabel, amountField);
//        gridPane.addRow(1, confirmButton, cancelButton);
//
//        Scene contributeScene = new Scene(gridPane, 300, 150);
//        contributeStage.setScene(contributeScene);
//        contributeStage.showAndWait();
//    }
    
  private void showContributeDialog(String contributor, String wishingUser, int index) {
        Stage contributeStage = new Stage();
        contributeStage.initModality(Modality.APPLICATION_MODAL);
        contributeStage.initStyle(StageStyle.TRANSPARENT); // Remove the top bar

        Label amountLabel = new Label("Enter Contribution:");
        TextField amountField = new TextField();

        Button confirmButton = new Button("Confirm");
        confirmButton.setCursor(Cursor.HAND);
        confirmButton.setStyle("-fx-background-color: #35415A; -fx-text-fill: #f7bc50; -fx-font-weight: bold;");
        confirmButton.setOnAction(event -> {
            // Get the selected item and its details
            ItemDTO selectedItem = wishlistTable.getItems().get(index);
            int price = selectedItem.getPrice();
            int collectedAmount = selectedItem.getCollectedAmount();
            String itemName = selectedItem.getItemName();

            // Check if the entered amount is valid
            int enteredAmount = Integer.parseInt(amountField.getText());
            int remainingAmount = price - collectedAmount;

           if(Client.balance<enteredAmount){ 
				Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initStyle(StageStyle.TRANSPARENT); // Remove the top bar
                alert.setTitle("Invalid Amount");
                alert.setHeaderText(null);

                // Customize the alert content
                Label contentLabel = new Label(" No cheating allowed! Please check your balance ");
                contentLabel.setStyle("-fx-text-fill: #f7bc50; -fx-font-weight: bold; ");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setContent(contentLabel);
                Button closeButton = (Button) dialogPane.lookupButton(ButtonType.OK);
                closeButton.setCursor(Cursor.HAND);
                closeButton.setStyle("-fx-background-color: #f7bc50; -fx-text-fill: #35415A; -fx-font-weight: bold;");
                closeButton.setOnAction(event2 -> alert.close());            
                // Add content and button to the DialogPane
                // Customize the alert background
                dialogPane.setStyle("-fx-background-color: #35415A;");
                dialogPane.setPadding(new Insets(10));
                alert.showAndWait();

                }
                else{
				
		if (enteredAmount > remainingAmount) {
               Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.initStyle(StageStyle.TRANSPARENT); // Remove the top bar
                alert.setTitle("Invalid Amount");
                alert.setHeaderText(null);

                // Customize the alert content
                Label contentLabel = new Label(" You are too generous! Please enter the remaining amount only.");
                contentLabel.setStyle("-fx-text-fill: #f7bc50; -fx-font-weight: bold; ");
                DialogPane dialogPane = alert.getDialogPane();
                dialogPane.setContent(contentLabel);
                Button closeButton = (Button) dialogPane.lookupButton(ButtonType.OK);
                closeButton.setCursor(Cursor.HAND);
                closeButton.setStyle("-fx-background-color: #f7bc50; -fx-text-fill: #35415A; -fx-font-weight: bold;");
                closeButton.setOnAction(event2 -> alert.close());            
                // Add content and button to the DialogPane
                // Customize the alert background
                dialogPane.setStyle("-fx-background-color: #35415A;");
                dialogPane.setPadding(new Insets(10));
                alert.showAndWait();
            }
			else{
			
				
				
                // Valid amount, proceed with contribution logic
                String itemId = selectedItem.getItemId();
                ContributeDTO contributeDTO = new ContributeDTO("contributeRequest", contributor, wishingUser, itemId, price, enteredAmount,collectedAmount,itemName);
                Gson gson = new Gson();
                String request = gson.toJson(contributeDTO, ContributeDTO.class);
                ps.println(request);

                // Update the local data
                int originalAmount = selectedItem.getCollectedAmount();
                selectedItem.setCollectedAmount(originalAmount + enteredAmount);
                Client.balance -= enteredAmount;
                Client.balanceText.setText(String.valueOf(Client.balance));
                wishlistTable.refresh();

                contributeStage.close();
                    
               }
                
                
                }
        });

        Button cancelButton = new Button("Cancel");
        cancelButton.setCursor(Cursor.HAND);
        cancelButton.setStyle("-fx-background-color: #35415A; -fx-text-fill: #f7bc50; -fx-font-weight: bold;");
        
        cancelButton.setOnAction(event -> contributeStage.close());

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setStyle("-fx-background-color: #f7bc50;");

        amountLabel.setStyle("-fx-text-fill: #35415A; -fx-font-weight: bold;");
        amountField.setStyle("-fx-text-fill: #f7bc50; -fx-font-weight: bold; -fx-background-color: #35415A;");

        gridPane.addRow(0, amountLabel, amountField);
        gridPane.addRow(1, confirmButton, cancelButton);

        Scene contributeScene = new Scene(gridPane, 300, 150, Color.TRANSPARENT);
        contributeStage.setScene(contributeScene);
        contributeStage.showAndWait();
    }  
    
    private void handleRemoveButtonClick(FriendDTO friend) {
    ObservableList<FriendDTO> friendList = friendsTable.getItems();

    // Notify the server about the removal
    Gson gson = new Gson();
    RemoveFriendDTO removeRequest = new RemoveFriendDTO("RemoveFriend", Client.username, friend.getName());
    String request = gson.toJson(removeRequest, RemoveFriendDTO.class);

    // Send the removal request to the server
    PrintStream ps = SocketHolder.getInstance().getPrintStream();
    ps.println(request);

    // Update the friend's wishlist locally
    friendList.remove(friend);
    
    wishlistTable.refresh();
    if(friendList.isEmpty())
         wishlistTable.getItems().clear();
}
    
}
