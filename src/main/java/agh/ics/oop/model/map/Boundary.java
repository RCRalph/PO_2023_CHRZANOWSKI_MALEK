package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;

import java.util.ArrayList;
import java.util.List;

public record Boundary(
    Vector2D lowerLeftCorner,
    Vector2D upperRightCorner
) {
    public Boundary {
        if (!lowerLeftCorner.precedes(upperRightCorner)) {
            throw new IllegalArgumentException(String.format(
                "Invalid boundary corners: %s doesn't precede %s",
                lowerLeftCorner, upperRightCorner
            ));
        }
    }

    public int totalPositionCount() {
        int horizontalMaxItemCount = this.upperRightCorner().x() - this.lowerLeftCorner().x() + 1,
            verticalMaxItemCount = this.upperRightCorner().y() - this.lowerLeftCorner().y() + 1;

        return horizontalMaxItemCount * verticalMaxItemCount;
    }

    public List<Vector2D> allPossiblePositions() {
        List<Vector2D> result = new ArrayList<>(this.totalPositionCount());

        for (int x = this.lowerLeftCorner().x(); x <= this.upperRightCorner().x(); x++) {
            for (int y = this.lowerLeftCorner().y(); y < this.upperRightCorner().y(); y++) {
                result.add(new Vector2D(x, y));
            }
        }

        return result;
    }

    public int width() {
        return this.upperRightCorner.x() - this.lowerLeftCorner.x();
    }

    public int height() {
        return this.upperRightCorner.y() - this.lowerLeftCorner.y();
    }

    public boolean isInside(Vector2D position) {
        return this.lowerLeftCorner.precedes(position) && this.upperRightCorner.follows(position);
    }
}
