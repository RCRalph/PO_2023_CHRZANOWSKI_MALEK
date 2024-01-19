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
import java.util.UUID;

public class SimulationWindow extends Application {
    private final String name;

    private final UUID uuid;

    private final Simulation simulation;

    private final boolean saveToCSV;  // czy to jest cecha okna?

    public SimulationWindow(String name, UUID uuid, Simulation simulation, boolean saveToCSV) {
        this.name = name;
        this.uuid = uuid;
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
        presenter.setUUID(this.uuid);

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
        primaryStage.setTitle(String.format("%s (%s)", this.name, this.uuid));
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
