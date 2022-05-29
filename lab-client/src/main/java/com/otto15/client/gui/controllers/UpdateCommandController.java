package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.AlertException;
import com.otto15.client.gui.models.CommandModel;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.entities.enums.Color;
import com.otto15.common.entities.enums.Country;
import com.otto15.common.exceptions.ValidationException;
import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class UpdateCommandController extends AbstractController {
    private final User user;
    private final Person person;
    private CommandModel commandModel;

    @FXML
    private Label idLabel;
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

    public UpdateCommandController(User user, Person person) {
        this.user = user;
        this.person = person;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        commandModel = new CommandModel();

        eyeColorComboBox.setItems(FXCollections.observableArrayList(Color.values()));
        hairColorComboBox.setItems(FXCollections.observableArrayList(Color.values()));
        nationalityComboBox.setItems(FXCollections.observableArrayList(Country.values()));

        idLabel.setText(person.getId().toString());
        nameField.setText(person.getName());
        xCoordinatesField.setText(String.valueOf(person.getCoordinates().getX()));
        yCoordinatesField.setText(String.valueOf(person.getCoordinates().getY()));
        heightField.setText(String.valueOf(person.getHeight()));
        eyeColorComboBox.setValue(person.getEyeColor());
        hairColorComboBox.setValue(person.getHairColor());
        nationalityComboBox.setValue(person.getNationality());
        xLocationField.setText(String.valueOf(person.getLocation().getX()));
        yLocationField.setText(String.valueOf(person.getLocation().getY()));
        zLocationField.setText(String.valueOf(person.getLocation().getZ()));

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

    public void updateButtonPressed(Event event) {
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        try {
            commandModel.update(person.getId(), user);
            stage.close();
        } catch (ValidationException e) {
            //TODO
        } catch (AlertException e) {
            e.showAlert();
            PerformanceState.getInstance().switchPerformanceStatus();
            Platform.exit();
        }
    }
}
