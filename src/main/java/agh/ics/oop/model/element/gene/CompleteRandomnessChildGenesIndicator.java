package agh.ics.oop.model.element.gene;

import java.util.List;

public class CompleteRandomnessChildGenesIndicator extends AbstractChildGenesIndicator {
    public CompleteRandomnessChildGenesIndicator(int geneSize, int minMutationCount, int maxMutationCount) {
        super(geneSize, minMutationCount, maxMutationCount);
    }

    @Override
    protected void mutateGenes(List<Gene> genes) {
        for (int index : this.getMutationIndexSet()) {
            genes.set(index, Gene.values()[this.random.nextInt(Gene.values().length)]);
        }
    }
}
