package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;

public class PositionAlreadyOccupiedException extends Exception {
    public PositionAlreadyOccupiedException(Vector2D position) {
        super(String.format("Position %s is already occupied", position));
    }
}
