package com.otto15.client.gui.controllers;

import com.otto15.client.gui.Resources;
import com.otto15.common.entities.Person;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class MainController extends AbstractController {
    private final String username;
    @FXML
    private Label usernameLabel;
    @FXML
    private GridPane pane;
    private Set<Person> persons;
    private

    public MainController(String username) {
        this.username = username;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText("username:\n" + username);
    }

    public void addButtonPressed() {
        //TODO add
    }

    public void clearButtonPressed() {

    }

    public void logoutButtonPressed(Event event) {
        switchScene(event, Resources.LOGIN_WINDOW_PATH, (aClass -> new LoginController()));
    }

    public void exitButtonPressed(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }

}
