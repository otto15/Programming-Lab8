package com.otto15.client.gui.controllers;

import com.otto15.client.gui.Localization;
import com.otto15.client.gui.Resources;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public abstract class AbstractController implements Initializable {

    public void switchScene(Event event, Resources resources, Callback<Class<?>, Object> callback) {
        try {
            Localization localization = new Localization();

            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(resources.getPath())));
            loader.setControllerFactory(callback);
            loader.setResources(localization.getResourceBundle());

            Parent root = loader.load();

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showErrors(List<TextField> errorFields, List<Label> errorLabels, List<String> errorMessages) {
        for (int i = 0; i < errorMessages.size(); i++) {
            String errorMessage = errorMessages.get(i);
            if (errorMessage == null) {
                continue;
            }
            errorFields.get(i).setText("");
            errorLabels.get(i).setText(errorMessage);
            errorLabels.get(i).setVisible(true);
        }
    }
}