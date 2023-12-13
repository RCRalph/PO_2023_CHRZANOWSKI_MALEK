package agh.ics.oop.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GeneGenerator {
    public static List<Gene> generate(int items) {
        List<Gene> result = new ArrayList<>(items);
        Random random = new Random();

        for (int i = 0; i < items; i++) {
            result.add(Gene.values()[random.nextInt(Gene.values().length)]);
        }

        return result;
    }
}
