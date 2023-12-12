package agh.ics.oop.model.map;

import agh.ics.oop.model.RandomPositionGenerator;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Grass;
import agh.ics.oop.model.element.WorldElement;

import java.util.*;

public class GrassField extends AbstractWorldMap {
    private final Map<Vector2D, Grass> grass = new HashMap<>();

    public GrassField(int grassFieldCount) {
        if (grassFieldCount < 0) {
            throw new IllegalArgumentException("Invalid grass field count");
        }

        Boundary grassBoundary = new Boundary(
            new Vector2D(0, 0),
            new Vector2D(
                (int)Math.sqrt(grassFieldCount * 10),
                (int)Math.sqrt(grassFieldCount * 10)
            )
        );

        for (Vector2D position : new RandomPositionGenerator(grassBoundary, grassFieldCount)) {
            this.grass.put(position, new Grass(position));
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return super.isOccupied(position) || this.grass.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2D position) {
        return super.objectAt(position) != null ?
            super.objectAt(position) :
            this.grass.get(position);
    }

    @Override
    public Collection<WorldElement> getElements() {
        List<WorldElement> result = new ArrayList<>(super.getElements());
        result.addAll(this.grass.values());

        return Collections.unmodifiableCollection(result);
    }

    Collection<Vector2D> getGrassPositions() {
        return Collections.unmodifiableSet(this.grass.keySet());
    }
}
