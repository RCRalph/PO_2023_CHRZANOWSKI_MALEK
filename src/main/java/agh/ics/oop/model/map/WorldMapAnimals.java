package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;

import java.util.*;

class WorldMapAnimals {
    private final Map<Vector2D, List<Animal>> animals = new HashMap<>();

    public void addAnimal(Animal animal) {
        this.animals
            .computeIfAbsent(animal.getPosition(), key -> new ArrayList<>())
            .add(animal);
    }

    public void removeAnimal(Animal animal) {
        Vector2D position = animal.getPosition();

        if (!this.animals.containsKey(position)) return;

        this.animals.get(position).remove(animal);
        if (this.animals.get(position).isEmpty()) {
            this.animals.remove(position);
        }
    }

    public boolean isOccupied(Vector2D position) {
        return this.animals.containsKey(position);
    }

    public List<Animal> animalsAt(Vector2D position) {
        return Collections.unmodifiableList(this.animals.get(position));
    }

    public Set<Vector2D> keys() {
        return this.animals.keySet();
    }

    public Collection<Animal> values() {
        List<Animal> result = new ArrayList<>();

        for (Vector2D key : this.animals.keySet()) {
            result.addAll(this.animals.get(key));
        }

        return result;
    }

    public int size() {
        return this.animals.keySet()
            .stream()
            .mapToInt(item -> this.animals.get(item).size())
            .sum();
    }
}
