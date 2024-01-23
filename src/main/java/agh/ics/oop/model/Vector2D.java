package agh.ics.oop.model;

public record Vector2D(int x, int y) {
    @Override
    public String toString() {
        return String.format("(%d,%d)", this.x, this.y);
    }

    public boolean precedes(Vector2D other) {
        return this.x() <= other.x() && this.y() <= other.y();
    }

    public boolean follows(Vector2D other) {
        return this.x() >= other.x() && this.y() >= other.y();
    }

    public Vector2D add(Vector2D other) {
        return new Vector2D(this.x() + other.x(), this.y() + other.y());
    }

    public Vector2D subtract(Vector2D other) {
        return new Vector2D(this.x() - other.x(), this.y() - other.y());
    }

    public Vector2D upperRight(Vector2D other) {
        return new Vector2D(Integer.max(this.x(), other.x()), Integer.max(this.y(), other.y()));
    }

    public Vector2D lowerLeft(Vector2D other) {
        return new Vector2D(Integer.min(this.x(), other.x()), Integer.min(this.y(), other.y()));
    }

    public Vector2D opposite() {
        return new Vector2D(-this.x(), -this.y());
    }
}
