package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.AlertException;
import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.CommandModel;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InfoController extends AbstractController {
    @FXML
    private Label idLabel;
    @FXML
    private Label nameLabel;
    @FXML
    private Label xCoordinatesLabel;
    @FXML
    private Label yCoordinatesLabel;
    @FXML
    private Label heightLabel;
    @FXML
    private Label eyeColorLabel;
    @FXML
    private Label hairColorLabel;
    @FXML
    private Label countryLabel;
    @FXML
    private Label xLocationLabel;
    @FXML
    private Label yLocationLabel;
    @FXML
    private Label zLocationLabel;

    private final User user;
    private final Person person;

    public InfoController(Person person, User user) {
        this.person = person;
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        idLabel.setText(person.getId().toString());
        nameLabel.setText(person.getName());
        xCoordinatesLabel.setText(String.valueOf(person.getCoordinates().getX()));
        yCoordinatesLabel.setText(String.valueOf(person.getCoordinates().getY()));
        heightLabel.setText(String.valueOf(person.getHeight()));
        eyeColorLabel.setText(person.getEyeColor() == null ? "Unknown" : person.getEyeColor().name());
        hairColorLabel.setText(person.getHairColor() == null ? "Unknown" : person.getHairColor().name());
        countryLabel.setText(person.getNationality().name());
        xLocationLabel.setText(String.valueOf(person.getLocation().getX()));
        yLocationLabel.setText(String.valueOf(person.getLocation().getY()));
        zLocationLabel.setText(String.valueOf(person.getLocation().getZ()));
    }

    public void updateButtonPressed(Event event) {
        try {
            if (!person.getAuthor().equals(user.getLogin())) {
                throw new AlertException("You're not author of this element, so you can't update it");
            }
            switchScene(event, Resources.UPDATE_PATH, aClass -> new UpdateCommandController(user, person));
        } catch (AlertException e) {
            e.showAlert();
            if (e.getCause() != null && IOException.class.equals(e.getCause().getClass())) {
                PerformanceState.getInstance().switchPerformanceStatus();
                Platform.exit();
            }
        }
    }

    public void deleteButtonPressed(Event event) {
        CommandModel commandModel = new CommandModel();
        try {
            if (!person.getAuthor().equals(user.getLogin())) {
                throw new AlertException("You're not author of this element, so you can't delete it");
            }
            commandModel.remove(person.getId(), user);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.close();
        } catch (AlertException e) {
            e.showAlert();
            if (e.getCause() != null && IOException.class.equals(e.getCause().getClass())) {
                PerformanceState.getInstance().switchPerformanceStatus();
                Platform.exit();
            }
        }
    }
}
