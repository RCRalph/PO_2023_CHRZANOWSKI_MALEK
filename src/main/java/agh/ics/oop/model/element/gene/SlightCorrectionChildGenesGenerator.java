package agh.ics.oop.model.element.gene;

import java.util.List;

public class SlightCorrectionChildGenesGenerator extends AbstractChildGenesIndicator {
    public SlightCorrectionChildGenesGenerator(int geneSize, int minMutationCount, int maxMutationCount) {
        super(geneSize, minMutationCount, maxMutationCount);
    }

    @Override
    protected void mutateGenes(List<Gene> genes) {
        for (int index : this.getMutationIndexSet()){
            if (this.random.nextBoolean()){
                genes.set(index, genes.get(index).next());
            } else {
                genes.set(index, genes.get(index).previous());
            }
        }
    }
}
