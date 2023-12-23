package agh.ics.oop.model.element.behaviour;

import agh.ics.oop.model.element.Animal;

public interface BehaviourIndicator {
    /**
     * Indicate the new index of gene list for an object after it changes its orientation.
     *
     * @param animal The animal of which gene index is indicated.
     * @return New index of Gene List
     */
    int indicateGeneIndex(Animal animal);
}
