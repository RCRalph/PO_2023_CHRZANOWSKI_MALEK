package agh.ics.oop.model.map;

import agh.ics.oop.model.Pose;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.DarwinistAnimalComparator;
import agh.ics.oop.model.element.Plant;
import agh.ics.oop.model.element.WorldElement;

import java.util.*;

abstract class AbstractWorldMap implements WorldMap {
    protected final WorldMapAnimals animals = new WorldMapAnimals();

    protected final Map<Vector2D, Plant> plants = new HashMap<>();

    protected final List<MapChangeListener> listeners = new ArrayList<>();

    protected final Boundary boundary;

    protected final PlantGrowthIndicator plantGrowthIndicator;

    public AbstractWorldMap(Boundary boundary, PlantGrowthIndicator plantGrowthIndicator) {
        this.boundary = boundary;
        this.plantGrowthIndicator = plantGrowthIndicator;
    }

    @Override
    public void placeAnimal(Animal animal) {
        this.animals.addAnimal(animal);
        this.mapChanged(String.format("Placed animal at %s", animal.getPosition()));
    }

    protected Set<Vector2D> invalidPlantPositions() {
        return Collections.unmodifiableSet(this.plants.keySet());
    }

    @Override
    public void growPlants(int plantCount) {
        for (Plant plant : this.plantGrowthIndicator.getPlants(this.invalidPlantPositions(), plantCount)) {
            this.plants.put(plant.getPosition(), plant);
            this.mapChanged(String.format("Placed plant at %s", plant.getPosition()));
        }
    }

    @Override
    public void removeDeadAnimals(int currentDay) {
        for (Animal animal : this.animals.values()) {
            if (animal.getEnergyLevel() <= 0) {
                animal.setDeathDay(currentDay);
                this.animals.removeAnimal(animal);
            }
        }
    }

    @Override
    public void moveAnimals() {
        for (Animal animal : this.animals.values()) {
            Pose startPose = animal.getPose();

            this.animals.removeAnimal(animal);
            animal.move(this);
            this.animals.addAnimal(animal);

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
    public void consumePlants() {
        List<Vector2D> positions = this.plants.keySet()
            .stream()
            .filter(this.animals::isOccupied)
            .toList();

        for (Vector2D position : positions) {
            this.animals.animalsAt(position)
                .stream()
                .min(new DarwinistAnimalComparator())
                .ifPresent(animal -> {
                    animal.consumePlant();
                    this.plants.remove(position);
                });
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return this.plants.containsKey(position) || this.animals.isOccupied(position);
    }

    @Override
    public List<WorldElement> objectsAt(Vector2D position) {
        List<WorldElement> result = new ArrayList<>();

        if (this.plants.containsKey(position)) {
            result.add(this.plants.get(position));
        }

        if (this.animals.isOccupied(position)) {
            result.addAll(this.animals.animalsAt(position));
        }

        return result;
    }

    @Override
    public List<WorldElement> getElements() {
        List<WorldElement> result = new ArrayList<>(this.animals.values());
        result.addAll(this.plants.values());

        return Collections.unmodifiableList(result);
    }

    @Override
    public Set<Vector2D> getAnimalPositions() {
        return this.animals.keys();
    }

    @Override
    public int aliveAnimalCount() {
        return this.animals.size();
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
    public void subscribe(MapChangeListener listener) {
        this.listeners.add(listener);
    }

    @Override
    public void unsubscribe(MapChangeListener listener) {
        this.listeners.remove(listener);
    }
}
