package com.otto15.client.gui.models;

import com.otto15.client.exceptions.LostConnectionException;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.ShowCommand;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.state.PerformanceState;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TableModel {

    private final User user;
    private final NetworkListener networkListener;
    private ListProperty<Person> persons;


    {

        networkListener = ClientNetworkListener.getInstance();
        persons = new SimpleListProperty<>(FXCollections.observableArrayList());

    }

    public TableModel(User user) {
        this.user = user;
    }

    public void launchUpdatingPersons() {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        PerformanceState performanceState = PerformanceState.getInstance();

        long delay = 0;
        long period = 5000L;
        executor.scheduleAtFixedRate(() -> {
            if (!performanceState.getPerformanceStatus()) {
                executor.shutdown();
            }
            try {
                getNewCollection();
            } catch (LostConnectionException e) {
                e.showAlert();
            }
        }, delay, period, TimeUnit.MILLISECONDS);
    }

    public void getNewCollection() throws LostConnectionException {
        Response response;
        try {
            response = networkListener.listen(new Request(new ShowCommand(), new Object[]{user}));
            persons = new SimpleListProperty<>(FXCollections.observableArrayList(response.getPersons()));
        } catch (IOException e) {
            throw new LostConnectionException("Server isn't available, try later");
        }
    }

    public ObservableList<Person> getPersons() {
        return persons.get();
    }

    public ListProperty<Person> personsProperty() {
        return persons;
    }
}
