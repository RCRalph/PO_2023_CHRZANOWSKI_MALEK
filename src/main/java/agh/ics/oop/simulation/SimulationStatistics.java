package agh.ics.oop.simulation;

import agh.ics.oop.model.element.gene.Gene;

import java.util.List;
import java.util.Map;

public record SimulationStatistics(
    int day,
    int animalCount,
    int plantCount,
    long freeFieldCount,
    double averageEnergyLevel,
    double averageLifespan,
    double averageChildren,
    Map<List<Gene>, Integer> genomePopularity
) {
    public List<Map.Entry<List<Gene>, Integer>> getOrderedGenomes() {
        return this.genomePopularity()
            .entrySet()
            .stream()
            .sorted((item1, item2) -> -Integer.compare(item1.getValue(), item2.getValue()))
            .limit(20)
            .toList();
    }

    public String getGenomePopularityString() {
        StringBuilder builder = new StringBuilder();

        for (var genomes : this.genomePopularity().entrySet()) {
            builder.append(Gene.listToString(genomes.getKey()));
            builder.append("=");
            builder.append(genomes.getValue());
            builder.append("|");
        }

        return builder.toString();
    }
}
