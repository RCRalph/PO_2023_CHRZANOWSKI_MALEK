package agh.ics.oop;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.DarwinistAnimalComparator;
import agh.ics.oop.model.element.EnergyParameters;
import agh.ics.oop.model.element.behaviour.ABitOfMadnessBehaviourIndicator;
import agh.ics.oop.model.element.behaviour.BackAndForthBehaviourIndicator;
import agh.ics.oop.model.element.behaviour.BehaviourIndicator;
import agh.ics.oop.model.element.behaviour.FullPredestinationBehaviourIndicator;
import agh.ics.oop.model.element.gene.*;
import agh.ics.oop.model.map.Boundary;
import agh.ics.oop.model.map.PlantGrowthIndicator;
import agh.ics.oop.model.map.UndergroundTunnelsWorldMap;
import agh.ics.oop.model.map.WorldMap;

import java.util.ArrayList;
import java.util.List;

public class Simulation implements Runnable {
    private final SimulationParameters parameters;

    private final WorldMap map;

    private final ChildGenesIndicator childGenesIndicator;

    private final BehaviourIndicator behaviourIndicator;

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
        } else if (0 < parameters.healthyAnimalEnergy() && parameters.healthyAnimalEnergy() > parameters.reproductionEnergy()) {
            throw new InvalidSimulationConfigurationException("Reproduction energy should not exceed healthy animal energy level");
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

        // Set plant growth indicator variant
        PlantGrowthIndicator plantGrowthIndicator = null; /*switch (parameters.plantGrowthIndicatorVariant()) {
            case "Forested equators" -> ...
        }*/

        this.behaviourIndicator = switch (parameters.animalBehaviourIndicatorVariant()) {
            case "Full predestination" -> new FullPredestinationBehaviourIndicator(parameters.geneCount());

            case "A bit of madness" -> new ABitOfMadnessBehaviourIndicator(parameters.geneCount());

            case "Back and forth" -> new BackAndForthBehaviourIndicator(parameters.geneCount());

            default -> throw new InvalidSimulationConfigurationException(
                "Animal behaviour indicator should indicate a valid class name"
            );
        };

        this.map = switch (parameters.worldMapVariant()) {
            case "Underground tunnels" -> {
                if (parameters.tunnelCount() * 2 <= (parameters.mapWidth() + 1) * (parameters.mapHeight() + 1)) {
                    throw new InvalidSimulationConfigurationException(
                        "Tunnel entrances count should not exceed total position count of the given map"
                    );
                }

                yield new UndergroundTunnelsWorldMap(
                    parameters.mapWidth(),
                    parameters.mapHeight(),
                    parameters.tunnelCount(),
                    plantGrowthIndicator
                );
            }
            default -> throw new InvalidSimulationConfigurationException(
                "World map should indicate a valid class name"
            );
        };

        this.childGenesIndicator = switch (parameters.childGenesIndicatorVariant()) {
            case "Full randomization" -> new CompleteRandomnessChildGenesIndicator(
                parameters.geneCount(),
                parameters.minimumMutationCount(),
                parameters.maximumMutationCount()
            );

            case "Slight correction" -> new SlightCorrectionChildGenesGenerator(
                parameters.geneCount(),
                parameters.minimumMutationCount(),
                parameters.maximumMutationCount()
            );

            case "Replacement" -> new ReplacementChildGenesIndicator(
                parameters.geneCount(),
                parameters.minimumMutationCount(),
                parameters.maximumMutationCount()
            );

            default -> throw new InvalidSimulationConfigurationException(
                "Child genes indicator indicate a valid class name"
            );
        };
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
                Gene.randomList(this.parameters.geneCount()),
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
                .filter(animal -> animal.getEnergyLevel() >= this.parameters.healthyAnimalEnergy())
                .sorted(new DarwinistAnimalComparator())
                .limit(2)
                .toList();

            if (animals.size() == 2) {
                Animal child = new Animal(
                    position,
                    this.childGenesIndicator.getChildGenes(
                        animals.get(0).reproduce(),
                        animals.get(1).reproduce()
                    ),
                    this.behaviourIndicator,
                    this.energyParameters,
                    this.currentDay,
                    this.energyParameters.reproductionEnergy() * 2
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
