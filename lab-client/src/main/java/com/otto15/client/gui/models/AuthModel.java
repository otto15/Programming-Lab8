package com.otto15.client.gui.models;

import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.SignInCommand;
import com.otto15.common.commands.SignUpCommand;

import com.otto15.common.entities.User;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.io.IOException;

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


        //TODO validation

        return networkListener.listen(new Request(
                new SignInCommand(), new Object[]{user}
        ));
    }

    public Response register() throws IOException {
        User user = new User(username.get(), password.get());

        //TODO validation

        return networkListener.listen(new Request(
                new SignUpCommand(), new Object[]{user}
        ));
    }

}
