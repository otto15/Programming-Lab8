package com.otto15.client.exceptions;

import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.scene.control.Alert;

public class AlertException extends Exception {
    private Alert alert;

    public AlertException(String message) {
        this(message, null);
    }

    public AlertException(String message, Throwable cause) {
        super(message, cause);
    }

    public void showAlert() {
        alert = new Alert(Alert.AlertType.ERROR);
        alert.setHeaderText(null);
        alert.setContentText(getMessage());
        alert.showAndWait();
    }

    public void fatalShowAlert() {
        showAlert();
        Platform.exit();
    }
}
