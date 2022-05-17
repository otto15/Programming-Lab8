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

    public void switchToRegisterScene(Event event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/register.fxml")));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//    public void switchToLoginScene(Event event) throws IOException {
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/login.fxml")));
//        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
//        scene = new Scene(root);
//        stage.setScene(scene);
//        stage.show();
//    }

    public void logInButtonPressed(Event event) throws IOException {
//        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/msg.fxml")));

        String login = usernameField.getText();
        String password = passwordField.getText();
        Request request = new Request(new SignInCommand(), new Object[] {new User(login, password)});

        ConnectionHandler connectionHandler = new ConnectionHandler(new PerformanceState());
        NetworkListener networkListener = new ClientNetworkListener(connectionHandler);
        connectionHandler.openConnection("localhost", 1234);
        Response response = networkListener.listen(request);

        if (response.getUser() == null) {
            usernameField.setText("молодец");
            passwordField.setText("переделывай");
        } else {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource("/views/main.fxml")));
            root = loader.load();

            MainController mainController = loader.getController();
            mainController.display(response.getUser().getLogin());

            stage = (Stage)((Node) event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }

//        Stage stageMsg = (Stage)((Node) event.getSource()).getScene().getWindow();
//        Scene sceneMsg = new Scene(root);
//        stageMsg.setScene(sceneMsg);
//        stageMsg.show();

    }
}
