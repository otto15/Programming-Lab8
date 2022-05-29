package com.otto15.client.gui.models;

import com.otto15.client.exceptions.LostConnectionException;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.AddCommand;
import com.otto15.common.commands.SignInCommand;
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
    //    private final ObjectProperty<Coordinates> coordinates;
    private final StringProperty height;
    private final ObjectProperty<Color> eyeColor;
    private final ObjectProperty<Color> hairColor;
    private final ObjectProperty<Country> nationality;
    private final StringProperty xLocation;
    private final StringProperty yLocation;
    private final StringProperty zLocation;
//    private final ObjectProperty<Location> location;

    private final NetworkListener networkListener;

    {
        networkListener = ClientNetworkListener.getInstance();
        name = new SimpleStringProperty();
        xCoordinates = new SimpleStringProperty();
        yCoordinates = new SimpleStringProperty();
//        coordinates = new SimpleObjectProperty<>();
        height = new SimpleStringProperty();
        eyeColor = new SimpleObjectProperty<>();
        hairColor = new SimpleObjectProperty<>();
        nationality = new SimpleObjectProperty<>();
//        location = new SimpleObjectProperty<>();
        xLocation = new SimpleStringProperty();
        yLocation = new SimpleStringProperty();
        zLocation = new SimpleStringProperty();
    }

    public String getName() {
        return name.get();
    }

    public StringProperty nameProperty() {
        return name;
    }

    public String getxCoordinates() {
        return xCoordinates.get();
    }

    public StringProperty xCoordinatesProperty() {
        return xCoordinates;
    }

    public String getyCoordinates() {
        return yCoordinates.get();
    }

    public StringProperty yCoordinatesProperty() {
        return yCoordinates;
    }

    public String getHeight() {
        return height.get();
    }

    public StringProperty heightProperty() {
        return height;
    }

    public Color getEyeColor() {
        return eyeColor.get();
    }

    public ObjectProperty<Color> eyeColorProperty() {
        return eyeColor;
    }

    public Color getHairColor() {
        return hairColor.get();
    }

    public ObjectProperty<Color> hairColorProperty() {
        return hairColor;
    }

    public Country getNationality() {
        return nationality.get();
    }

    public ObjectProperty<Country> nationalityProperty() {
        return nationality;
    }

    public String getxLocation() {
        return xLocation.get();
    }

    public StringProperty xLocationProperty() {
        return xLocation;
    }

    public String getyLocation() {
        return yLocation.get();
    }

    public StringProperty yLocationProperty() {
        return yLocation;
    }

    public String getzLocation() {
        return zLocation.get();
    }

    public StringProperty zLocationProperty() {
        return zLocation;
    }

    public Response add(User user) throws ValidationException, LostConnectionException {
        Person person = PersonValidator.validatePersonFromString(name.get(), xCoordinates.get(), yCoordinates.get(), height.get(), eyeColor.get(), hairColor.get(), nationality.get(), xLocation.get(), yLocation.get(), zLocation.get());
        System.out.println(person);
        Response response;
        try {
            response = networkListener.listen(new Request(new AddCommand(), new Object[] {person, user}));
        } catch (IOException e) {
            throw new LostConnectionException("Server isn't available, try later");
        }

        return response;
    }
}
