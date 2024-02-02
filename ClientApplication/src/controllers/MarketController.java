package controllers;

import DTOs.ItemDTO;
import DTOs.ItemListDTO;
import DTOs.RegisterDTO;
import DTOs.WishListDTO;
import client.Client;
import com.google.gson.Gson;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import de.jensd.fx.glyphs.materialicons.MaterialIcon;
import de.jensd.fx.glyphs.materialicons.MaterialIconView;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintStream;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.RotateTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

/**
 * FXML Controller class
 *
 * @author JUMAA
 */
public class MarketController implements Initializable {

    @FXML
    private TableView<ItemDTO> tableItems;
    @FXML
    private TableColumn<ItemDTO, String> idClm;
    @FXML
    private TableColumn<ItemDTO, String> nameClm;
    @FXML
    private TableColumn<ItemDTO, String> descClm;
    @FXML
    private TableColumn<ItemDTO, String> priceClm;
    @FXML
    private TableColumn<ItemDTO, String> catClm;
    @FXML
    private TableColumn<ItemDTO, ItemDTO> addToWishlistClm;

    BufferedReader br;
    PrintStream ps;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        br = SocketHolder.getInstance().getBufferedReader();
        ps = SocketHolder.getInstance().getPrintStream();
        loadItemsFromServer();
        setupTableColumns();
    }

    private void loadItemsFromServer() {
        try {
            ItemListDTO itemlist = new ItemListDTO("Sendmarketitems");
            Gson gson = new Gson();
            String request = gson.toJson(itemlist, ItemListDTO.class);
            ps.println(request);

            String result = br.readLine();
            System.out.println("these are our items" + result);
            itemlist = gson.fromJson(result, ItemListDTO.class);
            ObservableList<ItemDTO> items = FXCollections.observableArrayList(itemlist.getItems());

            // Set the items in the table
            tableItems.setItems(items);
        } catch (IOException ex) {
            Logger.getLogger(MarketController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupTableColumns() {
        idClm.setCellValueFactory(new PropertyValueFactory<>("itemId"));
        descClm.setCellValueFactory(new PropertyValueFactory<>("description"));
        priceClm.setCellValueFactory(new PropertyValueFactory<>("price"));
        nameClm.setCellValueFactory(new PropertyValueFactory<>("itemName"));
        catClm.setCellValueFactory(new PropertyValueFactory<>("category"));

        // Set up the "Add to Wishlist" column with a custom button cell
        //addToWishlistClm.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        addToWishlistClm.setCellFactory(param -> new TableCell<ItemDTO, ItemDTO>() {
            final Button addbutton = new Button("ADD");

            {
                addbutton.setStyle(
                        "-fx-background-color: #f7bc50; " +
                        "-fx-text-fill: #35415A;" +
                        "-fx-font-weight: bold;"
                );
                
                addbutton.setCursor(javafx.scene.Cursor.HAND);

                MaterialIconView icon = new MaterialIconView(MaterialIcon.ADD_CIRCLE_OUTLINE);
                icon.setFill(javafx.scene.paint.Color.valueOf("#35415A"));

                addbutton.setGraphic(icon);
                addbutton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        int index = getIndex();
                        ItemDTO selectedItem = tableItems.getItems().get(index);

                        // Add the selected item to the WishlistDTO
                        WishListDTO item = new WishListDTO("AddItem", Client.username, selectedItem.getItemId(), 0);

                        // Send the WishlistDTO to the server
                        Gson gson = new Gson();
                        String wishlistJson = gson.toJson(item, WishListDTO.class);
                        ps.println(wishlistJson);
                    }
                });
            }

            @Override
            protected void updateItem(ItemDTO item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    setGraphic(addbutton);
                    addbutton.setOnAction(event -> {
            int index = getIndex();
            ItemDTO selectedItem = tableItems.getItems().get(index);

            // Add the selected item to the WishlistDTO
            WishListDTO wishListItem = new WishListDTO("AddItem", Client.username, selectedItem.getItemId(), 0);

            // Send the WishlistDTO to the server
            Gson gson = new Gson();
            String wishlistJson = gson.toJson(wishListItem, WishListDTO.class);
            ps.println(wishlistJson);

            // Show a pop-up message
            showItemAddedPopup(selectedItem.getItemName());
        });
                }
            }
        });

     tableItems.getColumns().forEach(column -> {
        column.setStyle(null);
        });
        String cssFile = getClass().getResource("blueTable.css").toExternalForm();
        tableItems.getStylesheets().add(cssFile);
    }
    
    private void showItemAddedPopup(String itemName) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.initStyle(StageStyle.TRANSPARENT);
    alert.setTitle(null);
    alert.setHeaderText(null);
    alert.setGraphic(null);
    //alert.setContentText("Item '" + itemName + "' added to your wishlist!");
     Label label = new Label("Item '" + itemName + "' added to your wishlist!");
    label.setStyle("-fx-font-weight: bold; -fx-text-fill: #f7bc50;");
    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.setContent(label);
    dialogPane.setPrefWidth(400); // Set the preferred width
    dialogPane.setPrefHeight(80);
    dialogPane.setStyle("-fx-background-color: #35415A;");
    FontAwesomeIconView starIcon = new FontAwesomeIconView(FontAwesomeIcon.STAR);
    starIcon.setFill(Color.web("#f7bc50"));
    starIcon.setSize("2em");
    dialogPane.getChildren().add(starIcon);
    Button okButton = (Button) dialogPane.lookupButton(ButtonType.OK);
    okButton.setGraphic(starIcon);
    okButton.setCursor(javafx.scene.Cursor.HAND);
    okButton.setStyle("-fx-background-color: transparent; -fx-text-fill: #f7bc50; -fx-font-weight: bold;");
    animateStar(starIcon, alert);
    alert.show();
}
     private void animateStar(FontAwesomeIconView starIcon, Alert alert) {
        RotateTransition rotateTransition = new RotateTransition(Duration.seconds(1), starIcon);
        rotateTransition.setByAngle(360); // Rotate by 360 degrees
        rotateTransition.setInterpolator(Interpolator.LINEAR);
        rotateTransition.setCycleCount(Timeline.INDEFINITE); // Rotate indefinitely
        TranslateTransition transitionTransition = new TranslateTransition(Duration.seconds(2), starIcon);
        transitionTransition.setToY(-200); // Move the star up by 100 pixels
        transitionTransition.setOnFinished(e -> starIcon.setOpacity(0)); // Make the star disappear when the animation is finished
        ParallelTransition parallelTransition = new ParallelTransition(rotateTransition, transitionTransition);
        parallelTransition.play();
        alert.close();
    }

     
    
}
