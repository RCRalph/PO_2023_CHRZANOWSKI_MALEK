package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Plant;
import agh.ics.oop.model.element.WorldElement;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

public interface PlantGrowthIndicator {
    Collection<Plant> getPlants(Set<Vector2D> occupiedPositions, int plantCount);

    Collection<Vector2D> getDesirablePositions();
}
