package com.otto15.client.gui.models;

import com.otto15.client.ConnectionHandler;
import com.otto15.client.exceptions.AlertException;
import com.otto15.client.listeners.ClientNetworkListener;
import com.otto15.common.commands.ShowCommand;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.network.NetworkListener;
import com.otto15.common.network.Request;
import com.otto15.common.network.Response;
import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.beans.property.ListProperty;
import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
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
        launchUpdatingPersons();
    }

    public void getNewCollection() throws AlertException {
        Response response;
        try {
            response = networkListener.listen(new Request(new ShowCommand(), new Object[]{user}));
            Platform.runLater(() -> updateCollection(response.getPersons()));
        } catch (IOException e) {
            throw new AlertException("Server isn't available, try later");
        }
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
            } catch (AlertException e) {
                Platform.runLater(e::showAlert);
            }
            if (executor.isShutdown()) {
                ConnectionHandler.getInstance().close();
            }
        }, delay, period, TimeUnit.MILLISECONDS);
    }

    public void updateCollection(Collection<Person> newPersonsList) {
        for (Person person : newPersonsList) {
            if (!persons.contains(person)) {
                persons.add(person);
            } else {
                Person personFromTableList = persons.stream().filter((p) -> Objects.equals(p.getId(), person.getId())).toList().get(0);
                if (!person.equals(personFromTableList)) {
                    persons.remove(personFromTableList);
                    persons.add(person);
                }
            }
        }
//        persons.setValue(persons.get());
        persons.removeIf(person -> !newPersonsList.contains(person));
    }

    public ObservableList<Person> getPersons() {
        return persons.get();
    }

    public ListProperty<Person> personsProperty() {
        return persons;
    }

    public User getUser() {
        return user;
    }
}
