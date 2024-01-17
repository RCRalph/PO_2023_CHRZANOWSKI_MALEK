package agh.ics.oop.model.map;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.DarwinistAnimalComparator;
import agh.ics.oop.model.element.Plant;
import agh.ics.oop.model.element.WorldElement;

import java.util.*;

abstract class AbstractWorldMap implements WorldMap {
    protected final WorldMapAnimals animals = new WorldMapAnimals();

    protected final Map<Vector2D, Plant> plants = new HashMap<>();

    protected final Boundary boundary;

    protected final PlantGrowthIndicator plantGrowthIndicator;

    public AbstractWorldMap(Boundary boundary, PlantGrowthIndicator plantGrowthIndicator) {
        this.boundary = boundary;
        this.plantGrowthIndicator = plantGrowthIndicator;
    }

    @Override
    public void placeAnimal(Animal animal) {
        this.animals.addAnimal(animal);
    }

    protected Set<Vector2D> invalidPlantPositions() {
        return Collections.unmodifiableSet(this.plants.keySet());
    }

    @Override
    public void growPlants(int plantCount) {
        for (Plant plant : this.plantGrowthIndicator.getPlants(this.invalidPlantPositions(), plantCount)) {
            this.plants.put(plant.getPosition(), plant);
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
            this.animals.removeAnimal(animal);
            animal.move(this);
            this.animals.addAnimal(animal);
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

        synchronized (this.animals) {
            if (this.animals.isOccupied(position)) {
                result.add(
                    this.animals.animalsAt(position)
                        .stream()
                        .min(new DarwinistAnimalComparator())
                        .orElseThrow()
                );
            }
        }

        return result;
    }

    @Override
    public List<Animal> animalsAt(Vector2D position) {
        return Collections.unmodifiableList(this.animals.animalsAt(position));
    }

    @Override
    public Collection<Animal> getAnimals() {
        return animals.values();
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
    public int plantCount() {
        return this.plants.keySet().size();
    }

    @Override
    public long freeFieldCount() {
        return this.getCurrentBounds()
            .allPossiblePositions()
            .stream()
            .filter(position -> !this.isOccupied(position))
            .count();
    }

    @Override
    public Boundary getCurrentBounds() {
        return this.boundary;
    }

    public Collection<Vector2D> getDesirablePositions(){
        return this.plantGrowthIndicator.getDesirablePositions();
    }
}
