package agh.ics.oop.model.map;

import agh.ics.oop.model.Pose;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.Plant;
import agh.ics.oop.model.element.WorldElement;

import java.util.*;

abstract class AbstractWorldMap implements WorldMap {
    protected final WorldMapAnimals animals = new WorldMapAnimals();

    protected final Map<Vector2D, Plant> plants = new HashMap<>();

    protected final List<MapChangeListener> listeners = new ArrayList<>();

    protected final Boundary boundary;

    protected final PlantGrowthIndicator plantGrowthIndicator;

    public AbstractWorldMap(
        int mapWidth,
        int mapHeight,
        PlantGrowthIndicator plantGrowthIndicator
    ) {
        this.boundary = new Boundary(
            new Vector2D(0, 0),
            new Vector2D(mapWidth - 1, mapHeight - 1)
        );

        this.plantGrowthIndicator = plantGrowthIndicator;
    }

    @Override
    public void placeAnimal(Animal animal) {
        this.animals.addAnimal(animal);
        this.mapChanged(String.format("Placed animal at %s", animal.getPosition()));
    }

    private void placePlant(Plant plant) throws PositionAlreadyOccupiedException {
        if (this.plants.containsKey(plant.getPosition())) {
            throw new PositionAlreadyOccupiedException(plant.getPosition());
        }

        this.plants.put(plant.getPosition(), plant);
        this.mapChanged(String.format("Placed plant at %s", plant.getPosition()));
    }

    @Override
    public void growPlants(int plantCount) {
        Collection<Plant> plantsToPlace = this.plantGrowthIndicator.indicatePlantPositions(
            this.boundary,
            new HashSet<>(),//this.occupiedPositions(),
            plantCount
        );

        try {
            for (Plant plant : plantsToPlace) {
                this.placePlant(plant);
            }
        } catch (PositionAlreadyOccupiedException exception) {
            /*  This should never happen, because PlantGrowthIndicator
                should always return Plant instances on only
                non-occupied positions.  */
            this.mapChanged(exception.getMessage());
            throw new IllegalArgumentException(exception.getMessage());
        }
    }

    @Override
    public void removeDeadAnimals() {
        for (Animal animal : this.animals.values()) {
            if (animal.getEnergyLevel() <= 0) {
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
        for (Vector2D plantPosition : this.plants.keySet()) {
            if (!this.animals.isOccupied(plantPosition)) continue;

            this.animals.getOrderedAnimalsAt(plantPosition).get(0).eatPlant();
            this.plants.remove(plantPosition);
        }
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return this.plants.containsKey(position) || this.animals.isOccupied(position);
    }

    @Override
    public Collection<WorldElement> objectsAt(Vector2D position) {
        List<WorldElement> result = new ArrayList<>(this.animals.animalsAt(position));

        if (this.plants.containsKey(position)) {
            result.add(this.plants.get(position));
        }

        return result;
    }

    @Override
    public Collection<WorldElement> getElements() {
        List<WorldElement> result = new ArrayList<>(this.animals.values());
        result.addAll(this.plants.values());

        return Collections.unmodifiableCollection(result);
    }

    @Override
    public int animalCount() {
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
