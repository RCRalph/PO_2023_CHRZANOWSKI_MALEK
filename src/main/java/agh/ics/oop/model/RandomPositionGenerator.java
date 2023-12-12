package agh.ics.oop.model;

import agh.ics.oop.model.map.Boundary;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomPositionGenerator implements Iterable<Vector2D> {
    private final List<Vector2D> positions;

    public RandomPositionGenerator(Boundary boundary, int itemCount) {
        if (itemCount < 0 || itemCount > boundary.totalPositionCount()) {
            throw new IllegalArgumentException("Invalid item count");
        }

        List<Vector2D> positions = this.getAllPossiblePositions(boundary);
        Collections.shuffle(positions);

        // Remove unneeded positions for faster removal
        positions.subList(itemCount, positions.size()).clear();

        this.positions = positions;
    }

    private List<Vector2D> getAllPossiblePositions(Boundary boundary) {
        List<Vector2D> result = new ArrayList<>();

        for (int x = boundary.lowerLeftCorner().x(); x <= boundary.upperRightCorner().x(); x++) {
            for (int y = boundary.lowerLeftCorner().y(); y < boundary.upperRightCorner().y(); y++) {
                result.add(new Vector2D(x, y));
            }
        }

        return result;
    }

    @Override
    public Iterator<Vector2D> iterator() {
        return this.positions.iterator();
    }
}
