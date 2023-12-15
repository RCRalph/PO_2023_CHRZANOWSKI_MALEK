package agh.ics.oop;

import agh.ics.oop.model.map.AnimalBehaviourIndicator;
import agh.ics.oop.model.map.MutationIndicator;
import agh.ics.oop.model.map.PlantGrowthVariant;
import agh.ics.oop.model.map.WorldMap;

public record SimulationParameters(
    int mapWidth,
    int mapHeight,
    Class<WorldMap> worldMapClass,
    int startPlantCount,
    int plantEnergy,
    int dailyPlantGrowth,
    int startAnimalCount,
    int startAnimalEnergy,
    int healthyAnimalEnergy,
    int reproductionEnergy,
    int minimumMutationCount,
    int maximumMutationCount,
    Class<PlantGrowthVariant> plantGrowthVariantClass,
    Class<MutationIndicator> mutationIndicatorClass,
    int geneCount,
    Class<AnimalBehaviourIndicator> animalBehaviourIndicatorClass
) {
    public SimulationParameters {
        if (this.mapWidth() <= 0) {
            throw new IllegalArgumentException("Map width should be greater than 0");
        } else if (this.mapHeight() <= 0) {
            throw new IllegalArgumentException("Map height should be greater than 0");
        } else if (this.startPlantCount() < 0) {
            throw new IllegalArgumentException("Start plant count should be greater than or equal to 0");
        } else if (this.dailyPlantGrowth() < 0) {
            throw new IllegalArgumentException("Daily plant growth should be greater than or equal to 0");
        } else if (this.startAnimalCount() <= 0) {
            throw new IllegalArgumentException("Start animal count should be greater than 0");
        } else if (this.minimumMutationCount() < 0) {
            throw new IllegalArgumentException("Minimal mutation count should be greater or equal to 0");
        } else if (this.maximumMutationCount() > this.geneCount()) {
            throw new IllegalArgumentException("Maximal mutation count should be less than or equal to gene count");
        } else if (this.geneCount() <= 0) {
            throw new IllegalArgumentException("Gene count should be greater than 0");
        }
    }
}
