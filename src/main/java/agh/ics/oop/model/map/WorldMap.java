package agh.ics.oop.model.map;

import agh.ics.oop.model.PoseIndicator;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.WorldElement;

import java.util.List;
import java.util.Set;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2D and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends PoseIndicator {

    /**
     * Place an animal on the map.
     *
     * @param animal The animal to place on the map.
     */
    void placeAnimal(Animal animal);

    /**
     * Place plants on the map.
     *
     * @param plantCount The max amount of plants to place.
     */
    void growPlants(int plantCount);

    /**
     * Removes all dead animals from the map
     */
    void removeDeadAnimals(int currentDay);

    /**
     * Moves all animals on the map.
     */
    void moveAnimals();

    /**
     * Chooses animals which can consume plants at their positions,
     * then increases their energy and removes eaten plants from the map.
     */
    void consumePlants();

    /**
     * Return true if given position on the map is occupied. Should not be
     * confused with canMove since there might be empty positions where the animal
     * cannot move.
     *
     * @param position Position to check.
     * @return True if the position is occupied.
     */
    boolean isOccupied(Vector2D position);

    /**
     * Return a collection of WorldElement at a given position.
     *
     * @param position The position of the world elements.
     * @return Collection of WorldElement or empty collection if position isn't occupied.
     */
    List<WorldElement> objectsAt(Vector2D position);

    /**
     * Return a collection of elements contained on the map.
     *
     * @return Unmodifiable collection of elements on the map
     */
    List<WorldElement> getElements();

    /**
     * Return a collection of elements contained on the map.
     *
     * @return Unmodifiable collection of elements on the map
     */
    Set<Vector2D> getAnimalPositions();

    /**
     * Return the count of animals currently on the map
     *
     * @return Integer value of animals on the map
     */
    int aliveAnimalCount();

    /**
     * Return a Boundary record of map bounds.
     *
     * @return Boundary records of map bounds
     */
    Boundary getCurrentBounds();
}