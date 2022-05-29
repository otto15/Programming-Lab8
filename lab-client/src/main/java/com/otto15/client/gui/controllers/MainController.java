package com.otto15.client.gui.controllers;

import com.otto15.client.gui.Localization;
import com.otto15.client.gui.Resources;
import com.otto15.common.entities.User;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController extends AbstractController {
    private final User user;

    @FXML
    private Label usernameLabel;

    @FXML
    private BorderPane borderPane;

    @FXML
    private Button tableButton;

    public MainController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(user.getLogin());
        try {
            tableButtonPressed();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addButtonPressed() {
        //TODO add
    }

    public void clearButtonPressed() {

    }

    public void tableButtonPressed() throws IOException {
        Localization localization = new Localization();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.TABLE_PATH.getPath())));
        loader.setResources(localization.getResourceBundle());

        tableButton.requestFocus();
        borderPane.setCenter(loader.load());
    }

    public void visualizeButtonPressed() throws IOException {
        Localization localization = new Localization();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.VISUALIZE_PATH.getPath())));
        loader.setResources(localization.getResourceBundle());

        borderPane.setCenter(loader.load());
    }

    public void logoutButtonPressed(Event event) {
        switchScene(event, Resources.LOGIN_WINDOW_PATH, (aClass -> new LoginController()));
    }

    public void exitButtonPressed(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.close();
    }
}
