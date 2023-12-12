package agh.ics.oop.model.element;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.MoveValidator;
import agh.ics.oop.model.Vector2D;

public class Animal implements WorldElement {
    private MapDirection orientation;

    private Vector2D position;

    public Animal() {
        this(new Vector2D(2, 2));
    }

    public Animal(Vector2D position) {
        this.position = position;
        this.orientation = MapDirection.NORTH;
    }

    @Override
    public String toString() {
        return this.orientation.toString();
    }

    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    public void move(MoveValidator validator, MoveDirection direction) {
        switch (direction) {
            case RIGHT -> this.orientation = this.orientation.next();
            case LEFT -> this.orientation = this.orientation.previous();
            case FORWARD -> this.setPosition(validator, this.position.add(this.orientation.toUnitVector()));
            case BACKWARD -> this.setPosition(validator, this.position.subtract(this.orientation.toUnitVector()));
        }
    }

    public MapDirection getOrientation() {
        return orientation;
    }

    public Vector2D getPosition() {
        return position;
    }

    private void setPosition(MoveValidator validator, Vector2D position) {
        if (validator.canMoveTo(position)) {
            this.position = position;
        }
    }
}
