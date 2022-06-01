package com.otto15.client.gui.models;

import com.otto15.client.exceptions.AlertException;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.AddCommand;
import com.otto15.common.commands.AddIfMinCommand;
import com.otto15.common.commands.ClearCommand;
import com.otto15.common.commands.HistoryCommand;
import com.otto15.common.commands.RemoveByIdCommand;
import com.otto15.common.commands.RemoveGreaterCommand;
import com.otto15.common.commands.UpdateCommand;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.entities.enums.Color;
import com.otto15.common.entities.enums.Country;
import com.otto15.common.entities.validators.PersonValidator;
import com.otto15.common.exceptions.ValidationException;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.control.Alert;
import javafx.scene.layout.GridPane;

import javax.swing.*;
import javax.swing.text.html.ImageView;
import java.io.IOException;

public class CommandModel {
    private final StringProperty name;
    private final StringProperty xCoordinates;
    private final StringProperty yCoordinates;
    private final StringProperty height;
    private final ObjectProperty<Color> eyeColor;
    private final ObjectProperty<Color> hairColor;
    private final ObjectProperty<Country> nationality;
    private final StringProperty xLocation;
    private final StringProperty yLocation;
    private final StringProperty zLocation;

    private final NetworkListener networkListener;

    {
        networkListener = ClientNetworkListener.getInstance();
        name = new SimpleStringProperty();
        xCoordinates = new SimpleStringProperty();
        yCoordinates = new SimpleStringProperty();
        height = new SimpleStringProperty();
        eyeColor = new SimpleObjectProperty<>();
        hairColor = new SimpleObjectProperty<>();
        nationality = new SimpleObjectProperty<>();
        xLocation = new SimpleStringProperty();
        yLocation = new SimpleStringProperty();
        zLocation = new SimpleStringProperty();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public StringProperty xCoordinatesProperty() {
        return xCoordinates;
    }

    public StringProperty yCoordinatesProperty() {
        return yCoordinates;
    }

    public StringProperty heightProperty() {
        return height;
    }

    public ObjectProperty<Color> eyeColorProperty() {
        return eyeColor;
    }

    public ObjectProperty<Color> hairColorProperty() {
        return hairColor;
    }

    public ObjectProperty<Country> nationalityProperty() {
        return nationality;
    }

    public StringProperty xLocationProperty() {
        return xLocation;
    }

    public StringProperty yLocationProperty() {
        return yLocation;
    }

    public StringProperty zLocationProperty() {
        return zLocation;
    }

    private void sendRequestInNewThread(Request request) {
        new Thread(() -> {
            try {
                Response response = networkListener.listen(request);
                Platform.runLater(new AlertException(response.getMessage())::showAlert);
            } catch (IOException e) {
                new AlertException("Server doesn't work").fatalShowAlert();
            }
        }).start();
    }

    public void add(User user) throws ValidationException, AlertException {
        Person person = PersonValidator.validatePersonFromString(name.get(), xCoordinates.get(), yCoordinates.get(), height.get(), eyeColor.get(), hairColor.get(), nationality.get(), xLocation.get(), yLocation.get(), zLocation.get());

        sendRequestInNewThread(new Request(new AddCommand(), new Object[]{person, user}));
    }

    public void addIfMin(User user) throws ValidationException, AlertException {
        Person person = PersonValidator.validatePersonFromString(name.get(), xCoordinates.get(), yCoordinates.get(), height.get(), eyeColor.get(), hairColor.get(), nationality.get(), xLocation.get(), yLocation.get(), zLocation.get());

        sendRequestInNewThread(new Request(new AddIfMinCommand(), new Object[]{person, user}));
    }

    public void update(long id, User user) throws ValidationException, AlertException {
        Person person = PersonValidator.validatePersonFromString(name.get(), xCoordinates.get(), yCoordinates.get(), height.get(), eyeColor.get(), hairColor.get(), nationality.get(), xLocation.get(), yLocation.get(), zLocation.get());
        person.setId(id);

        sendRequestInNewThread(new Request(new UpdateCommand(), new Object[]{person, user}));
    }

    public void remove(long id, User user) throws AlertException {
        sendRequestInNewThread(new Request(new RemoveByIdCommand(), new Object[]{id, user}));
    }

    public void clear(User user) throws AlertException {
        sendRequestInNewThread(new Request(new ClearCommand(), new Object[] {user}));
    }

    public void removeGreater(User user) throws ValidationException {
        Person person = PersonValidator.validatePersonFromString(name.get(), xCoordinates.get(), yCoordinates.get(), height.get(), eyeColor.get(), hairColor.get(), nationality.get(), xLocation.get(), yLocation.get(), zLocation.get());

        sendRequestInNewThread(new Request(new RemoveGreaterCommand(), new Object[]{person, user}));
    }

    public void history(User user) {

        new Thread(() -> {
            try {
                Response response = networkListener.listen(new Request(new HistoryCommand(), new Object[]{user}));
                Platform.runLater(() -> {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setHeaderText(null);
                    alert.setContentText(response.getMessage());
                    alert.showAndWait();
                });
            } catch (IOException e) {
                Platform.runLater(new AlertException("Server isn't available, try later", e)::fatalShowAlert);
            }
        }).start();

    }
}
