package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.WorldElement;

import java.util.*;

class WorldMapElements<T extends WorldElement> {
    private final Map<Vector2D, List<T>> elements = new HashMap<>();

    private void insertKey(Vector2D key) {
        if (!this.elements.containsKey(key)) {
            this.elements.put(key, new ArrayList<>());
        }
    }

    public void addElement(T element) {
        this.insertKey(element.getPosition());
        this.elements.get(element.getPosition()).add(element);
    }

    public void removeElement(T element) {
        Vector2D position = element.getPosition();

        if (!this.elements.containsKey(position)) return;

        this.elements.get(position).remove(element);
        if (this.elements.get(position).isEmpty()) {
            this.elements.remove(position);
        }
    }

    public boolean isOccupied(Vector2D position) {
        return this.elements.containsKey(position);
    }

    public Collection<T> objectsAt(Vector2D position) {
        return Collections.unmodifiableCollection(this.elements.get(position));
    }

    public Collection<T> values() {
        List<T> result = new ArrayList<>();

        for (Vector2D key : this.elements.keySet()) {
            result.addAll(this.elements.get(key));
        }

        return result;
    }
}
