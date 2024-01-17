package agh.ics.oop.presenter;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.WorldElement;
import agh.ics.oop.model.map.Boundary;
import agh.ics.oop.model.map.WorldMap;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationChangeListener;
import agh.ics.oop.simulation.SimulationEngine;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.geometry.HPos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class SimulationPresenter implements SimulationChangeListener {
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

    private SimulationEngine simulationEngine;

    private boolean saveToCSV;

    @FXML
    private Label followingGenotype;

    @FXML
    private Label followingActivatedGene;

    @FXML
    private Label followingEnergyLevel;

    @FXML
    private Label followingPlantsEaten;

    @FXML
    private Label followingChildrenCount;

    @FXML
    private Label followingDescendantCount;

    @FXML
    private Label followingDaysAlive;

    @FXML
    private Label followingDeathday;

    @FXML
    private Button dominatingGenotype;

    @FXML
    private Button preferredPlantFields;

    private Animal followedAnimal;

    private boolean seeDesirablePositions = false;

    private boolean seeDominatingGenotype = false;

    public void setSimulationEngine(Simulation simulation) {
        if (this.simulationEngine != null) {
            this.simulationEngine.unsubscribe(this);
            this.simulationEngine.stop();
        }

        this.simulationEngine = simulation.getEngine();
        this.simulationEngine.subscribe(this);
        this.simulationEngine.initialize();
    }

    public void setSaveToCSV(boolean saveToCSV) {
        this.saveToCSV = saveToCSV;
    }

    private void addToGridPane(ImageView imageView, int column, int row, String styleClass) {
        GridPane.setHalignment(imageView, HPos.CENTER);
        imageView.getStyleClass().clear();
        imageView.getStyleClass().add(styleClass);
//        imageView.setStyle("-fx-border-color: " + getRGBString(borderColor)+";"+"-fx-border-width: 100px");
        this.mapContent.add(imageView, column, row);
    }

    private void addToGridPane(String text, int column, int row, String styleClass) {
        Label label = new Label(text);
        label.getStyleClass().clear();
        label.getStyleClass().add(styleClass);
//        label.setStyle("-fx-border-color: " + getRGBString(borderColor));
        GridPane.setHalignment(label, HPos.CENTER);
        this.mapContent.add(label, column, row);
    }

    private String getRGBString(Color color){
        double r = color.getRed();
        double g = color.getGreen();
        double b = color.getBlue();
        return String.format("rgb(%f, %f, %f)", r,g,b);
    }


    private void drawCoordinates(Boundary boundary) {
        this.addToGridPane("y\\x", 0, 0, "coordinateLabel");
        this.mapContent.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
        this.mapContent.getRowConstraints().add(new RowConstraints(CELL_SIZE));

        int horizontalCoordinateCount = boundary.upperRightCorner().x() - boundary.lowerLeftCorner().x() + 1,
            verticalCoordinateCount = boundary.upperRightCorner().y() - boundary.lowerLeftCorner().y() + 1;

        for (int i = 0; i < horizontalCoordinateCount; i++) {
            this.mapContent.getColumnConstraints().add(new ColumnConstraints(CELL_SIZE));
            this.addToGridPane(Integer.toString(i + boundary.lowerLeftCorner().x()), i + 1, 0, "coordinateLabel");
        }
        for (int i = 0; i < verticalCoordinateCount; i++) {
            this.mapContent.getRowConstraints().add(new RowConstraints(CELL_SIZE));
            this.addToGridPane(Integer.toString(boundary.upperRightCorner().y() - i), 0, i + 1, "coordinateLabel");
        }
    }

    private void clearGrid() {
        this.mapContent.getChildren().retainAll(this.mapContent.getChildren().get(0));
        this.mapContent.getColumnConstraints().clear();
        this.mapContent.getRowConstraints().clear();
    }

    private void drawMap(WorldMap map) {
        this.clearGrid();

        Boundary boundary = map.getCurrentBounds();
        this.drawCoordinates(boundary);
        for (int r = boundary.upperRightCorner().y(); r >= boundary.lowerLeftCorner().y(); r--) {
            for (int c = boundary.lowerLeftCorner().x(); c <= boundary.upperRightCorner().x(); c++) {
                Vector2D position = new Vector2D(c, r);
                String styleClass = "tileBorder";
                if (map.isOccupied(position)) {
                    if (!Objects.isNull(followedAnimal) && followedAnimal.isAt(position)){
                        styleClass = "highlightFollowed";
                    }
                    if (seeDesirablePositions && map.getDesirablePositions().contains(position)){
                        styleClass = "highlightPlants";
                    }
                    for (WorldElement element : map.objectsAt(position)) {
                        this.addToGridPane(
                            element.getImageView(),
                            c + 1 - boundary.lowerLeftCorner().x(),
                            boundary.upperRightCorner().y() - r + 1,
                                styleClass
                        );
                    }
                }
            }
        }
    }

    @Override
    public void simulationChanged(WorldMap map, String message, int currentDay) {
        this.simulationChanged(message);
        this.simulationChanged(currentDay);
        Platform.runLater(() -> this.drawMap(map));
    }

    @Override
    public void simulationChanged(String message) {
        Platform.runLater(() -> this.mapMessage.setText(message));
    }

    @Override
    public void simulationChanged(Animal followedAnimal){
        this.followedAnimal = followedAnimal;
        // add highlight
    }

    private void setFollowedStatistics(int currentDay){
        if (!Objects.isNull(this.followedAnimal)) {
            if (this.followedAnimal.getDeathDay() < 0) {
                this.followingActivatedGene.setText(this.followedAnimal.getCurrentGene().toString());
                this.followingDaysAlive.setText(String.valueOf(currentDay - this.followedAnimal.getBirthday()));
                this.followingEnergyLevel.setTextFill(Color.BLACK);
                this.followingEnergyLevel.setText(String.valueOf(this.followedAnimal.getEnergyLevel()));
                this.followingChildrenCount.setText(String.valueOf(this.followedAnimal.getChildCount()));
                this.followingPlantsEaten.setText(String.valueOf(this.followedAnimal.getPlantsEaten()));
                this.followingGenotype.setText(this.followedAnimal.getGenomeString());
                // add descendant count
            } else {
                this.followingDeathday.setText(String.valueOf(this.followedAnimal.getDeathDay()));
                this.followingEnergyLevel.setText("0");
                this.followingEnergyLevel.setTextFill(Color.RED);
            }
        }
    }

    @Override
    public void simulationChanged(int currentDay){
        Platform.runLater(() -> this.setFollowedStatistics(currentDay));
    }

    @FXML
    private void onStartButtonAction() {
        this.startButton.setDisable(true);
        this.pauseButton.setDisable(false);
        this.stopButton.setDisable(false);
        this.preferredPlantFields.setDisable(true);
        this.dominatingGenotype.setDisable(true);

        this.simulationEngine.start();
    }

    @FXML
    private void onPauseButtonAction() {
        this.startButton.setDisable(false);
        this.pauseButton.setDisable(true);
        this.stopButton.setDisable(false);
        this.preferredPlantFields.setDisable(false);
        this.dominatingGenotype.setDisable(false);

        try {
            this.simulationEngine.pause();
        } catch (InterruptedException ignored) {}
    }

    @FXML
    private void onStopButtonAction() {
        this.startButton.setDisable(true);
        this.pauseButton.setDisable(true);
        this.stopButton.setDisable(true);
        this.preferredPlantFields.setDisable(false);
        this.dominatingGenotype.setDisable(false);

        this.simulationEngine.stop();
    }

    @FXML
    private void preferredPlantFields(){
        this.seeDesirablePositions = !seeDesirablePositions;
        this.preferredPlantFields.setText(seeDesirablePositions ? "Hide" : "Show");
        // add highlight
    }

    @FXML
    private void dominatingGenotype(){
        this.seeDominatingGenotype = !seeDominatingGenotype;
        this.dominatingGenotype.setText(seeDominatingGenotype ? "Hide" : "Show");
        // add highlight
    }
}
