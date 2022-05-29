package com.otto15.client.gui.models;

import com.otto15.client.exceptions.AlertException;
import com.otto15.common.commands.SignUpCommand;
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
import java.util.Arrays;

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

    public Response login() throws AlertException, ValidationException {
        User user = UserValidator.validateUser(username.get(), password.get());

        Response response;
        try {
            response = networkListener.listen(new Request(
                    new SignInCommand(), new Object[]{user}
            ));
        } catch (IOException e) {
            throw new AlertException("Server isn't available, try later", e);
        }

        if (!response.isStatus()) {
            throw new ValidationException(Arrays.asList("Error when entering username/password", null));
        }
        return response;
    }

    public Response register() throws AlertException, ValidationException {
        User user = UserValidator.validateUser(username.get(), password.get(), repeatedPassword.get());

        Response response;
        try {
            response = networkListener.listen(new Request(
                    new SignUpCommand(), new Object[]{user}
            ));
        } catch (IOException e) {
            throw new AlertException("Server isn't available, try later", e);
        }

        if (!response.isStatus()) {
            throw new ValidationException(Arrays.asList("Such user already exist", null, null));
        }
        return response;

    }

}
