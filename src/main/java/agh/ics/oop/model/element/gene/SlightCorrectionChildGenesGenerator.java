package agh.ics.oop.model.element.gene;

import java.util.List;

public class SlightCorrectionChildGenesGenerator extends AbstractChildGenesIndicator {
    public SlightCorrectionChildGenesGenerator(int geneSize, int minMutationCount, int maxMutationCount) {
        super(geneSize, minMutationCount, maxMutationCount);
    }

    @Override
    protected void mutateGenes(List<Gene> genes) {
        for (int index : this.getMutationIndexSet()){
            genes.set(
                index,
                this.random.nextBoolean() ?
                    genes.get(index).next() :
                    genes.get(index).previous()
            );
        }
    }
}
