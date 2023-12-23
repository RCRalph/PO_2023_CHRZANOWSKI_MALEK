package agh.ics.oop.model.element.behaviour;

import agh.ics.oop.model.element.Animal;

import java.util.Collections;
import java.util.IdentityHashMap;
import java.util.Set;

public class BackAndForthBehaviourIndicator implements BehaviourIndicator {
    private final int geneCount;

    private final Set<Animal> animalsWhichGenesMoveRight = Collections.newSetFromMap(new IdentityHashMap<>());

    public BackAndForthBehaviourIndicator(int geneCount) {
        this.geneCount = geneCount;
    }

    @Override
    public int indicateGeneIndex(Animal animal) {
        int currentIndex = animal.getGeneIndex() + (this.animalsWhichGenesMoveRight.contains(animal) ? 1 : -1);

        if (currentIndex == 0) {
            this.animalsWhichGenesMoveRight.add(animal);
        } else if (currentIndex == this.geneCount - 1) {
            this.animalsWhichGenesMoveRight.remove(animal);
        }

        return currentIndex;
    }
}
