package agh.ics.oop.model;

import agh.ics.oop.model.map.Boundary;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomPositionGenerator implements Iterable<Vector2D> {
    private final List<Vector2D> positions;

    public RandomPositionGenerator(Boundary boundary, int itemCount) {
        if (itemCount < 0 || itemCount > boundary.totalPositionCount()) {
            throw new IllegalArgumentException("Invalid item count");
        }

        List<Vector2D> positions = boundary.allPossiblePositions();
        Collections.shuffle(positions);

        // Remove unneeded positions for faster removal
        positions.subList(itemCount, positions.size()).clear();

        this.positions = positions;
    }

    @Override
    public Iterator<Vector2D> iterator() {
        return this.positions.iterator();
    }
}
