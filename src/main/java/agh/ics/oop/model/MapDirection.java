package agh.ics.oop.model;

public enum MapDirection {
    NORTH(new Vector2D(0, 1), "N"),
    SOUTH(new Vector2D(0, -1), "S"),
    WEST(new Vector2D(-1, 0), "W"),
    EAST(new Vector2D(1, 0), "E");

    private final Vector2D unitVector;

    private final String label;

    MapDirection(Vector2D unitVector, String label) {
        this.unitVector = unitVector;
        this.label = label;
    }

    public MapDirection next() {
        return switch (this) {
            case NORTH -> EAST;
            case EAST -> SOUTH;
            case SOUTH -> WEST;
            case WEST -> NORTH;
        };
    }

    public MapDirection previous() {
        return switch (this) {
            case NORTH -> WEST;
            case WEST -> SOUTH;
            case SOUTH -> EAST;
            case EAST -> NORTH;
        };
    }

    public String toString() {
        return this.label;
    }

    public Vector2D toUnitVector() {
        return this.unitVector;
    }
}
