package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MapDirectionTest {
    @Test
    public void testLabel() {
        assertEquals("N", MapDirection.NORTH.toString());
        assertEquals("NE", MapDirection.NORTH_EAST.toString());
        assertEquals("E", MapDirection.EAST.toString());
        assertEquals("SE", MapDirection.SOUTH_EAST.toString());
        assertEquals("S", MapDirection.SOUTH.toString());
        assertEquals("SW", MapDirection.SOUTH_WEST.toString());
        assertEquals("W", MapDirection.WEST.toString());
        assertEquals("NW", MapDirection.NORTH_WEST.toString());
    }

    @Test
    public void testSquareVector() {
        assertEquals(new Vector2D(0, 1), MapDirection.NORTH.toSquareVector());
        assertEquals(new Vector2D(1, 1), MapDirection.NORTH_EAST.toSquareVector());
        assertEquals(new Vector2D(1, 0), MapDirection.EAST.toSquareVector());
        assertEquals(new Vector2D(1, -1), MapDirection.SOUTH_EAST.toSquareVector());
        assertEquals(new Vector2D(0, -1), MapDirection.SOUTH.toSquareVector());
        assertEquals(new Vector2D(-1, -1), MapDirection.SOUTH_WEST.toSquareVector());
        assertEquals(new Vector2D(-1, 0), MapDirection.WEST.toSquareVector());
        assertEquals(new Vector2D(-1, 1), MapDirection.NORTH_WEST.toSquareVector());
    }

    @Test
    public void testRotateByGene() {
        assertEquals(MapDirection.NORTH, MapDirection.NORTH.rotateByGene(Gene.FORWARD));
        assertEquals(MapDirection.NORTH_EAST, MapDirection.NORTH.rotateByGene(Gene.FORWARD_RIGHT));
        assertEquals(MapDirection.EAST, MapDirection.NORTH.rotateByGene(Gene.RIGHT));
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.NORTH.rotateByGene(Gene.BACKWARD_RIGHT));
        assertEquals(MapDirection.SOUTH, MapDirection.NORTH.rotateByGene(Gene.BACKWARD));
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.NORTH.rotateByGene(Gene.BACKWARD_LEFT));
        assertEquals(MapDirection.WEST, MapDirection.NORTH.rotateByGene(Gene.LEFT));
        assertEquals(MapDirection.NORTH_WEST, MapDirection.NORTH.rotateByGene(Gene.FORWARD_LEFT));

        assertEquals(MapDirection.NORTH_EAST, MapDirection.NORTH_EAST.rotateByGene(Gene.FORWARD));
        assertEquals(MapDirection.EAST, MapDirection.NORTH_EAST.rotateByGene(Gene.FORWARD_RIGHT));
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.NORTH_EAST.rotateByGene(Gene.RIGHT));
        assertEquals(MapDirection.SOUTH, MapDirection.NORTH_EAST.rotateByGene(Gene.BACKWARD_RIGHT));
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.NORTH_EAST.rotateByGene(Gene.BACKWARD));
        assertEquals(MapDirection.WEST, MapDirection.NORTH_EAST.rotateByGene(Gene.BACKWARD_LEFT));
        assertEquals(MapDirection.NORTH_WEST, MapDirection.NORTH_EAST.rotateByGene(Gene.LEFT));
        assertEquals(MapDirection.NORTH, MapDirection.NORTH_EAST.rotateByGene(Gene.FORWARD_LEFT));

        assertEquals(MapDirection.EAST, MapDirection.EAST.rotateByGene(Gene.FORWARD));
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.EAST.rotateByGene(Gene.FORWARD_RIGHT));
        assertEquals(MapDirection.SOUTH, MapDirection.EAST.rotateByGene(Gene.RIGHT));
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.EAST.rotateByGene(Gene.BACKWARD_RIGHT));
        assertEquals(MapDirection.WEST, MapDirection.EAST.rotateByGene(Gene.BACKWARD));
        assertEquals(MapDirection.NORTH_WEST, MapDirection.EAST.rotateByGene(Gene.BACKWARD_LEFT));
        assertEquals(MapDirection.NORTH, MapDirection.EAST.rotateByGene(Gene.LEFT));
        assertEquals(MapDirection.NORTH_EAST, MapDirection.EAST.rotateByGene(Gene.FORWARD_LEFT));

        assertEquals(MapDirection.SOUTH_EAST, MapDirection.SOUTH_EAST.rotateByGene(Gene.FORWARD));
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_EAST.rotateByGene(Gene.FORWARD_RIGHT));
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.SOUTH_EAST.rotateByGene(Gene.RIGHT));
        assertEquals(MapDirection.WEST, MapDirection.SOUTH_EAST.rotateByGene(Gene.BACKWARD_RIGHT));
        assertEquals(MapDirection.NORTH_WEST, MapDirection.SOUTH_EAST.rotateByGene(Gene.BACKWARD));
        assertEquals(MapDirection.NORTH, MapDirection.SOUTH_EAST.rotateByGene(Gene.BACKWARD_LEFT));
        assertEquals(MapDirection.NORTH_EAST, MapDirection.SOUTH_EAST.rotateByGene(Gene.LEFT));
        assertEquals(MapDirection.EAST, MapDirection.SOUTH_EAST.rotateByGene(Gene.FORWARD_LEFT));

        assertEquals(MapDirection.SOUTH, MapDirection.SOUTH.rotateByGene(Gene.FORWARD));
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.SOUTH.rotateByGene(Gene.FORWARD_RIGHT));
        assertEquals(MapDirection.WEST, MapDirection.SOUTH.rotateByGene(Gene.RIGHT));
        assertEquals(MapDirection.NORTH_WEST, MapDirection.SOUTH.rotateByGene(Gene.BACKWARD_RIGHT));
        assertEquals(MapDirection.NORTH, MapDirection.SOUTH.rotateByGene(Gene.BACKWARD));
        assertEquals(MapDirection.NORTH_EAST, MapDirection.SOUTH.rotateByGene(Gene.BACKWARD_LEFT));
        assertEquals(MapDirection.EAST, MapDirection.SOUTH.rotateByGene(Gene.LEFT));
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.SOUTH.rotateByGene(Gene.FORWARD_LEFT));

        assertEquals(MapDirection.SOUTH_WEST, MapDirection.SOUTH_WEST.rotateByGene(Gene.FORWARD));
        assertEquals(MapDirection.WEST, MapDirection.SOUTH_WEST.rotateByGene(Gene.FORWARD_RIGHT));
        assertEquals(MapDirection.NORTH_WEST, MapDirection.SOUTH_WEST.rotateByGene(Gene.RIGHT));
        assertEquals(MapDirection.NORTH, MapDirection.SOUTH_WEST.rotateByGene(Gene.BACKWARD_RIGHT));
        assertEquals(MapDirection.NORTH_EAST, MapDirection.SOUTH_WEST.rotateByGene(Gene.BACKWARD));
        assertEquals(MapDirection.EAST, MapDirection.SOUTH_WEST.rotateByGene(Gene.BACKWARD_LEFT));
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.SOUTH_WEST.rotateByGene(Gene.LEFT));
        assertEquals(MapDirection.SOUTH, MapDirection.SOUTH_WEST.rotateByGene(Gene.FORWARD_LEFT));

        assertEquals(MapDirection.WEST, MapDirection.WEST.rotateByGene(Gene.FORWARD));
        assertEquals(MapDirection.NORTH_WEST, MapDirection.WEST.rotateByGene(Gene.FORWARD_RIGHT));
        assertEquals(MapDirection.NORTH, MapDirection.WEST.rotateByGene(Gene.RIGHT));
        assertEquals(MapDirection.NORTH_EAST, MapDirection.WEST.rotateByGene(Gene.BACKWARD_RIGHT));
        assertEquals(MapDirection.EAST, MapDirection.WEST.rotateByGene(Gene.BACKWARD));
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.WEST.rotateByGene(Gene.BACKWARD_LEFT));
        assertEquals(MapDirection.SOUTH, MapDirection.WEST.rotateByGene(Gene.LEFT));
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.WEST.rotateByGene(Gene.FORWARD_LEFT));

        assertEquals(MapDirection.NORTH_WEST, MapDirection.NORTH_WEST.rotateByGene(Gene.FORWARD));
        assertEquals(MapDirection.NORTH, MapDirection.NORTH_WEST.rotateByGene(Gene.FORWARD_RIGHT));
        assertEquals(MapDirection.NORTH_EAST, MapDirection.NORTH_WEST.rotateByGene(Gene.RIGHT));
        assertEquals(MapDirection.EAST, MapDirection.NORTH_WEST.rotateByGene(Gene.BACKWARD_RIGHT));
        assertEquals(MapDirection.SOUTH_EAST, MapDirection.NORTH_WEST.rotateByGene(Gene.BACKWARD));
        assertEquals(MapDirection.SOUTH, MapDirection.NORTH_WEST.rotateByGene(Gene.BACKWARD_LEFT));
        assertEquals(MapDirection.SOUTH_WEST, MapDirection.NORTH_WEST.rotateByGene(Gene.LEFT));
        assertEquals(MapDirection.WEST, MapDirection.NORTH_WEST.rotateByGene(Gene.FORWARD_LEFT));
    }
}
