package agh.ics.oop.model.element;

import java.util.List;

public record ReproductionInformation(
    int energyLevel,
    List<Gene> genes
) {}
