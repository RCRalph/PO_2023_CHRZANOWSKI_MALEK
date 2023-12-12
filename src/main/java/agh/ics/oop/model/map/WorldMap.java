package agh.ics.oop.model.map;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.MoveValidator;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.WorldElement;

import java.util.Collection;
import java.util.UUID;

/**
 * The interface responsible for interacting with the map of the world.
 * Assumes that Vector2D and MoveDirection classes are defined.
 *
 * @author apohllo, idzik
 */
public interface WorldMap extends MoveValidator {

    /**
     * Place an animal on the map.
     *
     * @param element The animal to place on the map.
     */
    void place(WorldElement element) throws PositionAlreadyOccupiedException;

    /**
     * Moves an animal (if it is present on the map) according to specified direction.
     * If the move is not possible, this method has no effect.
     */
    void move(WorldElement element, MoveDirection direction);

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
     * Return an animal at a given position.
     *
     * @param position The position of the animal.
     * @return animal or null if the position is not occupied.
     */
    WorldElement objectAt(Vector2D position);

    /**
     * Return a collection of elements contained on the map.
     *
     * @return Unmodifiable collection of elements on the map
     */
    Collection<WorldElement> getElements();

    /**
     * Return a Boundary record of map bounds.
     *
     * @return Boundary records of map bounds
     */
    Boundary getCurrentBounds();

    /**
     * Return UUID of map
     *
     * @return UUID of map
     */
    UUID getID();

    void registerObserver(MapChangeListener listener);

    void unregisterObserver(MapChangeListener listener);
}