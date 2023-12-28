package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Plant;

import java.util.Collection;
import java.util.Set;

public interface PlantGrowthIndicator {
    Collection<Plant> getPlants(Set<Vector2D> occupiedPositions);
}
