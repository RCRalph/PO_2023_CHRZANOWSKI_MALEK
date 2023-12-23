package agh.ics.oop.model;

import agh.ics.oop.model.element.gene.Gene;

import java.util.Random;

public enum MapDirection {
    NORTH(new Vector2D(0, 1), "N"),
    NORTH_EAST(new Vector2D(1, 1), "NE"),
    EAST(new Vector2D(1, 0), "E"),
    SOUTH_EAST(new Vector2D(1, -1), "SE"),
    SOUTH(new Vector2D(0, -1), "S"),
    SOUTH_WEST(new Vector2D(-1, -1), "SW"),
    WEST(new Vector2D(-1, 0), "W"),
    NORTH_WEST(new Vector2D(-1, 1), "NW");

    private final Vector2D squareVector;

    private final String label;

    MapDirection(Vector2D squareVector, String label) {
        this.squareVector = squareVector;
        this.label = label;
    }

    public MapDirection rotateByGene(Gene gene) {
        return MapDirection.values()[(this.ordinal() + gene.ordinal()) % MapDirection.values().length];
    }

    public Vector2D toSquareVector() {
        return this.squareVector;
    }

    public String toString() {
        return this.label;
    }

    public static MapDirection random() {
        return values()[new Random().nextInt(values().length)];
    }
}
