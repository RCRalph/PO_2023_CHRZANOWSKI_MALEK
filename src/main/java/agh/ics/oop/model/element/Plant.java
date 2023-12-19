package agh.ics.oop.model.element;

import agh.ics.oop.model.Vector2D;

public record Plant(Vector2D position) implements WorldElement {
    @Override
    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public String toString() {
        return "*";
    }
}
