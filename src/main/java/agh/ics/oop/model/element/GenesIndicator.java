package agh.ics.oop.model.element;

import java.util.List;

public interface GenesIndicator {
    /**
     * Indicate the new genes which a child object should receive from its parent objects including mutations
     *
     * @param firstParentGenes List of genes of the first parent
     * @param firstParentEnergy Energy level of the first parent
     * @param secondParentGenes List of genes of the second parent
     * @param secondParentEnergy Energy level of the second parent
     * @return List of child's genes
     */
    List<Gene> indicateGenes(
        List<Gene> firstParentGenes,
        int firstParentEnergy,
        List<Gene> secondParentGenes,
        int secondParentEnergy
    );
}
