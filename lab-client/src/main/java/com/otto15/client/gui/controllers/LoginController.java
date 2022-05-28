package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.LostConnectionException;
import com.otto15.client.exceptions.ValidationException;
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
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class LoginController extends AbstractController {

    private AuthModel authModel;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label usernameErrorLabel;
    @FXML
    private Label passwordErrorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authModel = new AuthModel();

        authModel.usernameProperty().bind(usernameField.textProperty());
        authModel.passwordProperty().bind(passwordField.textProperty());
    }

    public void switchToRegisterScene(Event event) {
        switchScene(event, Resources.REGISTER_WINDOW_PATH, (aClass -> new RegisterController()));

    }

    public void logInButtonPressed(Event event) {
        usernameErrorLabel.setVisible(false);
        passwordErrorLabel.setVisible(false);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Response response = authModel.login();
            switchScene(event, Resources.MAIN_WINDOW_PATH, (aClass -> new MainController(response.getUser())));
        } catch (ValidationException e) {
            List<String> validationErrorsList = e.getValidationErrorsList();
            List<TextField> errorFields = Arrays.asList(usernameField, passwordField);
            List<Label> errorLabels = Arrays.asList(usernameErrorLabel, passwordErrorLabel);
            showErrors(errorFields, errorLabels, validationErrorsList);
        } catch (LostConnectionException e) {
            e.showAlert();
            stage.close();
        }
    }
}
