package agh.ics.oop.model.element.gene;

import java.util.List;

public record ReproductionInformation(
    int energyLevel,
    List<Gene> genes
) {}
