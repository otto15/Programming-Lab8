package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.AlertException;
import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.CommandModel;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.event.Event;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class TableMenuController extends AbstractController {
    private final User user;
    private final Person person;

    public TableMenuController(Person person, User user) {
        this.person = person;
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public void updateButtonPressed(Event event) {
        try {
            if (!person.getAuthor().equals(user.getLogin())) {
                throw new AlertException("You're not author of this element, so you can't update it");
            }
            openPopupWindow(event, Resources.UPDATE_PATH, aClass -> new UpdateCommandController(user, person));
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
        } catch (AlertException e) {
            e.showAlert();
            if (e.getCause() != null && IOException.class.equals(e.getCause().getClass())) {
                PerformanceState.getInstance().switchPerformanceStatus();
                Platform.exit();
            }
        }
    }
}
