package com.otto15.client.gui.controllers;

import com.otto15.client.gui.Localization;
import com.otto15.client.gui.Resources;
import com.sun.tools.javac.Main;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;


public class MainController extends AbstractController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    private final String username;
    @FXML
    private Label usernameLabel;
    @FXML
    private GridPane mainPane;

    public MainController(String username) {
        this.username = username;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText("username: " + username);
    }

//    public void logoutButtonPressed(Event event) {
//        try {
//            Localization localization = new Localization();
//
//            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.LOGIN_WINDOW_PATH.getPath())));
//            loader.setResources(localization.getResourceBundle());
//
//            Parent root = loader.load();
//            Scene scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}
