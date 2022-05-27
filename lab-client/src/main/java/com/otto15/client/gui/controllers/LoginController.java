package com.otto15.client.gui.controllers;

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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController extends AbstractController {

    private AuthModel authModel;

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Label errorLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authModel = new AuthModel();

        authModel.usernameProperty().bind(usernameField.textProperty());
        authModel.passwordProperty().bind(passwordField.textProperty());
    }

    public void switchToRegisterScene(Event event) {
        switchScene(event, Resources.REGISTER_WINDOW_PATH, (aClass -> new RegisterController()));
        //        Localization localization = new Localization();
//
//        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.REGISTER_WINDOW_PATH.getPath())));
//        loader.setResources(localization.getResourceBundle());
//
//        root = loader.load();
//        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
    }

    public void logInButtonPressed(Event event) {
        errorLabel.setVisible(false);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            Response response = authModel.login();

            if (!response.isStatus()) {
                usernameField.setText("");
                passwordField.setText("");
                errorLabel.setVisible(true);
                return;
            }

            switchScene(event, Resources.MAIN_WINDOW_PATH, (aClass -> new MainController(response.getUser().getLogin())));

//            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.MAIN_WINDOW_PATH.getPath())));
//
//            Localization localization = new Localization();
//
//            loader.setResources(localization.getResourceBundle());
//
//            root = loader.load();
//
//            MainController mainController = loader.getController();
//            mainController.display(response.getUser().getLogin());
//            scene = new Scene(root);
//            stage.setScene(scene);
//            stage.show();
        } catch (IOException e) {
            stage.close();
        }
    }
}
