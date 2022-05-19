package com.otto15.client.gui;

import com.otto15.client.ConnectionHandler;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.SignInCommand;
import com.otto15.common.entities.User;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.state.PerformanceState;
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

public class LoginController {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;
    @FXML
    private Label errorLabel;

    public void switchToRegisterScene(Event event) throws IOException {
        Localization localization = new Localization();

        FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/register.fxml")));
        loader.setResources(localization.getResourceBundle());

        root = loader.load();
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logInButtonPressed(Event event) throws IOException {

        errorLabel.setVisible(false);

        String login = usernameField.getText();
        String password = passwordField.getText();
        Request request = new Request(new SignInCommand(), new Object[] {new User(login, password)});

        ConnectionHandler connectionHandler = new ConnectionHandler(new PerformanceState());
        NetworkListener networkListener = new ClientNetworkListener(connectionHandler);
        //TODO remove hardcoding
        connectionHandler.openConnection(System.getenv("DB_HOST"), Integer.parseInt(System.getenv("SERVER_PORT")));
        Response response = networkListener.listen(request);

        if (response.getUser() == null) {
            usernameField.setText("");
            passwordField.setText("");
            errorLabel.setVisible(true);
        } else {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/main.fxml")));

            Localization localization = new Localization();

            loader.setResources(localization.getResourceBundle());

            root = loader.load();

            MainController mainController = loader.getController();
            mainController.display(response.getUser().getLogin());

            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
}
