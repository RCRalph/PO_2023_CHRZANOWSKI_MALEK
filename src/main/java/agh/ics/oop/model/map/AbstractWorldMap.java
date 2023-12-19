package agh.ics.oop.model.map;

import agh.ics.oop.model.Pose;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.BehaviourIndicator;
import agh.ics.oop.model.element.Plant;
import agh.ics.oop.model.element.WorldElement;

import java.util.*;

abstract class AbstractWorldMap implements WorldMap {
    protected final WorldMapElements<Animal> animals = new WorldMapElements<>();

    protected final Map<Vector2D, Plant> plants = new HashMap<>();

    protected final List<MapChangeListener> listeners = new ArrayList<>();

    protected final UUID id = UUID.randomUUID();

    protected final Boundary boundary;

    protected final BehaviourIndicator behaviourIndicator;

    public AbstractWorldMap(
        int mapWidth,
        int mapHeight,
        BehaviourIndicator behaviourIndicator
    ) {
        this.boundary = new Boundary(
            new Vector2D(0, 0),
            new Vector2D(mapWidth - 1, mapHeight - 1)
        );

        this.behaviourIndicator = behaviourIndicator;
    }

    @Override
    public void placeAnimal(Animal animal) {
        this.animals.addElement(animal);
        this.mapChanged(String.format("Placed animal at %s", animal.getPosition()));
    }

    @Override
    public void placePlant(Plant plant) throws PositionAlreadyOccupiedException {
        if (this.plants.containsKey(plant.getPosition())) {
            throw new PositionAlreadyOccupiedException(plant.getPosition());
        }

        this.plants.put(plant.getPosition(), plant);
        this.mapChanged(String.format("Placed plant at %s", plant.getPosition()));
    }

    @Override
    public void moveAnimals() {
        for (Animal animal : this.animals.values()) {
            Pose startPose = animal.getPose();

            this.animals.removeElement(animal);
            animal.move(this, this.behaviourIndicator);
            this.animals.addElement(animal);

            if (!startPose.orientation().equals(animal.getOrientation())) {
                this.mapChanged(String.format(
                    "Changed orientation of animal at %s from %s to %s",
                    startPose.position(), startPose.orientation(), animal.getOrientation()
                ));
            } else {
                this.mapChanged(String.format(
                    "Moved animal from %s to %s",
                    startPose.position(), animal.getPosition()
                ));
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return this.plants.containsKey(position) || this.animals.isOccupied(position);
    }

    @Override
    public Collection<WorldElement> objectsAt(Vector2D position) {
        List<WorldElement> result = new ArrayList<>(this.animals.objectsAt(position));

        if (this.plants.containsKey(position)) {
            result.add(this.plants.get(position));
        }

        return result;
    }

    @Override
    public Collection<WorldElement> getElements() {
        return Collections.unmodifiableCollection(this.animals.values());
    }

    @Override
    public Boundary getCurrentBounds() {
        return this.boundary;
    }

    private void mapChanged(String message) {
        for (MapChangeListener listener : listeners) {
            listener.mapChanged(this, message);
        }
    }

    @Override
    public UUID getID() {
        return this.id;
    }

    @Override
    public void subscribe(MapChangeListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void unsubscribe(MapChangeListener listener) {
        this.listeners.remove(listener);
    }
}
