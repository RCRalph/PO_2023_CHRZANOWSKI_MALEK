package agh.ics.oop.simulation;

import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.map.WorldMap;

import java.util.Collection;

public interface SimulationChangeListener {
    void simulationChanged(String message, WorldMap worldMap);

    void simulationChanged(String message, SimulationStatistics statistics, int animalDescendantCount);

    void simulationChanged(String message);

    void simulationChanged(WorldMap map, Collection<Animal> animals);

}
