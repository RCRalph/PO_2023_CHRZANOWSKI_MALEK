package agh.ics.oop.model.element.gene;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public enum Gene {
    FORWARD,
    FORWARD_RIGHT,
    RIGHT,
    BACKWARD_RIGHT,
    BACKWARD,
    BACKWARD_LEFT,
    LEFT,
    FORWARD_LEFT;

    public Gene previous() {
        return values()[(this.ordinal() + values().length - 1) % values().length];
    }

    public Gene next() {
        return values()[(this.ordinal() + 1) % values().length];
    }

    public static List<Gene> randomList(int count) {
        Random random = new Random();
        List<Gene> result = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            result.add(values()[random.nextInt(values().length)]);
        }

        return result;
    }
}
