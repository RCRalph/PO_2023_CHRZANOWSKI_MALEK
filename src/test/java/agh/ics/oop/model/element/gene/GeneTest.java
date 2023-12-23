package agh.ics.oop.model.element.gene;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    public void testPrevious() {
        assertEquals(Gene.FORWARD.previous(), Gene.FORWARD_LEFT);
        assertEquals(Gene.FORWARD_LEFT.previous(), Gene.LEFT);
        assertEquals(Gene.LEFT.previous(), Gene.BACKWARD_LEFT);
        assertEquals(Gene.BACKWARD_LEFT.previous(), Gene.BACKWARD);
        assertEquals(Gene.BACKWARD.previous(), Gene.BACKWARD_RIGHT);
        assertEquals(Gene.BACKWARD_RIGHT.previous(), Gene.RIGHT);
        assertEquals(Gene.RIGHT.previous(), Gene.FORWARD_RIGHT);
        assertEquals(Gene.FORWARD_RIGHT.previous(), Gene.FORWARD);
    }

    @Test
    public void testNext() {
        assertEquals(Gene.FORWARD.next(), Gene.FORWARD_RIGHT);
        assertEquals(Gene.FORWARD_RIGHT.next(), Gene.RIGHT);
        assertEquals(Gene.RIGHT.next(), Gene.BACKWARD_RIGHT);
        assertEquals(Gene.BACKWARD_RIGHT.next(), Gene.BACKWARD);
        assertEquals(Gene.BACKWARD.next(), Gene.BACKWARD_LEFT);
        assertEquals(Gene.BACKWARD_LEFT.next(), Gene.LEFT);
        assertEquals(Gene.LEFT.next(), Gene.FORWARD_LEFT);
        assertEquals(Gene.FORWARD_LEFT.next(), Gene.FORWARD);
    }
}
