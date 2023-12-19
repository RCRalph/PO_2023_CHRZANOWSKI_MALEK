package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;

import java.util.*;

class WorldMapAnimals {
    private final Map<Vector2D, List<Animal>> animals = new HashMap<>();

    private void insertKey(Vector2D key) {
        if (!this.animals.containsKey(key)) {
            this.animals.put(key, new ArrayList<>());
        }
    }

    public void addAnimal(Animal animal) {
        this.insertKey(animal.getPosition());
        this.animals.get(animal.getPosition()).add(animal);
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

    public Collection<Animal> animalsAt(Vector2D position) {
        return Collections.unmodifiableCollection(this.animals.get(position));
    }

    public List<Animal> getOrderedAnimalsAt(Vector2D position) {
        List<Animal> result = new ArrayList<>(this.animalsAt(position));

        Collections.shuffle(result);
        result.sort((animal1, animal2) -> {
            if (animal1.getEnergyLevel() != animal2.getEnergyLevel()) {
                return animal2.getEnergyLevel() - animal1.getEnergyLevel();
            } else if  (animal1.getBirthday() != animal2.getBirthday()) {
                return animal2.getBirthday() - animal1.getBirthday();
            } else if (animal1.getChildCount() != animal2.getChildCount()) {
                return animal2.getChildCount() - animal1.getChildCount();
            } else {
                return 0;
            }
        });

        return result;
    }

    public Collection<Animal> values() {
        List<Animal> result = new ArrayList<>();

        for (Vector2D key : this.animals.keySet()) {
            result.addAll(this.animals.get(key));
        }

        return result;
    }

    public int size() {
        return this.animals.values().size();
    }
}
