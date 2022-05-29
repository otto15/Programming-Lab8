package com.otto15.client.gui.models;

import com.otto15.client.exceptions.AlertException;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.AddCommand;
import com.otto15.common.commands.AddIfMinCommand;
import com.otto15.common.commands.ClearCommand;
import com.otto15.common.commands.RemoveByIdCommand;
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
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

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

    public Response add(User user) throws ValidationException, AlertException {
        Person person = PersonValidator.validatePersonFromString(name.get(), xCoordinates.get(), yCoordinates.get(), height.get(), eyeColor.get(), hairColor.get(), nationality.get(), xLocation.get(), yLocation.get(), zLocation.get());

        Response response;
        try {
            response = networkListener.listen(new Request(new AddCommand(), new Object[] {person, user}));
        } catch (IOException e) {
            throw new AlertException("Server isn't available, try later", e);
        }

        return response;
    }

    public Response addIfMin(User user) throws ValidationException, AlertException {
        Person person = PersonValidator.validatePersonFromString(name.get(), xCoordinates.get(), yCoordinates.get(), height.get(), eyeColor.get(), hairColor.get(), nationality.get(), xLocation.get(), yLocation.get(), zLocation.get());

        Response response;
        try {
            response = networkListener.listen(new Request(new AddIfMinCommand(), new Object[] {person, user}));
        } catch (IOException e) {
            throw new AlertException("Server isn't available, try later", e);
        }

        return response;
    }

    public Response update(long id, User user) throws ValidationException, AlertException {
        Person person = PersonValidator.validatePersonFromString(name.get(), xCoordinates.get(), yCoordinates.get(), height.get(), eyeColor.get(), hairColor.get(), nationality.get(), xLocation.get(), yLocation.get(), zLocation.get());
        person.setId(id);

        Response response;
        try {
            response = networkListener.listen(new Request(new UpdateCommand(), new Object[] {person, user}));
        } catch (IOException e) {
            throw new AlertException("Server isn't available, try later", e);
        }

        return response;
    }

    public Response remove(long id, User user) throws AlertException {
        Response response;
        try {
            response = networkListener.listen(new Request(new RemoveByIdCommand(), new Object[] {id, user}));
        } catch (IOException e) {
            throw new AlertException("Server isn't available, try later", e);
        }

        return response;
    }

    public Response clear(User user) throws AlertException {
        //TODO где проверять на владельца?
        Response response;
        try {
            response = networkListener.listen(new Request(new ClearCommand(), new Object[] {user}));
        } catch (IOException e) {
            throw new AlertException("Server isn't available, try later", e);
        }

        return response;
    }
}
