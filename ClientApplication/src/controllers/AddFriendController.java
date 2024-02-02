/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import DTOs.AcceptFriendDTO;
import DTOs.AddFriendDTO;
import DTOs.DeclineFriendDTO;
import DTOs.FriendDTO;
import DTOs.FriendRequestListDTO;
import DTOs.FriendRequestDTO;
import DTOs.NotificationDTO;
import DTOs.SearchFriendDTO;
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
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author dell
 */
public class AddFriendController implements Initializable {

    @FXML
    private TextField txtSearchBar;
    @FXML
    private Button SearchButton;
    @FXML
    private TableView<FriendDTO> txtFriends;
    @FXML
    private TableColumn<FriendDTO, String> UserNameCol;
    @FXML
    private TableColumn<FriendDTO, Void> AddColumn;
    @FXML
    private TableColumn<FriendRequestDTO, String> ReqCol;
    @FXML
    private TableColumn<FriendRequestDTO, Void> DeclineCol;
    @FXML
    private TableColumn<FriendRequestDTO, Void> AcceptCol;
    @FXML
    private Label txtRequests;
    @FXML
    private TableView<FriendRequestDTO> reqTable;
    ObservableList<FriendRequestDTO> obsReq;
    ObservableList<FriendDTO> obsAdd;
    PrintStream ps;
    BufferedReader br ;
    /**
     * Initializes the controller class.
     * 
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        ps = SocketHolder.getInstance().getPrintStream();
        br = SocketHolder.getInstance().getBufferedReader();
        ReqCol.setCellValueFactory(new PropertyValueFactory<>("sentFrom"));
        UserNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        FriendRequestListDTO requests = new FriendRequestListDTO();
        requests.setRequestType("FriendRequest");
        requests.setUserName(Client.username);
         Gson gson = new Gson();
         String request = gson.toJson(requests, FriendRequestListDTO.class);
         ps.println(request);
         
        try {
            String reader = br.readLine();
            System.out.println(reader);
            FriendRequestListDTO friend_requests= gson.fromJson(reader, FriendRequestListDTO.class);
            //System.out.println(friend_requests.getRequests().get(0).getSentFrom());
            obsReq = FXCollections.observableArrayList(friend_requests.getRequests());
            reqTable.setItems(obsReq);
            
        } catch (IOException ex) {
            Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
          
        }
        
        AcceptCol.setCellFactory((TableColumn<FriendRequestDTO, Void> param) -> 
        new TableCell<FriendRequestDTO, Void>() {
    final Button acceptButton = new Button("Accept");

    {
          acceptButton.setStyle(
                        "-fx-background-color: #35415A; " +
                        "-fx-text-fill: #EFF6EE;"
                );
                
                acceptButton.setCursor(javafx.scene.Cursor.HAND);

                MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT_PLUS);               icon.setFill(javafx.scene.paint.Color.valueOf("#f7bc50"));
                acceptButton.setGraphic(icon);
        acceptButton.setOnAction(event -> onAcceptButtonClicked(getIndex()));
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(acceptButton);
        }
    }
});
        DeclineCol.setCellFactory((TableColumn<FriendRequestDTO, Void> param) -> 
        new TableCell<FriendRequestDTO, Void>() {
    final Button DeclButton = new Button("Decline");

    {
         DeclButton.setStyle(
                        "-fx-background-color: #35415A; " +
                        "-fx-text-fill: #EFF6EE;"
                );
                
                DeclButton.setCursor(javafx.scene.Cursor.HAND);

                MaterialDesignIconView icon = new MaterialDesignIconView(MaterialDesignIcon.ACCOUNT_REMOVE);                icon.setFill(javafx.scene.paint.Color.valueOf("#f7bc50"));
                DeclButton.setGraphic(icon);
        DeclButton.setOnAction(event -> onDeclineButtonClicked(getIndex()));
    }

    @Override
    protected void updateItem(Void item, boolean empty) {
        super.updateItem(item, empty);
        if (empty) {
            setGraphic(null);
        } else {
            setGraphic(DeclButton);
        }
    }
});
    AddColumn.setCellFactory((TableColumn<FriendDTO, Void> param) -> 
          new TableCell<FriendDTO, Void>() {
     

      @Override
      protected void updateItem(Void item, boolean empty) {
          super.updateItem(item, empty);
          if (empty || getIndex() < 0 || getIndex() >= getTableView().getItems().size()) {
              setGraphic(null);
          } else {
               FriendDTO friend = getTableView().getItems().get(getIndex());
      final Button addButton = new Button(friend.isSentRequest() ? "Cancel Request" : "  Send Request ");

      {
           addButton.setStyle(
                        "-fx-background-color: #f7bc50; " +
                        "-fx-text-fill: #35415A;" +
                        "-fx-font-weight: bold;"
                );
                
                addButton.setCursor(javafx.scene.Cursor.HAND);

                MaterialDesignIconView icon = new MaterialDesignIconView(
                        friend.isSentRequest() ? MaterialDesignIcon.CLOSE_CIRCLE : MaterialDesignIcon.ACCOUNT_PLUS);
                icon.setFill(javafx.scene.paint.Color.valueOf("#35415A"));

                addButton.setGraphic(icon);
          addButton.setOnAction(event -> onAddButtonClicked(getIndex()));
      }
              setGraphic(addButton);
          }
      }
  });
    SearchButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
             String name = txtSearchBar.getText();
             SearchFriendDTO searchFriendDTO = new SearchFriendDTO();
             searchFriendDTO.setUserName(name);
             searchFriendDTO.setRequestType("SearchRequest");
             searchFriendDTO.setMyname(Client.username);
             Gson gson = new Gson();
             String request = gson.toJson(searchFriendDTO,SearchFriendDTO.class);
             ps.println(request);
                try {
                    String response = br.readLine();
                    System.out.println(response);
                    searchFriendDTO = gson.fromJson(response, SearchFriendDTO.class);
                    //tableview takes observable array list not regular array list, so we have to convert
                    obsAdd = FXCollections.observableArrayList(searchFriendDTO.getUsersList());
                    txtFriends.setItems(obsAdd);
                    
                    
                } catch (IOException ex) {
                    Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
    
    
    txtFriends.getColumns().forEach(column -> {
        column.setStyle(null);
        });
        String cssFile = getClass().getResource("blueTable.css").toExternalForm();
        txtFriends.getStylesheets().add(cssFile);
    
    reqTable.setStyle(
            "-fx-background-color: #f7bc50; " +
            "-fx-border-color: #f7bc50;"+
            "-fx-font-weight: bold;"+
            "-fx-border-width: 5px;"+
            "-fx-border-radius: 1px;"
    );
    
    ReqCol.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
    
    AcceptCol.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
    
    DeclineCol.setStyle(
            "-fx-text-fill: #35415A; "+
            "-fx-font-weight: bold;"
            +"-fx-background-color: #f7bc50;"
            + "-fx-border-color: #f7bc50;"
            +"-fx-alignment: CENTER;"
    );
    SearchButton.setCursor(javafx.scene.Cursor.HAND);
    } 
    
    public void onAcceptButtonClicked(int index){
    
        String sent_to = reqTable.getItems().get(index).getSentTo();
        String sent_from = reqTable.getItems().get(index).getSentFrom();
        AcceptFriendDTO acceptFriendDTO = new AcceptFriendDTO("acceptFriend", sent_to, sent_from);
        Gson gson = new Gson();
        String request = gson.toJson(acceptFriendDTO,AcceptFriendDTO.class);
        ps.println(request);
        obsReq.remove(index);
        reqTable.refresh();
        
    
    }
    public void onDeclineButtonClicked(int index){
        String sent_to = reqTable.getItems().get(index).getSentTo();
        String sent_from = reqTable.getItems().get(index).getSentFrom();
        DeclineFriendDTO declineFriendDTO = new DeclineFriendDTO(sent_to, sent_from, "DeclineFriend");
        Gson gson = new Gson();
        String request = gson.toJson(declineFriendDTO,DeclineFriendDTO.class);
        ps.println(request);
        obsReq.remove(index);
        reqTable.refresh();
    }
    public void onAddButtonClicked (int index){
        String name= txtFriends.getItems().get(index).getName();
        boolean sent_before = txtFriends.getItems().get(index).isSentRequest();
        AddFriendDTO addFriendDTO = new AddFriendDTO();
        addFriendDTO.setUserName(Client.username);
        addFriendDTO.setFriendName(name);
        addFriendDTO.setRequestType(sent_before ? "RemoveRequest" : "AddRequest");
        txtFriends.getItems().get(index).setSentRequest(!sent_before);
        txtFriends.refresh();
        Gson gson = new Gson();
        String request = gson.toJson(addFriendDTO,AddFriendDTO.class);
        ps.println(request);
        String Alert;
        try {
            Alert = br.readLine();
            if(Alert.equals(" Your friend request is already sent ")){
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setHeaderText("This is an information window.");
                alert.setContentText(" Your friend request is already sent ");
                alert.showAndWait();
            
            }
        } catch (IOException ex) {
            Logger.getLogger(AddFriendController.class.getName()).log(Level.SEVERE, null, ex);
        }
            System.out.println("Alert");
            
        
    
    }

    
}

