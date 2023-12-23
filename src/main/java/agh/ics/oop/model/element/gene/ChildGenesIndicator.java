package agh.ics.oop.model.element.gene;

import java.util.List;

public interface ChildGenesIndicator {
    /**
     * Generate child's genes using its parents genes and introduce mutations
     *
     * @param firstParent Reproduction information of the first parent.
     * @param secondParent Reproduction information of the second parent.
     * @return List of genes of child including mutations.
     */
    List<Gene> getChildGenes(ReproductionInformation firstParent, ReproductionInformation secondParent);
}
