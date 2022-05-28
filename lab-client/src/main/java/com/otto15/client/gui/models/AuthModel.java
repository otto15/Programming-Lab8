package com.otto15.client.gui.models;

import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.SignInCommand;
import com.otto15.common.commands.SignUpCommand;

import com.otto15.common.entities.User;
import com.otto15.common.entities.validators.UserValidator;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.util.Pair;

import java.io.IOException;
import java.util.List;

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

    public Response login() throws IOException {
        User user = new User(username.get(), password.get());

        List<Exception> validationErrorsList = UserValidator.validateUser(user);

        Response response = null;
        if (validationErrorsList.size() == 0) {
            response = networkListener.listen(new Request(
                    new SignInCommand(), new Object[]{user}
            ));
        }

        return response;
    }

    public Response register() throws IOException {
        User user = new User(username.get(), password.get());
        Response response = null;

        List<Exception> validationErrorsList = UserValidator.validateUser(user);

        if (!repeatedPassword.equals(password)) {
            validationErrorsList.add(new IllegalArgumentException(""));
        }


        return networkListener.listen(new Request(
                new SignUpCommand(), new Object[]{user}
        ));
    }

}
