package agh.ics.oop;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.*;
import agh.ics.oop.model.map.Boundary;
import agh.ics.oop.model.map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final SimulationParameters parameters;

    private final WorldMap map = null;

    private final GenesIndicator genesIndicator = null;

    private final BehaviourIndicator behaviourIndicator = null;

    private final List<Animal> animals = new ArrayList<>();

    private final EnergyParameters energyParameters;

    private int currentDay = 0;

    public Simulation(SimulationParameters parameters) throws InvalidSimulationConfigurationException {
        if (parameters.mapWidth() <= 0) {
            throw new InvalidSimulationConfigurationException("Map width should be greater than 0");
        } else if (parameters.mapHeight() <= 0) {
            throw new InvalidSimulationConfigurationException("Map height should be greater than 0");
        } else if (parameters.startPlantCount() < 0) {
            throw new InvalidSimulationConfigurationException("Start plant count should be greater than or equal to 0");
        } else if (parameters.dailyPlantGrowth() < 0) {
            throw new InvalidSimulationConfigurationException("Daily plant growth should be greater than or equal to 0");
        } else if (parameters.startAnimalCount() <= 0) {
            throw new InvalidSimulationConfigurationException("Start animal count should be greater than 0");
        } else if (parameters.animalStartEnergy() < 0) {
            throw new InvalidSimulationConfigurationException("Animal's start energy should be greater than 0");
        } else if (parameters.plantEnergy() < 0) {
            throw new InvalidSimulationConfigurationException("Plant's energy should be greater than 0");
        } else if (parameters.reproductionEnergy() < 0) {
            throw new InvalidSimulationConfigurationException("Reproduction energy should be greater than 0");
        } else if (parameters.minimumMutationCount() < 0) {
            throw new InvalidSimulationConfigurationException("Minimal mutation count should be greater or equal to 0");
        } else if (parameters.maximumMutationCount() > parameters.geneCount()) {
            throw new InvalidSimulationConfigurationException("Maximal mutation count should be less than or equal to gene count");
        } else if (parameters.geneCount() <= 0) {
            throw new InvalidSimulationConfigurationException("Gene count should be greater than 0");
        }

        this.parameters = parameters;
        this.energyParameters = new EnergyParameters(
            this.parameters.animalStartEnergy(),
            this.parameters.animalMoveEnergy(),
            this.parameters.plantEnergy(),
            this.parameters.reproductionEnergy()
        );

        // Get plant growth indicator variant
        /*PlantGrowthIndicator plantGrowthIndicator = switch (parameters.plantGrowthIndicatorVariant()) {
            case "Forested equators" -> ...
        }*/

        // Get animal behaviour indicator variant
        /*AnimalBehaviourIndicator animalBehaviourIndicator = switch (parameters.animalBehaviourIndicatorVariant()) {
            case "Full predestination" -> ...
        };*/

        // Set map
        /*this.map = switch (parameters.worldMapVariant()) {

        }*/

        // Set genes indicator variant
        /*this.genesIndicator = switch (parameters.genesIndicatorVariant()) {
            case "Full randomization" -> ...
            case "Slight correction" -> ...
        }*/
    }

    private void placeAnimal(Animal animal) {
        this.animals.add(animal);
        this.map.placeAnimal(animal);
    }

    private void generateAnimals() {
        Boundary boundary = this.map.getCurrentBounds();

        for (int i = 0; i < this.parameters.startAnimalCount(); i++) {
            Animal animal = new Animal(
                Vector2D.random(boundary),
                Gene.generateList(this.parameters.geneCount()),
                this.behaviourIndicator,
                this.energyParameters,
                this.currentDay
            );

            this.placeAnimal(animal);
        }
    }

    private void reproduceAnimals() {
        for (Vector2D position : this.map.getAnimalPositions()) {
            List<Animal> animals = this.map.objectsAt(position)
                .stream()
                .filter(item -> item instanceof Animal)
                .map(item -> (Animal) item)
                .filter(animal -> animal.getEnergyLevel() >= this.energyParameters.reproductionEnergy())
                .sorted(new DarwinistAnimalComparator())
                .toList();

            if (animals.size() >= 2) {
                Animal child = new Animal(
                    position,
                    this.genesIndicator.indicateGenes(
                        animals.get(0).reproduce(),
                        animals.get(1).reproduce()
                    ),
                    this.behaviourIndicator,
                    this.energyParameters,
                    this.currentDay
                );

                this.placeAnimal(child);
            }
        }
    }

    @Override
    public void run() {
        this.generateAnimals();
        this.map.growPlants(this.parameters.startPlantCount());

        while (this.map.aliveAnimalCount() > 0) {
            this.currentDay++;

            this.map.removeDeadAnimals(this.currentDay);
            this.map.moveAnimals();
            this.map.consumePlants();
            this.reproduceAnimals();
            this.map.growPlants(this.parameters.dailyPlantGrowth());
        }
    }
}
