package agh.ics.oop.model.element;

import java.util.List;

public interface GenesIndicator {
    /**
     * Indicate the new genes which a child object should receive from its parent objects including mutations
     *
     * @param firstParent Reproduction information of the first parent
     * @param secondParent Reproduction information of the second parent
     * @return List of child's genes
     */
    List<Gene> indicateGenes(
        ReproductionInformation firstParent,
        ReproductionInformation secondParent
    );
}
