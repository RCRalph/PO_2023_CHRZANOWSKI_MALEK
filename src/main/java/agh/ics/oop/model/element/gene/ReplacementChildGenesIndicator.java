package agh.ics.oop.model.element.gene;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ReplacementChildGenesIndicator extends AbstractChildGenesIndicator {
    public ReplacementChildGenesIndicator(int geneSize, int minMutationCount, int maxMutationCount) {
        super(geneSize, minMutationCount, maxMutationCount);
    }

    @Override
    protected void mutateGenes(List<Gene> genes) {
        List<Integer> mutationIndexes = new ArrayList<>(this.getMutationIndexSet());

        int swapCount = this.random.nextInt((mutationIndexes.size() / 2) + 1);
        for (int i = 0; i < swapCount; i++) {
            Collections.swap(genes, mutationIndexes.get(2 * i), mutationIndexes.get(2 * i + 1));
        }

        for (int i = swapCount * 2; i < genes.size(); i++) {
            genes.set(mutationIndexes.get(i), Gene.values()[this.random.nextInt(Gene.values().length)]);
        }
    }
}
