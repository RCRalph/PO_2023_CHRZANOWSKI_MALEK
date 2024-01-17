package agh.ics.oop.presenter;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.WorldElement;
import agh.ics.oop.model.element.gene.Gene;
import agh.ics.oop.model.map.Boundary;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationChangeListener;
import agh.ics.oop.simulation.SimulationStatistics;
import com.opencsv.CSVWriter;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;

import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class SimulationPresenter implements SimulationChangeListener {
    public static final int CELL_SIZE = 50;

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

    @FXML
    private Label currentDayLabel;

    @FXML
    private Label animalCountLabel;

    @FXML
    private Label plantCountLabel;

    @FXML
    private Label freeFieldLabel;

    @FXML
    private Label averageEnergyLabel;

    @FXML
    private Label averageLifespanLabel;

    @FXML
    private Label averageChildrenLabel;

    @FXML
    private GridPane popularGenomesGridPane;

    private Simulation simulation;

    private List<SimulationStatistics> simulationStatistics;

    private boolean saveToCSV;

    private UUID uuid;

    public void setSimulationEngine(Simulation simulation) {
        if (this.simulation != null) {
            this.simulation.unsubscribe(this);
            this.simulation.getEngine().stop();
        }

        this.simulation = simulation;
        this.simulation.subscribe(this);
        this.simulation.getEngine().initialize();
    }

    public void setSaveToCSV(boolean saveToCSV) {
        this.simulationStatistics = new ArrayList<>();
        this.saveToCSV = saveToCSV;
    }

    public void setUUID(UUID uuid) {
        this.uuid = uuid;
    }

    private double getCellSize(Boundary boundary) {
        return Math.min(
            (this.mapContent.getScene().getHeight() - 150) / (boundary.height() + 2),
            (this.mapContent.getScene().getWidth() - 450) / (boundary.width() + 2)
        );
    }

    private void addToGridPane(GridPane gridPane, ImageView imageView, int column, int row) {
        GridPane.setHalignment(imageView, HPos.CENTER);
        gridPane.add(imageView, column, row);
    }

    private void addToGridPane(GridPane gridPane, String text, int column, int row) {
        Label label = new Label(text);
        GridPane.setHalignment(label, HPos.CENTER);
        gridPane.add(label, column, row);
    }

    private void drawCoordinates(Boundary boundary, double cellSize) {
        this.addToGridPane(this.mapContent, "y\\x", 0, 0);
        this.mapContent.getColumnConstraints().add(new ColumnConstraints(cellSize));
        this.mapContent.getRowConstraints().add(new RowConstraints(cellSize));

        int horizontalCoordinateCount = boundary.upperRightCorner().x() - boundary.lowerLeftCorner().x() + 1,
            verticalCoordinateCount = boundary.upperRightCorner().y() - boundary.lowerLeftCorner().y() + 1;

        for (int i = 0; i < horizontalCoordinateCount; i++) {
            this.mapContent.getColumnConstraints().add(new ColumnConstraints(cellSize));
            this.addToGridPane(
                this.mapContent,
                Integer.toString(i + boundary.lowerLeftCorner().x()),
                i + 1,
                0
            );
        }

        for (int i = 0; i < verticalCoordinateCount; i++) {
            this.mapContent.getRowConstraints().add(new RowConstraints(cellSize));
            this.addToGridPane(
                this.mapContent,
                Integer.toString(boundary.upperRightCorner().y() - i),
                0,
                i + 1
            );
        }
    }

    private void drawMap(WorldMap map) {
        this.mapContent.getChildren().retainAll(this.mapContent.getChildren().get(0));
        this.mapContent.getColumnConstraints().clear();
        this.mapContent.getRowConstraints().clear();

        Boundary boundary = map.getCurrentBounds();
        double cellSize = this.getCellSize(boundary);

        this.drawCoordinates(boundary, cellSize);

        for (int r = boundary.upperRightCorner().y(); r >= boundary.lowerLeftCorner().y(); r--) {
            for (int c = boundary.lowerLeftCorner().x(); c <= boundary.upperRightCorner().x(); c++) {
                Vector2D position = new Vector2D(c, r);

                if (map.isOccupied(position)) {
                    for (WorldElement element : map.objectsAt(position)) {
                        element.setImageViewSize(cellSize);

                        this.addToGridPane(
                            this.mapContent,
                            element.getImageView(),
                            c + 1 - boundary.lowerLeftCorner().x(),
                            boundary.upperRightCorner().y() - r + 1
                        );
                    }
                }
            }
        }
    }

    private void updateStatistics(SimulationStatistics statistics) {
        this.simulationStatistics.add(statistics);

        this.currentDayLabel.setText(Integer.toString(statistics.day()));
        this.animalCountLabel.setText(Integer.toString(statistics.animalCount()));
        this.plantCountLabel.setText(Integer.toString(statistics.plantCount()));
        this.freeFieldLabel.setText(Long.toString(statistics.freeFieldCount()));
        this.averageEnergyLabel.setText(Double.toString(statistics.averageEnergyLevel()));
        this.averageLifespanLabel.setText(Double.toString(statistics.averageLifespan()));
        this.averageChildrenLabel.setText(Double.toString(statistics.averageChildren()));

        this.popularGenomesGridPane.getChildren().clear();

        List<Map.Entry<List<Gene>, Integer>> orderedGenomes = statistics.getOrderedGenomes();

        for (int row = 0; row < orderedGenomes.size(); row++) {
            this.addToGridPane(
                this.popularGenomesGridPane,
                Gene.listToString(orderedGenomes.get(row).getKey()),
                0,
                row
            );

            this.addToGridPane(
                this.popularGenomesGridPane,
                orderedGenomes.get(row).getValue().toString(),
                1,
                row
            );
        }
    }

    @Override
    public void simulationChanged(String message, WorldMap map) {
        this.simulationChanged(message);

        Platform.runLater(() -> this.drawMap(map));
    }

    @Override
    public void simulationChanged(String message, SimulationStatistics statistics) {
        this.simulationChanged(message);

        Platform.runLater(() -> this.updateStatistics(statistics));
    }

    @Override
    public void simulationChanged(String message) {
        Platform.runLater(() -> this.mapMessage.setText(message));
    }

    private void exportStatisticsToCSV() {
        try (CSVWriter writer = new CSVWriter(new FileWriter(String.format("%s.csv", this.uuid)))) {
            writer.writeNext(new String[] {
                "Day",
                "Animal count",
                "Plant count",
                "Free field count",
                "Average energy level",
                "Average lifespan",
                "Average children",
                "Genome popularity"
            });

            for (SimulationStatistics statistics : this.simulationStatistics) {
                writer.writeNext(new String[] {
                    Integer.toString(statistics.day()),
                    Integer.toString(statistics.animalCount()),
                    Integer.toString(statistics.plantCount()),
                    Long.toString(statistics.freeFieldCount()),
                    Double.toString(statistics.averageEnergyLevel()),
                    Double.toString(statistics.averageLifespan()),
                    Double.toString(statistics.averageChildren()),
                    statistics.getGenomePopularityString()
                });
            }
        } catch (IOException exception) {
            this.mapMessage.setText(exception.toString());
        }
    }

    @FXML
    private void onStartButtonAction() {
        this.startButton.setDisable(true);
        this.pauseButton.setDisable(false);
        this.stopButton.setDisable(false);

        this.simulation.getEngine().start();
    }

    @FXML
    private void onPauseButtonAction() {
        this.startButton.setDisable(false);
        this.pauseButton.setDisable(true);
        this.stopButton.setDisable(false);

        try {
            this.simulation.getEngine().pause();
        } catch (InterruptedException ignored) {}
    }

    @FXML
    private void onStopButtonAction() {
        this.startButton.setDisable(true);
        this.pauseButton.setDisable(true);
        this.stopButton.setDisable(true);

        this.simulation.getEngine().stop();

        if (this.saveToCSV) {
            this.exportStatisticsToCSV();
        }
    }
}
