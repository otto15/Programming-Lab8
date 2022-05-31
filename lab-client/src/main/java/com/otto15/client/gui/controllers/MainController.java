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
import javafx.css.PseudoClass;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.util.StringConverter;

import java.io.IOException;
import java.net.URL;
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
    private static boolean lastViewWasVisualisation = false;

    public MainController(User user) {
        this.user = user;
        tableModel = TableModel.getInstance(user);
        commandModel = new CommandModel();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        usernameLabel.setText(user.getLogin());

        languageComboBox.setValue(Localization.getInstance().getLocales());
        languageComboBox.getItems().setAll(Locales.values());
        languageComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(Locales locales) {
                return locales.getRepresentation();
            }

            @Override
            public Locales fromString(String s) {
                for (Locales locale : Locales.values()) {
                    if (s.equals(locale.getRepresentation())) {
                        return locale;
                    }
                }
                return null;
            }
        });
        try {
            if (lastViewWasVisualisation) {
                visualizeButtonPressed();
            } else {
                tableButtonPressed();
            }
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
        lastViewWasVisualisation = false;
        visualizeButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        tableButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        Localization localization = Localization.getInstance();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.TABLE_PATH.getPath())));
        loader.setControllerFactory(aClass -> new TableController(tableModel));
        loader.setResources(localization.getResourceBundle());

        borderPane.setCenter(loader.load());
    }

    public void visualizeButtonPressed() throws IOException {
        lastViewWasVisualisation = true;
        visualizeButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), true);
        tableButton.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        Localization localization = Localization.getInstance();

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
        Localization localization = Localization.getInstance();
        localization.setResourceBundle(languageComboBox.getValue());
        languageComboBox.setValue(languageComboBox.getValue());
        switchScene(event, Resources.MAIN_WINDOW_PATH, aClass -> new MainController(user));
    }

}
