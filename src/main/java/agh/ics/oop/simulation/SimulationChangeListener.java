package agh.ics.oop.simulation;

import agh.ics.oop.model.map.WorldMap;

public interface SimulationChangeListener {
    void simulationMapChanged(WorldMap worldMap, String message);
}
