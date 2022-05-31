package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.AlertException;
import com.otto15.client.gui.Locales;
import com.otto15.client.gui.Localization;
import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.CommandModel;
import com.otto15.client.gui.models.TableModel;
import com.otto15.common.entities.User;
import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class MainController extends AbstractController {

    private final User user;
    private final TableModel tableModel;
    private final CommandModel commandModel;

    @FXML
    private Label usernameLabel;
    @FXML
    private Button tableButton;
    @FXML
    private Button visualizeButton;
    @FXML
    private BorderPane borderPane;
    @FXML
    private ComboBox<Locales> languageComboBox;

    public MainController(User user) {
        this.user = user;
        tableModel = new TableModel(user);
        commandModel = new CommandModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(user.getLogin());
        languageComboBox.setItems(FXCollections.observableArrayList(Locales.values()));
        try {
            tableButtonPressed();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addButtonPressed(Event event) {
        openPopupWindow(event, Resources.ADD_PATH, aClass -> new PersonProcessController(user));
    }

    public void clearButtonPressed() {
        try {
            CommandModel commandModel = new CommandModel();
            commandModel.clear(user);
        } catch (AlertException e) {
            PerformanceState.getInstance().switchPerformanceStatus();
            Platform.exit();
        }
    }

    public void tableButtonPressed() throws IOException {
        visualizeButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        tableButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        Localization localization = new Localization();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.TABLE_PATH.getPath())));
        loader.setControllerFactory(aClass -> new TableController(tableModel));
        loader.setResources(localization.getResourceBundle());

        borderPane.setCenter(loader.load());
    }

    public void visualizeButtonPressed() throws IOException {
        visualizeButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        tableButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        Localization localization = new Localization();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.VISUALIZE_PATH.getPath())));
        loader.setControllerFactory(aClass -> new VisualizeController(tableModel));
        loader.setResources(localization.getResourceBundle());

        borderPane.setCenter(loader.load());
    }

    public void historyButtonPressed(Event event) {
        try {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText(null);
            alert.setContentText(commandModel.history(user).getMessage());
            alert.showAndWait();
        } catch (AlertException e) {
            e.showAlert();
        }
    }

    public void logoutButtonPressed(Event event) {
        switchScene(event, Resources.LOGIN_WINDOW_PATH, aClass -> new LoginController());
    }

    public void removeGreaterButtonPressed(Event event) {
        openPopupWindow(event, Resources.REMOVE_GREATER_PATH, aClass -> new PersonProcessController(user));
    }

    public void addIfMinButtonPressed(Event event) {
        openPopupWindow(event, Resources.ADD_IF_MIN_PATH, aClass -> new PersonProcessController(user));
    }

    public void exitButtonPressed() {
        PerformanceState.getInstance().switchPerformanceStatus();
        Platform.exit();
    }

    public void comboAction(Event event) {
        Localization localization = new Localization();
        localization.setResourceBundle(languageComboBox.getValue().getLocale());
    }

}
