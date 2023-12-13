package agh.ics.oop;

import agh.ics.oop.model.map.WorldMap;

public record SimulationParameters(
    int mapWidth,
    int mapHeight,
    WorldMap mapVariant,
    int startPlantCount,
    int plantEnergy,
    int dailyPlantGrowth,
    int startAnimalCount,
    int healthyAnimalEnergy,
    int reproductionEnergy,
    int geneCount,
    int minimumMutationCount,
    int maximumMutationCount
    // plantGrowthVariant
    // mutationVariant
    // behaviourVariant
) {
    private record Assertion(
       boolean predicate,
       String message
    ) {}

    public SimulationParameters {
        Assertion[] asserts = new Assertion[] {
            new Assertion(this.mapWidth() <= 0, "Map width should be greater than 0"),
            new Assertion(this.mapHeight() <= 0, "Map height should be greater than 0"),
            new Assertion(this.startPlantCount() < 0, "Start plant count should be greater than or equal to 0"),
            new Assertion(this.dailyPlantGrowth() < 0, "Daily plant growth should be greater than or equal to 0"),
            new Assertion(this.startAnimalCount() <= 0, "Start animal count should be greater than 0"),
            new Assertion(this.geneCount() <= 0, "Gene count should be greater than 0"),
            new Assertion(this.minimumMutationCount() < 0, "Minimal mutation count should be greater or equal to 0"),
            new Assertion(this.maximumMutationCount() > this.geneCount(), "Maximal mutation count should be less than or equal to gene count")
        };

        for (Assertion item : asserts) {
            if (item.predicate) {
                throw new IllegalArgumentException(item.message);
            }
        }
    }
}
