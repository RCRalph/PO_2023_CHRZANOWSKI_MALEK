package agh.ics.oop.simulation;

import agh.ics.oop.model.map.WorldMap;

public interface SimulationChangeListener {
    void simulationChanged(WorldMap worldMap, String message);

    void simulationChanged(String message);
}
