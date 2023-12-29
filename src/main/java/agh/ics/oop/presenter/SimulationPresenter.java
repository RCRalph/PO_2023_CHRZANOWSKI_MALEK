package agh.ics.oop.presenter;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.WorldElement;
import agh.ics.oop.model.map.Boundary;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationChangeListener;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.net.URL;
import java.util.ResourceBundle;

public class SimulationPresenter implements SimulationChangeListener, Initializable {
    public static final int CELL_SIZE = 75;

    @FXML
    private Label mapMessage;

    @FXML
    private GridPane mapContent;

    @FXML
    private Button startButton;

    @FXML
    private Button pauseButton;

    @FXML
    private Button stopButton;

    private Simulation simulation;

    private SimulationEngine simulationEngine;

    public void setSimulation(Simulation simulation) {
        if (this.simulation != null) {
            this.simulation.unsubscribe(this);

            try {
                this.simulationEngine.stop();
            } catch (InterruptedException ignored) {}
        }

        this.simulation = simulation;
        this.simulation.subscribe(this);
        this.simulation.initialize();
        this.simulationEngine = new SimulationEngine(this.simulation);
    }

    private void addToGridPane(ImageView imageView, int column, int row) {
        GridPane.setHalignment(imageView, HPos.CENTER);
        this.mapContent.add(imageView, column, row);
    }

    private void addToGridPane(String text, int column, int row) {
        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        this.mapContent.add(label, column, row);
    }

    private void drawCoordinates(Boundary boundary) {
        this.addToGridPane("y\\x", 0, 0);
        this.mapContent.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        this.mapContent.getRowConstraints().add(new RowConstraints(CELL_SIZE));

        int horizontalCoordinateCount = boundary.upperRightCorner().x() - boundary.lowerLeftCorner().x() + 1,
            verticalCoordinateCount = boundary.upperRightCorner().y() - boundary.lowerLeftCorner().y() + 1;

        for (int i = 0; i < horizontalCoordinateCount; i++) {
            this.mapContent.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
            this.addToGridPane(Integer.toString(i + boundary.lowerLeftCorner().x()), i + 1, 0);
        }

        for (int i = 0; i < verticalCoordinateCount; i++) {
            this.mapContent.getRowConstraints().add(new RowConstraints(CELL_SIZE));
            this.addToGridPane(Integer.toString(boundary.upperRightCorner().y() - i), 0, i + 1);
        }
    }

    private void drawMap(WorldMap map) {
        // Clear all WorldElements from map
        this.mapContent.getChildren().retainAll(this.mapContent.getChildren().get(0));
        this.mapContent.getColumnConstraints().clear();
        this.mapContent.getRowConstraints().clear();

        Boundary boundary = map.getCurrentBounds();
        this.drawCoordinates(boundary);

        for (int r = boundary.upperRightCorner().y(); r >= boundary.lowerLeftCorner().y(); r--) {
            for (int c = boundary.lowerLeftCorner().x(); c <= boundary.upperRightCorner().x(); c++) {
                Vector2D position = new Vector2D(c, r);

                if (map.isOccupied(position)) {
                    for (WorldElement element : map.objectsAt(position)) {
                        this.addToGridPane(
                            element.getImageView(),
                            c + 1 - boundary.lowerLeftCorner().x(),
                            boundary.upperRightCorner().y() - r + 1
                        );
                    }
                }
            }
        }
    }

    @Override
    public void simulationMapChanged(WorldMap map, String message) {
        Platform.runLater(() -> {
            this.mapMessage.setText(message);
            this.drawMap(map);
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.startButton.setOnAction(event -> {
            this.startButton.setDisable(true);
            this.pauseButton.setDisable(false);
            this.stopButton.setDisable(false);

            this.simulationEngine.start();
        });

        this.pauseButton.setOnAction(event -> {
            this.startButton.setDisable(false);
            this.pauseButton.setDisable(true);
            this.stopButton.setDisable(false);

            this.simulationEngine.pause();
        });

        this.stopButton.setOnAction(event -> {
            this.startButton.setDisable(true);
            this.pauseButton.setDisable(true);
            this.stopButton.setDisable(true);

            try {
                this.simulationEngine.stop();
            } catch (InterruptedException ignored) {}
        });
    }
}
