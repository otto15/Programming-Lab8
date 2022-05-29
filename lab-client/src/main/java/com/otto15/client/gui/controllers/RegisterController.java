package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.AlertException;
import com.otto15.common.exceptions.ValidationException;
import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.AuthModel;
import com.otto15.common.network.Response;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
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

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authModel = new AuthModel();

        authModel.usernameProperty().bind(usernameField.textProperty());
        authModel.passwordProperty().bind(passwordField.textProperty());
        authModel.repeatedPasswordProperty().bind(repeatPasswordField.textProperty());
    }

    public void switchToLoginScene(Event event) {
        switchScene(event, Resources.LOGIN_WINDOW_PATH, (aClass -> new LoginController()));
    }

    public void registerButtonPressed(Event event) {
        usernameErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Response response = authModel.register();
            switchScene(event, Resources.MAIN_WINDOW_PATH, (aClass -> new MainController(response.getUser())));
        } catch (ValidationException e) {
            List<String> validationErrorsList = e.getValidationErrorsList();
            List<TextField> errorFields = Arrays.asList(usernameField, passwordField, repeatPasswordField);
            List<Label> errorLabels = Arrays.asList(usernameErrorLabel, passwordErrorLabel, repeatPasswordLabel);
            showErrors(errorFields, errorLabels, validationErrorsList);
        } catch (AlertException e) {
            e.showAlert();
            stage.close();
        }
    }
}
