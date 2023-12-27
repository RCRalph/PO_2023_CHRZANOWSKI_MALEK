package agh.ics.oop.model;

import agh.ics.oop.model.map.Boundary;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class RandomPositionGenerator {
    public static List<Vector2D> getPositions(Boundary boundary, int positionCount) {
        if (positionCount < 0 || positionCount > boundary.totalPositionCount()) {
            throw new IllegalArgumentException("Invalid position count");
        }

        List<Vector2D> positions = boundary.allPossiblePositions();
        Collections.shuffle(positions);

        return positions.subList(0, positionCount);
    }
}
