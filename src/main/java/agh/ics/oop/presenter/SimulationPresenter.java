package agh.ics.oop.presenter;

import agh.ics.oop.InvalidSimulationConfigurationException;
import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationEngine;
import agh.ics.oop.SimulationParameters;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.map.Boundary;
import agh.ics.oop.model.map.MapChangeListener;
import agh.ics.oop.model.map.WorldMap;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.util.List;

public class SimulationPresenter implements MapChangeListener {
    @FXML
    private Label mapMessage;

    @FXML
    private TextField moveInput;

    @FXML
    private GridPane mapContent;

    @FXML
    private Button startButton;

    private WorldMap map;

    public void setWorldMap(WorldMap map) {
        if (this.map != null) {
            this.map.unsubscribe(this);
        }

        this.map = map;
        this.map.subscribe(this);
    }

    private void addToGridPane(String text, int column, int row) {
        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        this.mapContent.add(label, column, row);
    }

    private void drawCoordinates(Boundary boundary) {
        this.addToGridPane("y\\x", 0, 0);
        this.mapContent.getColumnConstraints().add(new ColumnConstraints(25));
        this.mapContent.getRowConstraints().add(new RowConstraints(25));

        int horizontalCoordinateCount = boundary.upperRightCorner().x() - boundary.lowerLeftCorner().x() + 1,
            verticalCoordinateCount = boundary.upperRightCorner().y() - boundary.lowerLeftCorner().y() + 1;

        for (int i = 0; i < horizontalCoordinateCount; i++) {
            this.mapContent.getColumnConstraints().add(new ColumnConstraints(25));
            this.addToGridPane(Integer.toString(i + boundary.lowerLeftCorner().x()), i + 1, 0);
        }

        for (int i = 0; i < verticalCoordinateCount; i++) {
            this.mapContent.getRowConstraints().add(new RowConstraints(25));
            this.addToGridPane(Integer.toString(boundary.upperRightCorner().y() - i), 0, i + 1);
        }
    }

    private void drawMap(String message) {
        this.mapMessage.setText(message);

        // Clear all WorldElements from map
        this.mapContent.getChildren().retainAll(this.mapContent.getChildren().get(0)); // hack to retain visible grid lines
        this.mapContent.getColumnConstraints().clear();
        this.mapContent.getRowConstraints().clear();

        Boundary boundary = this.map.getCurrentBounds();
        this.drawCoordinates(boundary);

        for (int r = boundary.upperRightCorner().y(); r >= boundary.lowerLeftCorner().y(); r--) {
            for (int c = boundary.lowerLeftCorner().x(); c <= boundary.upperRightCorner().x(); c++) {
                Vector2D position = new Vector2D(c, r);

                if (this.map.isOccupied(position)) {
                    this.addToGridPane(
                        "x",//this.map.objectAt(position).toString(),
                        c + 1 - boundary.lowerLeftCorner().x(),
                        boundary.upperRightCorner().y() - r + 1
                    );
                }
            }
        }
    }

    @Override
    public void mapChanged(WorldMap worldMap, String message) {
        Platform.runLater(() -> this.drawMap(message));
    }

    @FXML
    private void onSimulationStartClicked(ActionEvent ignored) {
        SimulationParameters simulationParameters = new SimulationParameters(
            "Non-editable config",
            0,
            0,
            "Planet",
            0,
            0,
            0,
            0,
            "Slight correction",
            0,
            0,
            0,
            "Forested Equator",
            0,
            0,
            0,
            0,
            0,
            "Full predestination"
        );

        try {
            Simulation simulation = new Simulation(simulationParameters);

            SimulationEngine simulationEngine = new SimulationEngine(List.of(simulation));
            simulationEngine.runAsync();

            this.moveInput.setDisable(true);
            this.startButton.setDisable(true);
        } catch (InvalidSimulationConfigurationException exception) {
            this.drawMap(exception.getMessage());
        }
    }
}
