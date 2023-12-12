package agh.ics.oop;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.map.PositionAlreadyOccupiedException;
import agh.ics.oop.model.map.WorldMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Simulation implements Runnable {
    private final List<Animal> animals;

    private final WorldMap map;

    private final List<MoveDirection> moves;

    public Simulation(List<MoveDirection> moves, List<Vector2D> positions, WorldMap map) {
        if (positions.isEmpty()) {
            throw new IllegalArgumentException("Positions cannot be empty");
        }

        this.moves = new ArrayList<>(moves);
        this.map = map;

        this.animals = new ArrayList<>(positions.size());
        for (Vector2D position : positions) {
            Animal animal = new Animal(position);

            try {
                this.map.place(animal);
                this.animals.add(animal);
            } catch (PositionAlreadyOccupiedException ignored) {}
        }
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < this.moves.size(); i++) {
                Thread.sleep(500);
                this.map.move(this.animals.get(i % this.animals.size()), this.moves.get(i));
            }
        } catch (InterruptedException ignored) {}
    }

    List<Animal> getAnimals() {
        return Collections.unmodifiableList(this.animals);
    }
}
