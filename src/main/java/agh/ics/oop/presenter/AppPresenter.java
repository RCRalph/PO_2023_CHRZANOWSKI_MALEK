package agh.ics.oop.presenter;

import agh.ics.oop.SimulationWindow;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.stage.Stage;

public class AppPresenter {
    private int simulationID = 1;

    @FXML
    private void onStartClicked(ActionEvent ignored) throws Exception {
        new SimulationWindow(String.format("Simulation #%d", this.simulationID++)).start(new Stage());
    }
}
