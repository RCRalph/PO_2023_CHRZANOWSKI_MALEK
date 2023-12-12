package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;

public record Boundary(
    Vector2D lowerLeftCorner,
    Vector2D upperRightCorner
) {
    public int totalPositionCount() {
        int horizontalMaxItemCount = this.upperRightCorner().x() - this.lowerLeftCorner().x() + 1,
            verticalMaxItemCount = this.upperRightCorner().y() - this.lowerLeftCorner().y() + 1;

        return horizontalMaxItemCount * verticalMaxItemCount;
    }
}
