package agh.ics.oop.simulation;

import agh.ics.oop.model.map.WorldMap;

public interface SimulationChangeListener {
    void simulationChanged(String message, WorldMap worldMap);

    void simulationChanged(String message, SimulationStatistics statistics, int animalDescendantCount);

    void simulationChanged(String message);
}
