package agh.ics.oop.model.map;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.WorldElement;

import java.util.*;

abstract class AbstractWorldMap implements WorldMap {
    protected final Map<Vector2D, Animal> animals = new HashMap<>();

    protected final List<MapChangeListener> listeners = new ArrayList<>();

    protected final UUID id = UUID.randomUUID();

    @Override
    public boolean canMoveTo(Vector2D position) {
        return !this.animals.containsKey(position);
    }

    @Override
    public void place(WorldElement element) throws PositionAlreadyOccupiedException {
        if (element instanceof Animal animal) {
            if (!this.canMoveTo(animal.getPosition())) {
                throw new PositionAlreadyOccupiedException(animal.getPosition());
            }

            this.animals.put(animal.getPosition(), animal);
        } else {
            throw new IllegalArgumentException("WorldElement cannot be placed");
        }

        this.mapChanged(String.format("Placed at %s", element.getPosition()));
    }

    @Override
    public void move(WorldElement element, MoveDirection direction) {
        if (this.objectAt(element.getPosition()) != element) return;

        if (element instanceof Animal animal) {
            Vector2D startPosition = animal.getPosition();
            MapDirection startOrientation = animal.getOrientation();

            this.animals.remove(animal.getPosition());
            animal.move(this, direction);
            this.animals.put(animal.getPosition(), animal);

            if (!startOrientation.equals(animal.getOrientation())) {
                this.mapChanged(String.format(
                    "Changed orientation of animal at %s from %s to %s",
                    startPosition, startOrientation, animal.getOrientation()
                ));
            } else if (startPosition.equals(element.getPosition())) {
                this.mapChanged(String.format(
                    "Tried to move animal at %s %s - position already occupied",
                    startPosition, direction
                ));
            } else {
                this.mapChanged(String.format(
                    "Successfully moved animal from %s to %s",
                    startPosition, animal.getPosition()
                ));
            }
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return this.animals.containsKey(position);
    }

    @Override
    public WorldElement objectAt(Vector2D position) {
        return this.animals.get(position);
    }

    @Override
    public Collection<WorldElement> getElements() {
        return Collections.unmodifiableCollection(this.animals.values());
    }

    @Override
    public Boundary getCurrentBounds() {
        Vector2D lowerLeftCorner = new Vector2D(Integer.MAX_VALUE, Integer.MAX_VALUE);
        Vector2D upperRightCorner = new Vector2D(Integer.MIN_VALUE, Integer.MIN_VALUE);

        for (WorldElement item : this.getElements()) {
            lowerLeftCorner = lowerLeftCorner.lowerLeft(item.getPosition());
            upperRightCorner = upperRightCorner.upperRight(item.getPosition());
        }

        return new Boundary(lowerLeftCorner, upperRightCorner);
    }

    public void registerObserver(MapChangeListener listener) {
        this.listeners.add(listener);
    }

    public void unregisterObserver(MapChangeListener listener) {
        this.listeners.remove(listener);
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
}
