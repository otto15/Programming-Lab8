package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.AlertException;
import com.otto15.client.gui.models.CommandModel;
import com.otto15.common.entities.User;
import com.otto15.common.entities.enums.Color;
import com.otto15.common.entities.enums.Country;
import com.otto15.common.exceptions.ValidationException;
import com.otto15.common.network.Response;
import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PersonProcessController extends AbstractController {
    private final User user;
    private CommandModel commandModel;

    @FXML
    private TextField nameField;
    @FXML
    private TextField xCoordinatesField;
    @FXML
    private TextField yCoordinatesField;
    @FXML
    private TextField heightField;
    @FXML
    private ComboBox<Color> eyeColorComboBox;
    @FXML
    private ComboBox<Color> hairColorComboBox;
    @FXML
    private ComboBox<Country> nationalityComboBox;
    @FXML
    private TextField xLocationField;
    @FXML
    private TextField yLocationField;
    @FXML
    private TextField zLocationField;
    @FXML
    private Label errorNameLabel;
    @FXML
    private Label errorXCoordinatesLabel;
    @FXML
    private Label errorYCoordinatesLabel;
    @FXML
    private Label errorHeightLabel;
    @FXML
    private Label errorCountryLst;
    @FXML
    private Label errorXLocationLabel;
    @FXML
    private Label errorYLocationLabel;
    @FXML
    private Label errorZLocationLabel;

    public PersonProcessController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandModel = new CommandModel();

        eyeColorComboBox.setItems(FXCollections.observableArrayList(Color.values()));
        hairColorComboBox.setItems(FXCollections.observableArrayList(Color.values()));
        nationalityComboBox.setItems(FXCollections.observableArrayList(Country.values()));

        commandModel.nameProperty().bind(nameField.textProperty());
        commandModel.xCoordinatesProperty().bind(xCoordinatesField.textProperty());
        commandModel.yCoordinatesProperty().bind(yCoordinatesField.textProperty());
        commandModel.heightProperty().bind(heightField.textProperty());
        commandModel.eyeColorProperty().bind(eyeColorComboBox.valueProperty());
        commandModel.hairColorProperty().bind(hairColorComboBox.valueProperty());
        commandModel.nationalityProperty().bind(nationalityComboBox.valueProperty());
        commandModel.xLocationProperty().bind(xLocationField.textProperty());
        commandModel.yLocationProperty().bind(yLocationField.textProperty());
        commandModel.zLocationProperty().bind(zLocationField.textProperty());
    }

    public void addButtonPressed(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            commandModel.add(user);
            stage.close();
        } catch (ValidationException e) {
            List<String> validationErrorsList = e.getValidationErrorsList();
            List<Node> errorFields = Arrays.asList(nameField, xCoordinatesField, yCoordinatesField, heightField, nationalityComboBox, xLocationField, yLocationField, zLocationField);
            List<Label> errorLabels = Arrays.asList(errorNameLabel, errorXCoordinatesLabel, errorYCoordinatesLabel, errorHeightLabel, errorCountryLst, errorXLocationLabel, errorYLocationLabel, errorZLocationLabel);
            showErrors(errorFields, errorLabels, validationErrorsList);
        } catch (AlertException e) {
            e.showAlert();
            PerformanceState.getInstance().switchPerformanceStatus();
            Platform.exit();
        }
    }

    public void addIfMinButtonPressed(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            commandModel.addIfMin(user);
            stage.close();
        } catch (ValidationException e) {
            List<String> validationErrorsList = e.getValidationErrorsList();
            List<Node> errorFields = Arrays.asList(nameField, xCoordinatesField, yCoordinatesField, heightField, nationalityComboBox, xLocationField, yLocationField, zLocationField);
            List<Label> errorLabels = Arrays.asList(errorNameLabel, errorXCoordinatesLabel, errorYCoordinatesLabel, errorHeightLabel, errorCountryLst, errorXLocationLabel, errorYLocationLabel, errorZLocationLabel);
            showErrors(errorFields, errorLabels, validationErrorsList);
        } catch (AlertException e) {
            e.showAlert();
            PerformanceState.getInstance().switchPerformanceStatus();
            Platform.exit();
        }
    }

    public void removeGreaterButtonPressed(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            commandModel.removeGreater(user);
        } catch (ValidationException e) {
            e.getValidationErrorsList().forEach(System.out::println);
            //TODO
        }
    }
}
