package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.LostConnectionException;
import com.otto15.client.gui.models.TableModel;
import com.otto15.common.entities.Coordinates;
import com.otto15.common.entities.Location;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.state.PerformanceState;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.Collections;
import java.util.ResourceBundle;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TableController extends AbstractController {

    private final User user;

    @FXML
    private TableView<Person> table;
    @FXML
    private TableColumn<Person, String> authorColumn;
    @FXML
    private TableColumn<Person, Long> idColumn;
    @FXML
    private TableColumn<Person, String> nameColumn;
    @FXML
    private TableColumn<Person, Coordinates> coordinatesColumn;
    @FXML
    private TableColumn<Person, Long> heightColumn;
    @FXML
    private TableColumn<Person, String> eyeColorColumn;
    @FXML
    private TableColumn<Person, String> hairColorColumn;
    @FXML
    private TableColumn<Person, String> nationalityColumn;
    @FXML
    private TableColumn<Person, Location> locationColumn;

    TableModel tableModel;

    public TableController(User user) {
        this.user = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        tableModel = new TableModel(user);

        launchUpdatingPersons();

        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordinatesColumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        eyeColorColumn.setCellValueFactory(new PropertyValueFactory<>("eyeColor"));
        hairColorColumn.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
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
                tableModel.getNewCollection();
                if (!) {
                    updateIfChanged(tableModel.getPersons());
                }
            } catch (LostConnectionException e) {
                e.showAlert();
            }
        }, delay, period, TimeUnit.MILLISECONDS);
    }

    public void updateIfChanged(ObservableList<Person> persons) {
        table.setItems(persons);
    }
}
