package com.otto15.client.gui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.Objects;
import java.util.ResourceBundle;

public class HelloFX extends Application {

    @Override
    public void start(Stage stage) {
        try {
            Localization localization = new Localization();

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/login.fxml")));
            loader.setResources(localization.getResourceBundle());

            Parent root = loader.load();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}