package com.otto15.client.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class MainController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Label welcomeText;

    public void display(String username) throws IOException {
        welcomeText.setText(username + " пиздуй нахуй отсюда!!!");
    }

}
