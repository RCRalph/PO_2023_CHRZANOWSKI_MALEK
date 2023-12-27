package agh.ics.oop.model.element.behaviour;

import agh.ics.oop.model.element.Animal;

public class CompletePredestinationBehaviourIndicator implements BehaviourIndicator {
    private final int geneCount;

    public CompletePredestinationBehaviourIndicator(int geneCount) {
        this.geneCount = geneCount;
    }

    @Override
    public int indicateGeneIndex(Animal animal) {
        return (animal.getGeneIndex() + 1) % this.geneCount;
    }
}
