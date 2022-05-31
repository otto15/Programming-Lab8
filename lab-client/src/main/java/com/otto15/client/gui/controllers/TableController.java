package com.otto15.client.gui.controllers;

import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.TableModel;
import com.otto15.common.entities.Coordinates;
import com.otto15.common.entities.Location;
import com.otto15.common.entities.Person;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.io.IOException;
import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class TableController extends AbstractController {
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
    @FXML
    private TextField authorFilter;
    @FXML
    private TextField idFilter;
    @FXML
    private TextField nameFilter;
    @FXML
    private TextField coordinatesFilter;
    @FXML
    private TextField heightFilter;
    @FXML
    private TextField eyeColorFilter;
    @FXML
    private TextField hairColorFilter;
    @FXML
    private TextField nationalityFilter;
    @FXML
    private TextField locationFilter;

    private final TableModel tableModel;

    public TableController(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        coordinatesColumn.setCellValueFactory(new PropertyValueFactory<>("coordinates"));
        heightColumn.setCellValueFactory(new PropertyValueFactory<>("height"));
        eyeColorColumn.setCellValueFactory(new PropertyValueFactory<>("eyeColor"));
        hairColorColumn.setCellValueFactory(new PropertyValueFactory<>("hairColor"));
        nationalityColumn.setCellValueFactory(new PropertyValueFactory<>("nationality"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));

        applyFilters();
    }

    public void applyFilters() {
        FilteredList<Person> filteredList = new FilteredList<>(tableModel.getPersons());
        authorFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> person.getAuthor().startsWith(newValue)));
        idFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> String.valueOf(person.getId()).startsWith(newValue)));
        nameFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> person.getName().startsWith(newValue)));
        coordinatesFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> person.getCoordinates().toString().startsWith(newValue)));
        heightFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> String.valueOf(person.getHeight()).startsWith(newValue)));
        eyeColorFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> (person.getEyeColor() == null ? "" : person.getEyeColor().name()).toLowerCase(Locale.ROOT)
                .startsWith(newValue.toLowerCase(Locale.ROOT))));
        hairColorFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> (person.getHairColor() == null ? "" : person.getHairColor().name()).toLowerCase(Locale.ROOT)
                .startsWith(newValue.toLowerCase(Locale.ROOT))));
        nationalityFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> person.getNationality().name().toLowerCase(Locale.ROOT)
                .startsWith(newValue.toLowerCase(Locale.ROOT))));
        locationFilter.textProperty().addListener((observableValue, oldValue, newValue)
                -> filteredList.setPredicate(person -> person.getLocation().toString().startsWith(newValue)));
        SortedList<Person> sortedList = new SortedList<>(filteredList);
        sortedList.comparatorProperty().bind(table.comparatorProperty());
        table.setItems(sortedList);
    }

    public void rightMouseClicked(Event event) {
        try {
            Person person = table.getSelectionModel().getSelectedItem();
            if (person != null && ((MouseEvent) event).getButton() == MouseButton.SECONDARY) {
                FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(getClass().getResource(Resources.TABLE_MENU_PATH.getPath())));
                loader.setControllerFactory(aClass -> new TableMenuController(person, tableModel.getUser()));

                ContextMenu contextMenu = loader.load();
                table.contextMenuProperty().bind(new SimpleObjectProperty<>(contextMenu));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
