package agh.ics.oop.model.element;

import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.Random;

public class DarwinistAnimalComparator implements Comparator<Animal> {
    private final Map<Animal, Integer> orderOfEqualValues = new IdentityHashMap<>();

    private static final Random random = new Random();

    private synchronized int orderOfEqualValue(Animal animal) {
        return this.orderOfEqualValues.computeIfAbsent(animal, ignore -> random.nextInt());
    }

    /**
     * Compares animals in way to achieve order from most to least dominant.
     * Animals that have the same degree of dominance are randomly shuffled.
     *
     * @param o1 the first animal to be compared.
     * @param o2 the second animal to be compared.
     * @return Information about compared values {-1, 0, 1}
     */
    @Override
    public int compare(Animal o1, Animal o2) {
        if (o1.getEnergyLevel() != o2.getEnergyLevel()) {
            return -Integer.compare(o1.getEnergyLevel(), o2.getEnergyLevel()); // DESC order
        } else if (o1.getBirthday() != o2.getBirthday()) {
            return Integer.compare(o1.getBirthday(), o2.getBirthday()); // ASC order
        } else if (o1.getChildCount() != o2.getChildCount()) {
            return -Integer.compare(o1.getChildCount(), o2.getChildCount()); // DESC order
        }

        return Integer.compare(this.orderOfEqualValue(o1), this.orderOfEqualValue(o2)); // Random order
    }
}
