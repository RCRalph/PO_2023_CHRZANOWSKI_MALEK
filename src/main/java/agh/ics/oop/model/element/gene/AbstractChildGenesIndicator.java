package agh.ics.oop.model.element.gene;

import java.util.*;

abstract class AbstractChildGenesIndicator implements ChildGenesIndicator {
    protected final Random random = new Random();

    protected final int geneSize;

    protected final int minMutationCount;

    protected final int maxMutationCount;

    protected AbstractChildGenesIndicator(int geneSize, int minMutationCount, int maxMutationCount) {
        this.geneSize = geneSize;
        this.minMutationCount = minMutationCount;
        this.maxMutationCount = maxMutationCount;
    }

    private int getFirstParentShare(int energy1, int energy2) {
        return Math.round((float)energy1 * this.geneSize / (energy1 + energy2));
    }

    protected Set<Integer> getMutationIndexSet() {
        int mutationCount = this.random.nextInt(this.minMutationCount, this.maxMutationCount + 1);
        List<Integer> indexList = new ArrayList<>();

        for (int i = 0; i < this.geneSize; i++) {
            indexList.add(i);
        }

        Collections.shuffle(indexList);

        return new HashSet<>(indexList.subList(0, mutationCount));
    }

    abstract protected void mutateGenes(List<Gene> genes);

    @Override
    public List<Gene> getChildGenes(ReproductionInformation firstParent, ReproductionInformation secondParent) {
        int firstParentShare = this.getFirstParentShare(firstParent.energyLevel(), secondParent.energyLevel());

        List<Gene> result = new ArrayList<>(this.geneSize);
        if (random.nextBoolean()) {
            result.addAll(firstParent.genes().subList(0, firstParentShare));
            result.addAll(secondParent.genes().subList(firstParentShare, this.geneSize));
        } else {
            result.addAll(secondParent.genes().subList(0, this.geneSize - firstParentShare));
            result.addAll(firstParent.genes().subList(this.geneSize - firstParentShare, this.geneSize));
        }

        this.mutateGenes(result);

        return result;
    }
}
