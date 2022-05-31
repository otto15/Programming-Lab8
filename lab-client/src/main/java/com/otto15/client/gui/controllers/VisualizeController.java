package com.otto15.client.gui.controllers;

import com.otto15.client.gui.Resources;
import com.otto15.client.gui.models.TableModel;
import com.otto15.common.entities.Coordinates;
import com.otto15.common.entities.Person;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class VisualizeController extends AbstractController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageView;

    private final TableModel tableModel;
    private final List<Canvas> peoples;

    public VisualizeController(TableModel tableModel) {
        this.tableModel = tableModel;
        peoples = new ArrayList<>();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableModel.getPersons().forEach(this::drawPerson);
        tableModel.personsProperty().addListener((observableValue, oldList, newList) -> {
            peoples.forEach(canvas1 -> {
                GraphicsContext graphicsContext = canvas1.getGraphicsContext2D();
                graphicsContext.clearRect(0, 0, canvas1.getWidth(), canvas1.getHeight());
            });
            peoples.clear();
            newList.forEach(this::drawPerson);
        });
    }

    public void drawPerson(Person person) {
        //invertirovannoe govno
        Coordinates coordinates = person.getCoordinates();

        Canvas personCanvas = new Canvas(14, 39);
        personCanvas.setLayoutX(calculateX(coordinates.getX() / 5));
        personCanvas.setLayoutY(calculateY(coordinates.getY() / 5));

        GraphicsContext graphicsContext = personCanvas.getGraphicsContext2D();
        graphicsContext.setFill(Color.BLACK);
        graphicsContext.setStroke(Color.BLACK);
        graphicsContext.setLineWidth(3);

        graphicsContext.strokeLine(7, 11, 7, 21);
        //legs
        graphicsContext.strokeLine( 0, 30, 7, 21);
        graphicsContext.strokeLine(14,30, 7, 21);

        //hands
        graphicsContext.strokeLine(0, 5, 7,11);
        graphicsContext.strokeLine( 14, 5, 7,11);

        //head
        graphicsContext.strokeOval(- 5, - 24, 10, 10);
//        graphicsContext.strokeLine(calculateX(coordinates.getX()), calculateY(coordinates.getY() - 11), calculateX(coordinates.getX()), calculateY(coordinates.getY() + 10));
//        //legs
//        graphicsContext.strokeLine(calculateX(coordinates.getX() - 7), calculateY(coordinates.getY() + 15), calculateX(coordinates.getX()), calculateY(coordinates.getY() + 10));
//        graphicsContext.strokeLine(calculateX(coordinates.getX() + 7), calculateY(coordinates.getY() + 15), calculateX(coordinates.getX()), calculateY(coordinates.getY() + 10));
//
//        //hands
//        graphicsContext.strokeLine(calculateX(coordinates.getX() - 7), calculateY(coordinates.getY() - 10), calculateX(coordinates.getX()), calculateY(coordinates.getY() - 6));
//        graphicsContext.strokeLine(calculateX(coordinates.getX() + 7), calculateY(coordinates.getY() - 10), calculateX(coordinates.getX()), calculateY(coordinates.getY() - 6));
//
//        //head
//        graphicsContext.strokeOval(calculateX(coordinates.getX() - 5), calculateY(coordinates.getY() - 24), 10, 10);




        personCanvas.setOnMouseEntered(event -> {
            personCanvas.setScaleX(1.07);
            personCanvas.setScaleY(1.07);
        });
        personCanvas.setOnMouseExited(event -> {
            personCanvas.setScaleX(1);
            personCanvas.setScaleY(1);
        });
        personCanvas.setOnMouseClicked(event -> {
            openPopupWindow(event, Resources.INFO_PATH, aClass -> new InfoController(person, tableModel.getUser()));
        });

        FadeTransition fade = new FadeTransition();
        fade.setDuration(Duration.millis(1500));
        fade.setFromValue(0);
        fade.setToValue(10);
        fade.setNode(personCanvas);
        fade.play();

        anchorPane.getChildren().add(personCanvas);
        peoples.add(personCanvas);
    }

    public double calculateX(double x) {
        return (x + imageView.getFitWidth() / 2);
    }

    public double calculateY(double y) {
        return (y + imageView.getFitHeight() / 2);
    }
}
