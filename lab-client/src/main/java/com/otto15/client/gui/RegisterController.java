package com.otto15.client.gui;

import com.otto15.client.ConnectionHandler;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.SignInCommand;
import com.otto15.common.commands.SignUpCommand;
import com.otto15.common.entities.User;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.state.PerformanceState;
import jakarta.validation.constraints.NotNull;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class RegisterController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private TextField repeatPasswordField;
    @FXML
    private Label errorLabel;

    public void switchToLoginScene(Event event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/login.fxml")));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void registerButtonPressed(Event event) throws IOException {
        errorLabel.setVisible(false);
        String login = usernameField.getText();
        String password = passwordField.getText();
        String repeatPassword = repeatPasswordField.getText();
        if (login.isEmpty()) {
            setAndShowErrorMessage("Login must not be empty");
            return;
        }
        if (!password.equals(repeatPassword)) {
            setAndShowErrorMessage("Password not equals");
            return;
        }
        if (password.isEmpty()) {
            setAndShowErrorMessage("Password must not be empty");
            return;
        }
        Request request = new Request(new SignUpCommand(), new Object[]{new User(login, password)});

        ConnectionHandler connectionHandler = new ConnectionHandler(new PerformanceState());
        NetworkListener networkListener = new ClientNetworkListener(connectionHandler);
        //TODO remove hardcoding
        connectionHandler.openConnection(System.getenv("DB_HOST"), Integer.parseInt(System.getenv("SERVER_PORT")));
        Response response = networkListener.listen(request);

        if (response.getUser() == null) {
            errorLabel.setText("Such user already exist");
            errorLabel.setVisible(true);
            usernameField.setText("");
            passwordField.setText("");
            repeatPasswordField.setText("");
        } else {
            //TODO made common load for login and register
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/main.fxml")));
            root = loader.load();

            MainController mainController = loader.getController();
            mainController.display(response.getUser().getLogin());

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
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