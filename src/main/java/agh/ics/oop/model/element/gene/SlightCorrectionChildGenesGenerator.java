package agh.ics.oop.model.element.gene;

import java.util.List;

public class SlightCorrectionChildGenesGenerator extends AbstractChildGenesIndicator {
    public SlightCorrectionChildGenesGenerator(int geneSize, int minMutationCount, int maxMutationCount) {
        super(geneSize, minMutationCount, maxMutationCount);
    }

    @Override
    protected void mutateGenes(List<Gene> genes) {
        // Your implementation here!
        // Remember to see what AbstractChildGenesIndicator has to offer
    }
}
