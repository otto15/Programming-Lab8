package com.otto15.client.gui;

import com.otto15.client.ConnectionHandler;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.state.PerformanceState;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Localization localization = Localization.getInstance();

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.LOGIN_WINDOW_PATH.getPath())));
            loader.setResources(localization.getResourceBundle());

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.setResizable(false);
            stage.getIcons().add(new Image(Objects.requireNonNull(getClass().getResource("/images/icon.jpg")).toExternalForm()));
            stage.setTitle("Object Vision");
            stage.setOnCloseRequest(windowEvent -> {
                PerformanceState.getInstance().switchPerformanceStatus();
                Platform.exit();
            });
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }

}