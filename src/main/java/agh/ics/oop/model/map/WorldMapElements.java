package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.WorldElement;

import java.util.*;

public class WorldMapElements {
    private final Map<Vector2D, HashSet<WorldElement>> elements = new HashMap<>();

    private void insertKey(Vector2D key) {
        if (!this.elements.containsKey(key)) {
            this.elements.put(key, new HashSet<>());
        }
    }

    public void addElement(WorldElement element) {
        this.insertKey(element.getPosition());
        this.elements.get(element.getPosition()).add(element);
    }

    public void removeElement(WorldElement element) {
        if (!this.elements.containsKey(element.getPosition())) {
            this.elements.put(element.getPosition(), new HashSet<>());
        }
    }

    public boolean isOccupied(Vector2D position) {
        return this.elements.containsKey(position) && !this.elements.get(position).isEmpty();
    }

    public Collection<WorldElement> objectsAt(Vector2D position) {
        return Collections.unmodifiableCollection(this.elements.get(position));
    }
}
