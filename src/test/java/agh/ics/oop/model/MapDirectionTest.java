package agh.ics.oop.model;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapDirectionTest {
    @Test
    public void testNext() {
        assertEquals(MapDirection.EAST, MapDirection.NORTH.next());
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.next());
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.next());
        assertEquals(MapDirection.NORTH, MapDirection.WEST.next());
    }

    @Test
    public void testPrevious() {
        assertEquals(MapDirection.WEST, MapDirection.NORTH.previous());
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.previous());
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.previous());
        assertEquals(MapDirection.NORTH, MapDirection.EAST.previous());
    }
}
