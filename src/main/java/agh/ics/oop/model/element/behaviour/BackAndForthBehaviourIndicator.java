package agh.ics.oop.model.element.behaviour;

import agh.ics.oop.model.element.Animal;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class BackAndForthBehaviourIndicator implements BehaviourIndicator {
    private final int geneCount;

    private final Set<Animal> animalsWhichGenesMoveLeft = Collections.newSetFromMap(new IdentityHashMap<>());

    public BackAndForthBehaviourIndicator(int geneCount) {
        this.geneCount = geneCount;
    }

    @Override
    public int indicateGeneIndex(Animal animal) {
        if (animal.getGeneIndex() == 0) {
            this.animalsWhichGenesMoveLeft.remove(animal);
        } else if (animal.getGeneIndex() == this.geneCount - 1) {
            this.animalsWhichGenesMoveLeft.add(animal);
        }

        return animal.getGeneIndex() + (this.animalsWhichGenesMoveLeft.contains(animal) ? -1 : 1);
    }
}
