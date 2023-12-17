package agh.ics.oop.model.element;

public interface AnimalBehaviourIndicator {
    /**
     * Indicate the new index of gene list for an object after it changes its orientation
     *
     * @param currentIndex The current gene index of the given object
     * @return New index of Gene List
     */
    int indicateGeneIndex(int currentIndex);
}
