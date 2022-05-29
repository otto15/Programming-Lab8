package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.LostConnectionException;
import com.otto15.client.gui.models.CommandModel;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.entities.enums.Color;
import com.otto15.common.entities.enums.Country;
import com.otto15.common.exceptions.ValidationException;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CommandController extends AbstractController {
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

    public CommandController(User user) {
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
            e.getValidationErrorsList().forEach(System.out::println);
            //TODO
        } catch (LostConnectionException e) {
            e.showAlert();
            ((Stage) stage.getOwner()).close();
        }
    }

    public void addIfMinButtonPressed(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            commandModel.addIfMin(user);
            stage.close();
        } catch (ValidationException e) {
            e.getValidationErrorsList().forEach(System.out::println);
            //TODO
        } catch (LostConnectionException e) {
            e.showAlert();
            ((Stage) stage.getOwner()).close();
        }
    }

    public void updateButtonPressed(Event event) {

    }
}
