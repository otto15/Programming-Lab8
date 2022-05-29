package com.otto15.client.exceptions;

import javafx.scene.control.Alert;

public class AlertException extends Exception {
    private final Alert alert;

    public AlertException(String message) {
        this(message, null);
    }

    public AlertException(String message, Throwable cause) {
        super(message, cause);
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(message);
    }

    public void showAlert() {
        alert.showAndWait();
    }
}
