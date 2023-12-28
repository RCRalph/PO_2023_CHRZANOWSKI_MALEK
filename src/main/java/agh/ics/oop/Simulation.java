package agh.ics.oop;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.DarwinistAnimalComparator;
import agh.ics.oop.model.element.EnergyParameters;
import agh.ics.oop.model.element.behaviour.ABitOfMadnessBehaviourIndicator;
import agh.ics.oop.model.element.behaviour.BackAndForthBehaviourIndicator;
import agh.ics.oop.model.element.behaviour.BehaviourIndicator;
import agh.ics.oop.model.element.behaviour.CompletePredestinationBehaviourIndicator;
import agh.ics.oop.model.element.gene.*;
import agh.ics.oop.model.map.*;

import java.lang.reflect.InvocationTargetException;
import java.util.*;

public class Simulation implements Runnable {
    public static final Map<String, Class<? extends PlantGrowthIndicator>> PLANT_GROWTH_INDICATORS = Map.of(
        "Forested equator", ForestedEquatorPlantGrowthIndicator.class
    );

    public static final Map<String, Class<? extends WorldMap>> WORLD_MAPS = Map.of(
        "Globe", GlobeWorldMap.class,
        "Underground tunnels", UndergroundTunnelsWorldMap.class
    );

    public static final Map<String, Class<? extends ChildGenesIndicator>> CHILD_GENES_INDICATORS = Map.of(
        "Complete randomness", CompleteRandomnessChildGenesIndicator.class,
        "Slight correction", SlightCorrectionChildGenesGenerator.class,
        "Replacement", ReplacementChildGenesIndicator.class
    );

    public static final Map<String, Class<? extends BehaviourIndicator>> BEHAVIOUR_INDICATORS = Map.of(
        "Complete predestination", CompletePredestinationBehaviourIndicator.class,
        "A bit of madness", ABitOfMadnessBehaviourIndicator.class,
        "Back and forth", BackAndForthBehaviourIndicator.class
    );

    private final SimulationParameters parameters;

    private final ChildGenesIndicator childGenesIndicator;

    private final BehaviourIndicator behaviourIndicator;

    private final WorldMap worldMap;

    private final List<Animal> animals = new ArrayList<>();

    private final EnergyParameters energyParameters;

    private final Boundary boundary;

    private int currentDay = 0;

    private PlantGrowthIndicator getPlantGrowthIndicator(
        SimulationParameters parameters
    ) throws InvalidSimulationConfigurationException {
        try {
            return PLANT_GROWTH_INDICATORS
                .get(parameters.plantGrowthIndicatorVariant())
                .getDeclaredConstructor(Boundary.class)
                .newInstance(this.boundary);
        } catch (
            NoSuchMethodException |
            InstantiationException |
            IllegalAccessException |
            InvocationTargetException exception
        ) {
            throw new InvalidSimulationConfigurationException(
                "Invalid plant growth indicator implementation: " + exception.getMessage()
            );
        }
    }

    private WorldMap getWorldMap(
        SimulationParameters parameters,
        PlantGrowthIndicator plantGrowthIndicator
    ) throws InvalidSimulationConfigurationException {
        Class<? extends WorldMap> worldMap = WORLD_MAPS.get(parameters.worldMapVariant());

        try {
            if (worldMap == GlobeWorldMap.class) {
                return worldMap
                    .getDeclaredConstructor(Boundary.class, PlantGrowthIndicator.class)
                    .newInstance(this.boundary, plantGrowthIndicator);
            } else if (worldMap == UndergroundTunnelsWorldMap.class) {
                return worldMap
                    .getDeclaredConstructor(Boundary.class, Integer.class, PlantGrowthIndicator.class)
                    .newInstance(this.boundary, parameters.tunnelCount(), plantGrowthIndicator);
            } else {
                throw new InvalidSimulationConfigurationException("World map variant should point to a valid class");
            }
        } catch (
            NoSuchMethodException |
            InstantiationException |
            IllegalAccessException |
            InvocationTargetException exception
        ) {
            throw new InvalidSimulationConfigurationException(
                "Invalid world map implementation: " + exception.getMessage()
            );
        }
    }

    private BehaviourIndicator getBehaviourIndicator(
        SimulationParameters parameters
    ) throws InvalidSimulationConfigurationException {
        try {
            return BEHAVIOUR_INDICATORS
                .get(parameters.animalBehaviourIndicatorVariant())
                .getDeclaredConstructor(Integer.class)
                .newInstance(parameters.geneCount());
        } catch (
            NoSuchMethodException |
            InstantiationException |
            IllegalAccessException |
            InvocationTargetException exception
        ) {
            throw new InvalidSimulationConfigurationException(
                "Invalid behaviour indicator implementation: " + exception.getMessage()
            );
        }
    }

    private ChildGenesIndicator getChildGenesIndicator(
        SimulationParameters parameters
    ) throws InvalidSimulationConfigurationException {
        try {
            return CHILD_GENES_INDICATORS
                .get(parameters.childGenesIndicatorVariant())
                .getDeclaredConstructor(Integer.class, Integer.class, Integer.class)
                .newInstance(
                    parameters.geneCount(),
                    parameters.minimumMutationCount(),
                    parameters.maximumMutationCount()
                );
        } catch (
            NoSuchMethodException |
            InstantiationException |
            IllegalAccessException |
            InvocationTargetException exception
        ) {
            throw new InvalidSimulationConfigurationException(
                "Invalid child genes indicator implementation: " + exception.getMessage()
            );
        }
    }

    public Simulation(SimulationParameters parameters) throws InvalidSimulationConfigurationException {
        if (parameters.getValidationMessage().isPresent()) {
            throw new InvalidSimulationConfigurationException(parameters.getValidationMessage().get());
        }

        this.parameters = parameters;

        this.energyParameters = new EnergyParameters(
            this.parameters.animalStartEnergy(),
            this.parameters.animalMoveEnergy(),
            this.parameters.plantEnergy(),
            this.parameters.reproductionEnergy()
        );

        this.boundary = new Boundary(
            new Vector2D(0, 0),
            new Vector2D(parameters.mapWidth() - 1, parameters.mapHeight() - 1)
        );

        this.worldMap = this.getWorldMap(parameters, this.getPlantGrowthIndicator(parameters));
        this.behaviourIndicator = this.getBehaviourIndicator(parameters);
        this.childGenesIndicator = this.getChildGenesIndicator(parameters);
    }

    private void placeAnimal(Animal animal) {
        this.animals.add(animal);
        this.worldMap.placeAnimal(animal);
    }

    private void generateAnimals() {
        Boundary boundary = this.worldMap.getCurrentBounds();

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
        for (Vector2D position : this.worldMap.getAnimalPositions()) {
            List<Animal> animals = this.worldMap.objectsAt(position)
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
        this.worldMap.growPlants(this.parameters.startPlantCount());

        while (this.worldMap.aliveAnimalCount() > 0) {
            this.currentDay++;

            this.worldMap.removeDeadAnimals(this.currentDay);
            this.worldMap.moveAnimals();
            this.worldMap.consumePlants();
            this.reproduceAnimals();
            this.worldMap.growPlants(this.parameters.dailyPlantGrowth());
        }
    }
}
