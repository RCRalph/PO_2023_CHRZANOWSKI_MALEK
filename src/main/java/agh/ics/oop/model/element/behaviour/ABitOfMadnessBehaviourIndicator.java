package agh.ics.oop.model.element.behaviour;

import agh.ics.oop.model.element.Animal;

import java.util.Random;

public class ABitOfMadnessBehaviourIndicator implements BehaviourIndicator {
    private final int geneCount;

    private final Random random = new Random();

    public ABitOfMadnessBehaviourIndicator(int geneCount) {
        this.geneCount = geneCount;
    }

    @Override
    public int indicateGeneIndex(Animal animal) {
        if (this.random.nextInt(0, 10) < 8) {
            return (animal.getGeneIndex() + 1) % this.geneCount;
        } else {
            return this.random.nextInt(geneCount);
        }
    }
}
