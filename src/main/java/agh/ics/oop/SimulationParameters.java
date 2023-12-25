package agh.ics.oop;

import com.google.gson.JsonObject;

import java.util.Optional;
import java.util.Set;

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
    private Optional<String> validateName(Set<String> names) {
        if (this.configurationName.isBlank()) {
            return Optional.of("Configuration name cannot be blank");
        } else if (names.contains(this.configurationName)) {
            return Optional.of("Configuration name has to be unique");
        }

        return Optional.empty();
    }

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
        } else if (0 > this.healthyAnimalEnergy() || this.healthyAnimalEnergy() > this.reproductionEnergy()) {
            return Optional.of("Healthy animal energy should not exceed reproduction energy level");
        } else if (!Simulation.BEHAVIOUR_INDICATORS.containsKey(this.animalBehaviourIndicatorVariant)) {
            return Optional.of(
                "Animal behaviour indicator variant should point to a valid class"
            );
        }

        return Optional.empty();
    }

    public Optional<String> getValidationMessage() {
        return this.getValidationMessage(Set.of());
    }

    public Optional<String> getValidationMessage(Set<String> names) {
        return this.validateName(names)
            .or(this::validateWorldMap)
            .or(this::validatePlants)
            .or(this::validateGenes)
            .or(this::validateAnimals);
    }

    public JsonObject toJsonObject() {
        JsonObject result = new JsonObject();

        result.addProperty("configuration-name", this.configurationName());
        result.addProperty("map-width", this.mapWidth());
        result.addProperty("map-height", this.mapHeight());
        result.addProperty("world-map-variant", this.worldMapVariant());
        result.addProperty("tunnel-count", this.tunnelCount());
        result.addProperty("gene-count", this.geneCount());
        result.addProperty("min-mutation-count", this.minimumMutationCount());
        result.addProperty("max-mutation-count", this.maximumMutationCount());
        result.addProperty("child-genes-indicator-variant", this.childGenesIndicatorVariant());
        result.addProperty("start-plant-count", this.startAnimalCount());
        result.addProperty("daily-plant-growth", this.dailyPlantGrowth());
        result.addProperty("plant-energy", this.plantEnergy());
        result.addProperty("plant-growth-indicator-variant", this.plantGrowthIndicatorVariant());
        result.addProperty("start-animal-count", this.startAnimalCount());
        result.addProperty("animal-start-energy", this.animalStartEnergy());
        result.addProperty("animal-move-energy", this.animalMoveEnergy());
        result.addProperty("healthy-animal-energy", this.healthyAnimalEnergy());
        result.addProperty("reproduction-energy", this.reproductionEnergy());
        result.addProperty("animal-behaviour-indicator-variant", this.animalBehaviourIndicatorVariant());

        return result;
    }

    public static SimulationParameters fromJsonObject(JsonObject jsonObject) {
        return new SimulationParameters(
            jsonObject.get("configuration-name").getAsString(),
            jsonObject.get("map-width").getAsInt(),
            jsonObject.get("map-height").getAsInt(),
            jsonObject.get("world-map-variant").getAsString(),
            jsonObject.get("tunnel-count").getAsInt(),
            jsonObject.get("gene-count").getAsInt(),
            jsonObject.get("min-mutation-count").getAsInt(),
            jsonObject.get("max-mutation-count").getAsInt(),
            jsonObject.get("child-genes-indicator-variant").getAsString(),
            jsonObject.get("start-plant-count").getAsInt(),
            jsonObject.get("daily-plant-growth").getAsInt(),
            jsonObject.get("plant-energy").getAsInt(),
            jsonObject.get("plant-growth-indicator-variant").getAsString(),
            jsonObject.get("start-animal-count").getAsInt(),
            jsonObject.get("animal-start-energy").getAsInt(),
            jsonObject.get("animal-move-energy").getAsInt(),
            jsonObject.get("healthy-animal-energy").getAsInt(),
            jsonObject.get("reproduction-energy").getAsInt(),
            jsonObject.get("animal-behaviour-indicator-variant").getAsString()
        );
    }
}
