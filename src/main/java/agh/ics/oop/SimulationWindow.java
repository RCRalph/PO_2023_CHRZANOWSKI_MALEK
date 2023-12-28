package agh.ics.oop;

import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class SimulationWindow extends Application {
    private final String title;

    private final Simulation simulation;

    private SimulationPresenter presenter;

    public SimulationWindow(String title, Simulation parameters) {
        this.title = title;
        this.simulation = parameters;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("simulation.fxml"));
        BorderPane viewRoot = loader.load();
        this.configureStage(stage, viewRoot);

        this.presenter = loader.getController();
        this.presenter.setSimulation(this.simulation);

        stage.show();

        try {
            presenter.start();
        } catch (InterruptedException ignored) {}
    }

    private void configureStage(Stage primaryStage, BorderPane viewRoot) {
        Scene scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.getIcons().add(new Image("images/laciasweety.png"));
        primaryStage.setTitle(this.title);
        primaryStage.minWidthProperty().bind(viewRoot.minWidthProperty());
        primaryStage.minHeightProperty().bind(viewRoot.minHeightProperty());
    }
}
