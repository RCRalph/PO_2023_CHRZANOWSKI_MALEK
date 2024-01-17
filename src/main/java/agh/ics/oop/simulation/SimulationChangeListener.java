package agh.ics.oop.simulation;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.map.WorldMap;

import java.util.List;

public interface SimulationChangeListener {
    void simulationChanged(WorldMap worldMap, String message, int currentDay);

    void simulationChanged(String message);

    void simulationChanged(int currentDay);

    void simulationChanged(Animal followedAnimal);
}
