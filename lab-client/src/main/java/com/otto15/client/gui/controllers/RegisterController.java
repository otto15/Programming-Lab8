package com.otto15.client.gui.controllers;

import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.AuthModel;
import com.otto15.common.network.Response;
import jakarta.validation.constraints.NotNull;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
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
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authModel = new AuthModel();

        authModel.usernameProperty().bind(usernameField.textProperty());
        authModel.passwordProperty().bind(passwordField.textProperty());
        authModel.repeatedPasswordProperty().bind(repeatPasswordField.textProperty());
    }

    public void switchToLoginScene(Event event) {
        switchScene(event, Resources.LOGIN_WINDOW_PATH, (aClass -> new LoginController()));
//        Localization localization = new Localization();
//
//        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.LOGIN_WINDOW_PATH.getPath())));
//        loader.setResources(localization.getResourceBundle());
//
//        Parent root = loader.load();
//
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    public void registerButtonPressed(Event event) {
        errorLabel.setVisible(false);

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Response response = authModel.register();

            if (!response.isStatus()) {
                errorLabel.setText("Such user already exist");
                errorLabel.setVisible(true);
                usernameField.setText("");
                passwordField.setText("");
                repeatPasswordField.setText("");
            } else {
                //TODO made common load for login and register
                switchScene(event, Resources.MAIN_WINDOW_PATH, (aClass -> new MainController(response.getUser().getLogin())));
//
//                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.MAIN_WINDOW_PATH.getPath())));
//                root = loader.load();
//
//                MainController mainController = loader.getController();
//                mainController.display(response.getUser().getLogin());
//
//                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//                scene = new Scene(root);
//                stage.setScene(scene);
//                stage.show();
            }
        } catch (IOException e) {
            stage.close();

        }
    }

    private void setAndShowErrorMessage(@NotNull String message) {
        errorLabel.setText(message);
        errorLabel.setVisible(true);
        usernameField.setText("");
        passwordField.setText("");
        repeatPasswordField.setText("");
    }
}
