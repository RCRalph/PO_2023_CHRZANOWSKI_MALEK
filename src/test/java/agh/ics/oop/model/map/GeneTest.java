package agh.ics.oop.model.map;

import agh.ics.oop.model.Gene;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GeneTest {
    @Test
    public void testOrdinal() {
        assertEquals(Gene.FORWARD.ordinal(), 0);
        assertEquals(Gene.FORWARD_RIGHT.ordinal(), 1);
        assertEquals(Gene.RIGHT.ordinal(), 2);
        assertEquals(Gene.BACKWARD_RIGHT.ordinal(), 3);
        assertEquals(Gene.BACKWARD.ordinal(), 4);
        assertEquals(Gene.BACKWARD_LEFT.ordinal(), 5);
        assertEquals(Gene.LEFT.ordinal(), 6);
        assertEquals(Gene.FORWARD_LEFT.ordinal(), 7);
    }
}
