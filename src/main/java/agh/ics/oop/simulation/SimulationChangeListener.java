package agh.ics.oop.simulation;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.map.WorldMap;

import java.util.Collection;
import java.util.List;

public interface SimulationChangeListener {
    void simulationChanged(String message, WorldMap worldMap);

    void simulationChanged(String message, SimulationStatistics statistics);

    void simulationChanged(String message);

    void simulationChanged(WorldMap map, Animal followedAnimal);

    void simulationChanged(int descendantCount);

    void simulationChanged(WorldMap map, Collection<Animal> animals);
}
