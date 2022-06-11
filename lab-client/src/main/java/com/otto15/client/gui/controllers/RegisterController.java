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

public class RegisterController extends AbstractController {

    private AuthModel authModel;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField repeatPasswordField;
    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Label passwordErrorLabel;
    @FXML
    private Label repeatPasswordLabel;
    @FXML
    private Button registerButton;
    private ObjectProperty<Response> objectProperty;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authModel = new AuthModel();

        authModel.usernameProperty().bind(usernameField.textProperty());
        authModel.passwordProperty().bind(passwordField.textProperty());
        authModel.repeatedPasswordProperty().bind(repeatPasswordField.textProperty());

        objectProperty = new SimpleObjectProperty<>(null);

    }

    public void switchToLoginScene(Event event) {
        if (registerButton.isDisabled()) {
            new AlertException("You pressed register, wait for the response, please!").showAlert();
            return;
        }
        switchScene(event, Resources.LOGIN_WINDOW_PATH, (aClass -> new LoginController()));
    }

    public void registerButtonPressed(Event event) {
        registerButton.setDisable(true);

        usernameErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            authModel.register(objectProperty);
            objectProperty.addListener((observableValue, response, newResponse) -> {
                processResponse(event);
            });
        } catch (ValidationException e) {
            showError(e.getValidationErrorsList());
            registerButton.setDisable(false);
        }
    }

    public void processResponse(Event event) {
        Response response = objectProperty.get();
        if (!response.isStatus()) {
            showError(Arrays.asList("Error when entering username/password", null));
            registerButton.setDisable(false);
        } else {
            switchScene(event, Resources.MAIN_WINDOW_PATH, (aClass -> new MainController(response.getUser())));
        }
    }

    public void showError(List<String> validationErrorsList) {
        List<Node> errorFields = Arrays.asList(usernameField, passwordField, repeatPasswordField);
        List<Label> errorLabels = Arrays.asList(usernameErrorLabel, passwordErrorLabel, repeatPasswordLabel);
        showErrors(errorFields, errorLabels, validationErrorsList);
    }
}
