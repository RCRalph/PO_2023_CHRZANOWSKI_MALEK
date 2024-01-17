package agh.ics.oop;

import agh.ics.oop.presenter.SimulationPresenter;
import agh.ics.oop.simulation.Simulation;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class SimulationWindow extends Application {
    private final String title;

    private final Simulation simulation;

    private final boolean saveToCSV;

    public SimulationWindow(String title, Simulation simulation, boolean saveToCSV) {
        this.title = title;
        this.simulation = simulation;
        this.saveToCSV = saveToCSV;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        this.configureStage(stage, viewRoot);

        SimulationPresenter presenter = loader.getController();
        presenter.setSimulationEngine(this.simulation);
        presenter.setSaveToCSV(this.saveToCSV);

        stage.setOnCloseRequest(event -> {
            if (this.simulation.getEngine().getExecutionStatus().isStoppable()) {
                this.simulation.getEngine().stop();
            }
        });

        stage.show();
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("animals/laciasweety.png"));
        primaryStage.setTitle(this.title);
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
