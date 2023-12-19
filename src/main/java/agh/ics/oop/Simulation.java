package agh.ics.oop;

import agh.ics.oop.model.element.BehaviourIndicator;
import agh.ics.oop.model.element.GenesIndicator;
import agh.ics.oop.model.map.WorldMap;

public class Simulation implements Runnable {
    //private final WorldMap map;

    //private final GenesIndicator genesIndicator;

    //private final BehaviourIndicator behaviourIndicator;

    public Simulation(SimulationParameters parameters) {
        if (parameters.mapWidth() <= 0) {
            throw new IllegalArgumentException("Map width should be greater than 0");
        } else if (parameters.mapHeight() <= 0) {
            throw new IllegalArgumentException("Map height should be greater than 0");
        } else if (parameters.startPlantCount() < 0) {
            throw new IllegalArgumentException("Start plant count should be greater than or equal to 0");
        } else if (parameters.dailyPlantGrowth() < 0) {
            throw new IllegalArgumentException("Daily plant growth should be greater than or equal to 0");
        } else if (parameters.startAnimalCount() <= 0) {
            throw new IllegalArgumentException("Start animal count should be greater than 0");
        } else if (parameters.minimumMutationCount() < 0) {
            throw new IllegalArgumentException("Minimal mutation count should be greater or equal to 0");
        } else if (parameters.maximumMutationCount() > parameters.geneCount()) {
            throw new IllegalArgumentException("Maximal mutation count should be less than or equal to gene count");
        } else if (parameters.geneCount() <= 0) {
            throw new IllegalArgumentException("Gene count should be greater than 0");
        }

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

    @Override
    public void run() {

    }
}
