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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class VisualizeController extends AbstractController {

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private ImageView imageView;

    private final TableModel tableModel;
    private final List<Canvas> peoples;
    private static final Map<String, Color> colors = new HashMap<>();

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
        if (!colors.containsKey(person.getAuthor())) {
//            System.out.println(person.getAuthor());
//            System.out.println(colors.keySet());
            colors.put(person.getAuthor(), Color.color(Math.random(), Math.random(), Math.random()));
        }
        graphicsContext.setFill(colors.get(person.getAuthor()));
        graphicsContext.setStroke(colors.get(person.getAuthor()));
        graphicsContext.setLineWidth(3);

        graphicsContext.strokeLine(7, 15, 7, 30);
        //legs
        graphicsContext.strokeLine( 0, 35, 7, 30);
        graphicsContext.strokeLine(14,35, 7, 30);

        //hands
        graphicsContext.strokeLine(0, 13, 7,20);
        graphicsContext.strokeLine( 14, 13, 7,20);

        //head
        graphicsContext.strokeOval(2, 2, 10, 10);

        if (person.getEyeColor() != null) {
            switch (person.getEyeColor()) {
                case RED -> graphicsContext.setFill(Color.RED);
                case BLUE -> graphicsContext.setFill(Color.BLUE);
                case BLACK -> graphicsContext.setFill(Color.BLACK);
                case BROWN -> graphicsContext.setFill(Color.BROWN);
                case GREEN -> graphicsContext.setFill(Color.GREEN);
                case WHITE -> graphicsContext.setFill(Color.WHITE);
                case ORANGE -> graphicsContext.setFill(Color.ORANGE);
                case YELLOW -> graphicsContext.setFill(Color.YELLOW);
            }
            graphicsContext.fillOval(3, 3, 7, 7);
        }
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
