package com.otto15.client.exceptions;

import javafx.scene.control.Alert;

public class LostConnectionException extends Exception {
    private final Alert alert;

    public LostConnectionException(String message) {
        super(message);
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
    }

    public void showAlert() {
        alert.showAndWait();
    }
}
