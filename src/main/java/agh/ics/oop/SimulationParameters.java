package agh.ics.oop;

public record SimulationParameters(
    int mapWidth,
    int mapHeight,
    String worldMapVariant,
    int startPlantCount,
    int plantEnergy,
    int dailyPlantGrowth,
    int startAnimalCount,
    int animalStartEnergy,
    int animalMoveEnergy,
    int healthyAnimalEnergy,
    int reproductionEnergy,
    int minimumMutationCount,
    int maximumMutationCount,
    String plantGrowthIndicatorVariant,
    String genesIndicatorVariant,
    int geneCount,
    String animalBehaviourIndicatorVariant
) {}
