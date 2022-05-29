package com.otto15.client.gui.models;

import com.otto15.client.exceptions.LostConnectionException;
import com.otto15.common.exceptions.ValidationException;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.SignInCommand;

import com.otto15.common.entities.User;
import com.otto15.common.entities.validators.UserValidator;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class AuthModel {

    private final StringProperty username;
    private final StringProperty password;
    private final StringProperty repeatedPassword;



    private final NetworkListener networkListener;

    {
        networkListener = ClientNetworkListener.getInstance();
        username = new SimpleStringProperty();
        password = new SimpleStringProperty();
        repeatedPassword = new SimpleStringProperty();
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public StringProperty passwordProperty() {
        return password;
    }

    public StringProperty repeatedPasswordProperty() {
        return repeatedPassword;
    }

    public Response login() throws LostConnectionException, ValidationException {
        User user = new User(username.get(), password.get());


        List<String> validationErrorsList = UserValidator.validateUser(user);
        if (validationErrorsList.stream().anyMatch(Objects::nonNull)) {
            throw new ValidationException(validationErrorsList);
        }

        Response response;
        try {
            response = networkListener.listen(new Request(
                    new SignInCommand(), new Object[]{user}
            ));
        } catch (IOException e) {
            throw new LostConnectionException("Server isn't available, try later");
        }

        if (!response.isStatus()) {
            validationErrorsList.set(0, "Error when entering username/password");
            throw new ValidationException(validationErrorsList);
        }
        return response;
    }

    public Response register() throws LostConnectionException, ValidationException {
        User user = new User(username.get(), password.get());

        List<String> validationErrorsList = UserValidator.validateUser(user);
        if (validationErrorsList.stream().anyMatch(Objects::nonNull)) {
            throw new ValidationException(validationErrorsList);
        }
        //TODO make registration validation
        if (!password.get().equals(repeatedPassword.get())) {
            validationErrorsList.set(1, "Passwords must be the same");
            throw new ValidationException(validationErrorsList);
        }

        Response response;
        try {
            response = networkListener.listen(new Request(
                    new SignInCommand(), new Object[]{user}
            ));
        } catch (IOException e) {
            throw new LostConnectionException("Server isn't available, try later");
        }

        if (!response.isStatus()) {
            validationErrorsList.set(0, "Such user already exist");
            throw new ValidationException(validationErrorsList);
        }
        return response;

    }

}
