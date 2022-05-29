package com.otto15.client.gui.controllers;

import com.otto15.client.gui.Localization;
import com.otto15.client.gui.Resources;
import com.otto15.common.entities.User;
import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
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

    public void addButtonPressed(Event event) {
        openPopupWindow(event, Resources.ADD_PATH, aClass -> new CommandController(user));
    }

    public void clearButtonPressed() {

    }

    public void tableButtonPressed() throws IOException {
        Localization localization = new Localization();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.TABLE_PATH.getPath())));
        loader.setControllerFactory(aClass -> new TableController(user));
        loader.setResources(localization.getResourceBundle());

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
        PerformanceState.getInstance().switchPerformanceStatus();
        Platform.exit();
    }
}
