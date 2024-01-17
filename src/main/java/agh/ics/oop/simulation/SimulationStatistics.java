package agh.ics.oop.simulation;

import agh.ics.oop.model.element.gene.Gene;

import java.util.List;
import java.util.Map;

public record SimulationStatistics(
    int animalCount,
    int plantCount,
    long freeFieldCount,
    double averageEnergyLevel,
    double averageLifespan,
    double averageChildren,
    Map<List<Gene>, Integer> genomePopularity
) {}
