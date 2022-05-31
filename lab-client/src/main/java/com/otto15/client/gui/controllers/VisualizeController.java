package com.otto15.client.gui.controllers;

import com.otto15.client.gui.models.TableModel;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.ResourceBundle;

public class VisualizeController extends AbstractController {

    @FXML
    private ImageView imageView;
    @FXML
    private Canvas canvas;

    private final TableModel tableModel;

    public VisualizeController(TableModel tableModel) {
        this.tableModel = tableModel;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        GraphicsContext graphicsContext = canvas.getGraphicsContext2D();

    }
}
