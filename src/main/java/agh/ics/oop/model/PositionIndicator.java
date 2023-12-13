package agh.ics.oop.model;

public interface PositionIndicator {
    /**
     * Indicate the new position of an object after it moves in the specified MapDirection
     *
     * @param currentPosition The current position of the given object
     * @param direction The direction of the move
     * @return New position of the object
     */
    Vector2D getNewPosition(Vector2D currentPosition, MapDirection direction);
}