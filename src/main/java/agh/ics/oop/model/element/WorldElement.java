package agh.ics.oop.model.element;

import agh.ics.oop.model.Vector2D;

public interface WorldElement {
    boolean isAt(Vector2D position);

    Vector2D getPosition();
}
