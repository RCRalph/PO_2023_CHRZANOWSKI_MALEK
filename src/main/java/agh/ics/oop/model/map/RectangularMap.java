package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;

public class RectangularMap extends AbstractWorldMap {
    private final Boundary boundaries;

    public RectangularMap(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Invalid map size");
        }

        this.boundaries = new Boundary(
            new Vector2D(0, 0),
            new Vector2D(width - 1, height - 1)
        );
    }

    @Override
    public boolean canMoveTo(Vector2D position) {
        return this.boundaries.lowerLeftCorner().precedes(position) &&
            this.boundaries.upperRightCorner().follows(position) &&
            super.canMoveTo(position);
    }

    @Override
    public Boundary getCurrentBounds() {
        return this.boundaries;
    }
}
