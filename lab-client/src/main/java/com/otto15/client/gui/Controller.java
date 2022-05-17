package com.otto15.client.gui;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Controller {
    private Stage stage;
    private Scene scene;
    private Parent root;
    @FXML
    private TextField username;
    @FXML
    private TextField password;
    @FXML
    private Text text1;
    @FXML
    private Text text2;

    public void switchToRegisterScene(Event event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/register.fxml")));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void switchToLoginScene(Event event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/login.fxml")));
        stage = (Stage)((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void logInButtonPressed(Event event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/views/msg.fxml")));

        System.out.println(username.getText());
        System.out.println(password.getText());
        text1.setText(username.getText());
        text2.setText(password.getText());


        Stage stageMsg = (Stage)((Node) event.getSource()).getScene().getWindow();
        Scene sceneMsg = new Scene(root);
        stageMsg.setScene(sceneMsg);
        stageMsg.show();

    }
}
