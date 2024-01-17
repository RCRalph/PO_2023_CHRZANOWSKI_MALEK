package agh.ics.oop.simulation;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.behaviour.ABitOfMadnessBehaviourIndicator;
import agh.ics.oop.model.element.behaviour.BackAndForthBehaviourIndicator;
import agh.ics.oop.model.element.behaviour.BehaviourIndicator;
import agh.ics.oop.model.element.behaviour.CompletePredestinationBehaviourIndicator;
import agh.ics.oop.model.element.gene.ChildGenesIndicator;
import agh.ics.oop.model.element.gene.CompleteRandomnessChildGenesIndicator;
import agh.ics.oop.model.element.gene.ReplacementChildGenesIndicator;
import agh.ics.oop.model.element.gene.SlightCorrectionChildGenesGenerator;
import agh.ics.oop.model.map.*;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;

public class SimulationConfigurator {
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

    private final Boundary boundary;

    private final SimulationParameters parameters;

    public SimulationConfigurator(SimulationParameters parameters) throws InvalidSimulationConfigurationException {
        if (parameters.getValidationMessage().isPresent()) {
            throw new InvalidSimulationConfigurationException(parameters.getValidationMessage().get());
        }

        this.parameters = parameters;

        this.boundary = new Boundary(
            new Vector2D(0, 0),
            new Vector2D(parameters.mapWidth() - 1, parameters.mapHeight() - 1)
        );
    }

    public Simulation createSimulation() throws InvalidSimulationConfigurationException {
        return new Simulation(
            this.parameters,
            this.getWorldMap(this.getPlantGrowthIndicator()),
            this.getBehaviourIndicator(),
            this.getChildGenesIndicator()
        );
    }

    private PlantGrowthIndicator getPlantGrowthIndicator() throws InvalidSimulationConfigurationException {
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
                "Invalid plant growth indicator implementation: " + exception
            );
        }
    }

    private WorldMap getWorldMap(
        PlantGrowthIndicator plantGrowthIndicator
    ) throws InvalidSimulationConfigurationException {
        Class<? extends WorldMap> worldMap = WORLD_MAPS.get(parameters.worldMapVariant());

        try {
            if (worldMap == GlobeWorldMap.class) {
                return worldMap
                    .getDeclaredConstructor(Boundary.class, PlantGrowthIndicator.class)
                    .newInstance(this.boundary, plantGrowthIndicator);
            } else if (worldMap == UndergroundTunnelsWorldMap.class) {
                if (this.parameters.tunnelCount().isEmpty()) {
                    throw new InvalidSimulationConfigurationException(
                        "Tunnel count should be specified when using Underground Tunnels"
                    );
                }

                return worldMap
                    .getDeclaredConstructor(Boundary.class, int.class, PlantGrowthIndicator.class)
                    .newInstance(this.boundary, this.parameters.tunnelCount().get(), plantGrowthIndicator);
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
                "Invalid world map implementation: " + exception
            );
        }
    }

    private BehaviourIndicator getBehaviourIndicator() throws InvalidSimulationConfigurationException {
        try {
            return BEHAVIOUR_INDICATORS
                .get(parameters.animalBehaviourIndicatorVariant())
                .getDeclaredConstructor(int.class)
                .newInstance(parameters.geneCount());
        } catch (
            NoSuchMethodException |
            InstantiationException |
            IllegalAccessException |
            InvocationTargetException exception
        ) {
            throw new InvalidSimulationConfigurationException(
                "Invalid behaviour indicator implementation: " + exception
            );
        }
    }

    private ChildGenesIndicator getChildGenesIndicator() throws InvalidSimulationConfigurationException {
        try {
            return CHILD_GENES_INDICATORS
                .get(parameters.childGenesIndicatorVariant())
                .getDeclaredConstructor(int.class, int.class, int.class)
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
                "Invalid child genes indicator implementation: " + exception
            );
        }
    }
}
