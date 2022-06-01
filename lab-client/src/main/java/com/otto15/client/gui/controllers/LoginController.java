package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.AlertException;
import com.otto15.common.exceptions.ValidationException;
import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.AuthModel;
import com.otto15.common.network.Response;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends AbstractController {

    private AuthModel authModel;

    @FXML
    private Button loginButton;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    private ObjectProperty<Response> objectProperty;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authModel = new AuthModel();

        authModel.usernameProperty().bind(usernameField.textProperty());
        authModel.passwordProperty().bind(passwordField.textProperty());
        objectProperty = new SimpleObjectProperty<>(null);
    }

    public void switchToRegisterScene(Event event) {
        if (loginButton.isDisabled()) {
            new AlertException("You pressed login, wait for the response, please!").showAlert();
            return;
        }
        switchScene(event, Resources.REGISTER_WINDOW_PATH, (aClass -> new RegisterController()));

    }

    public void logInButtonPressed(Event event) {
        loginButton.setDisable(true);

        usernameErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            authModel.login(objectProperty);
            objectProperty.addListener((observableValue, response, newResponse) -> {
                processResponse(event);
            });
        } catch (ValidationException e) {
            showError(e.getValidationErrorsList());
            loginButton.setDisable(false);
        }
    }

    public void processResponse(Event event) {
        Response response = objectProperty.get();
        if (!response.isStatus()) {
            showError(Arrays.asList("Error when entering username/password", null));
            loginButton.setDisable(false);
        } else {
            switchScene(event, Resources.MAIN_WINDOW_PATH, (aClass -> new MainController(response.getUser())));
        }
    }

    public void showError(List<String> validationErrorsList) {
        List<Node> errorFields = Arrays.asList(usernameField, passwordField);
        List<Label> errorLabels = Arrays.asList(usernameErrorLabel, passwordErrorLabel);
        showErrors(errorFields, errorLabels, validationErrorsList);
    }
}
