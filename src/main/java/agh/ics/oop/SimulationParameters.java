package agh.ics.oop;

import java.util.Optional;

public record SimulationParameters(
    String configurationName,
    int mapWidth,
    int mapHeight,
    String worldMapVariant,
    int tunnelCount,

    int geneCount,
    int minimumMutationCount,
    int maximumMutationCount,
    String childGenesIndicatorVariant,

    int startPlantCount,
    int dailyPlantGrowth,
    int plantEnergy,
    String plantGrowthIndicatorVariant,

    int startAnimalCount,
    int animalStartEnergy,
    int animalMoveEnergy,
    int healthyAnimalEnergy,
    int reproductionEnergy,
    String animalBehaviourIndicatorVariant
) {
    private Optional<String> validateWorldMap() {
        if (this.mapWidth() <= 0) {
            return Optional.of(
                "Map width should be greater than 0"
            );
        } else if (this.mapHeight() <= 0) {
            return Optional.of(
                "Map height should be greater than 0"
            );
        } else if (this.tunnelCount() * 2 > (this.mapWidth + 1) * (this.mapHeight + 1)) {
            return Optional.of(
                "Tunnel entrances count should not exceed total position count of the given map"
            );
        } else if (!Simulation.WORLD_MAPS.containsKey(this.worldMapVariant)) {
            return Optional.of(
                "World map variant should point to a valid class"
            );
        }

        return Optional.empty();
    }

    private Optional<String> validatePlants() {
        if (this.startPlantCount() < 0) {
            return Optional.of("Start plant count should be greater than or equal to 0");
        } else if (this.dailyPlantGrowth() < 0) {
            return Optional.of("Daily plant growth should be greater than or equal to 0");
        } else if (!Simulation.PLANT_GROWTH_INDICATORS.containsKey(this.plantGrowthIndicatorVariant)) {
            return Optional.of(
                "Plant growth indicator variant should point to a valid class"
            );
        }

        return Optional.empty();
    }

    private Optional<String> validateGenes() {
        if (this.geneCount() <= 0) {
            return Optional.of("Gene count should be greater than 0");
        } else if (this.minimumMutationCount() < 0) {
            return Optional.of("Minimal mutation count should be greater or equal to 0");
        } else if (this.maximumMutationCount() > this.geneCount()) {
            return Optional.of("Maximal mutation count should be less than or equal to gene count");
        } else if (!Simulation.CHILD_GENES_INDICATORS.containsKey(this.childGenesIndicatorVariant())) {
            return Optional.of(
                "Mutation variant should point to a valid class"
            );
        }

        return Optional.empty();
    }

    private Optional<String> validateAnimals() {
        if (this.startAnimalCount() <= 0) {
            return Optional.of("Start animal count should be greater than 0");
        } else if (this.animalStartEnergy() < 0) {
            return Optional.of("Animal's start energy should be greater than 0");
        } else if (this.animalMoveEnergy() < 0) {
            return Optional.of("Animal's move energy should be greater or equal to 0");
        } else if (0 < this.healthyAnimalEnergy() && this.healthyAnimalEnergy() > this.reproductionEnergy()) {
            return Optional.of("Reproduction energy should not exceed healthy animal energy level");
        } else if (this.reproductionEnergy() < 0) {
            return Optional.of("Reproduction energy should be greater than 0");
        } else if (!Simulation.BEHAVIOUR_INDICATORS.containsKey(this.animalBehaviourIndicatorVariant)) {
            return Optional.of(
                "Animal behaviour indicator variant should point to a valid class"
            );
        }

        return Optional.empty();
    }

    public Optional<String> getValidationMessage() {
        return this.validateWorldMap()
            .or(this::validatePlants)
            .or(this::validateGenes)
            .or(this::validateAnimals);
    }
}
