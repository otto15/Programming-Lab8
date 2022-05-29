package com.otto15.client.gui.controllers;

import com.otto15.client.exceptions.AlertException;
import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.TableModel;
import com.otto15.common.entities.Coordinates;
import com.otto15.common.entities.Location;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.User;
import com.otto15.common.state.PerformanceState;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Objects;
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

        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordinatesColumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        eyeColorColumn.setCellValueFactory(new PropertyValueFactory<>("eyeColor"));
        hairColorColumn.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        launchUpdatingPersons();
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

                List<Person> tablePersons = table.getItems().stream().sorted().toList();
                List<Person> serverPersons = tableModel.getPersons().stream().sorted().toList();
//                System.out.println(tablePersons.equals(serverPersons));
//                System.out.println();
                //TODO this ******* ****
                if (!table.getItems().sorted().equals(tableModel.getPersons().sorted())) {
                    updateIfChanged(tableModel.getPersons());
                }
            } catch (AlertException e) {
                e.showAlert();
            }
        }, delay, period, TimeUnit.MILLISECONDS);
    }

    public void updateIfChanged(ObservableList<Person> persons) {
        table.setItems(persons);
    }

    public void rightMouseClicked(Event event) {
        try {
            Person person = table.getSelectionModel().getSelectedItem();
            if (person != null && ((MouseEvent) event).getButton() == MouseButton.SECONDARY) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.TABLE_MENU_PATH.getPath())));
                loader.setControllerFactory(aClass -> new TableMenuController(person, user));

                ContextMenu contextMenu = loader.load();
                table.contextMenuProperty().bind(new SimpleObjectProperty<>(contextMenu));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
