package com.otto15.client.gui.controllers;

import com.otto15.common.entities.Coordinates;
import com.otto15.common.entities.Location;
import com.otto15.common.entities.Person;
import com.otto15.common.entities.enums.Color;
import com.otto15.common.entities.enums.Country;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

public class TableController extends AbstractController {
    private ObservableList<Person> persons;

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


        table.setItems(persons);
    }
}
