package controllers;

import DTOs.NotificationDTO;
import client.Client;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CustomNotification extends Stage {

    private static final double NOTIFICATION_WIDTH = 300;
    private static final double NOTIFICATION_HEIGHT = 80;

    public CustomNotification(NotificationDTO nDTO) {
        if (Client.username == null ? nDTO.getUsername() == null : Client.username.equals(nDTO.getUsername())){
        Client.notifyCountTxt.setText(String.valueOf(Client.notifyCount + 1));
        // Set the stage style to TRANSPARENT for a custom look
        initStyle(StageStyle.TRANSPARENT);

        // Create the notification content
        Label label = new Label(nDTO.getMessage());
        label.setTextFill(Color.web("#f7bc50"));
        label.setStyle("-fx-font-weight: bold;");

        // Create a stack pane to hold the label
        StackPane root = new StackPane(label);
        root.setStyle("-fx-background-color: #35415A; -fx-padding: 10;");

        // Set up the scene
        Scene scene = new Scene(root, NOTIFICATION_WIDTH, NOTIFICATION_HEIGHT);
        scene.setFill(Color.TRANSPARENT);
        setScene(scene);

        // Set the position of the notification in the bottom right corner
        Screen screen = Screen.getPrimary();
        setX(screen.getBounds().getMaxX() - NOTIFICATION_WIDTH - 30);
        setY(screen.getBounds().getMaxY() - NOTIFICATION_HEIGHT - 60);

        // Animate the notification in and out
        animateInAndOut();}
    }

    private void animateInAndOut() {
        // Fade in animation
        KeyFrame keyFrameIn = new KeyFrame(Duration.seconds(10), new KeyValue(opacityProperty(), 0));
        //KeyFrame keyFrameFadeIn = new KeyFrame(Duration.seconds(2), new KeyValue(opacityProperty(), 1));

        // Fade out animation
        //KeyFrame keyFrameFadeOut = new KeyFrame(Duration.seconds(4), new KeyValue(opacityProperty(), 0));

        // Create a timeline for the animation
        Timeline timeline = new Timeline(keyFrameIn);

        // Set an event handler to close the notification when the timeline finishes
        timeline.setOnFinished(event -> close());

        // Show the stage
        show();

        // Play the animation
        timeline.play();
    }
}
