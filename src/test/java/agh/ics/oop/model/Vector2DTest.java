package agh.ics.oop.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Vector2DTest {
    private final Vector2D[] vectors = {
        new Vector2D(30, 53),
        new Vector2D(64, -25),
        new Vector2D(-30, 53),
        new Vector2D(-64, -25),
        new Vector2D(0, 0),
    };

    @Test
    public void testEquals() {
        assertEquals(new Vector2D(30, 53), vectors[0]);
        assertNotEquals(new Vector2D(64, -25), vectors[0]);
        assertNotEquals(new Vector2D(-30, 53), vectors[0]);
        assertNotEquals(new Vector2D(-64, -25), vectors[0]);
        assertNotEquals(new Vector2D(0, 0), vectors[0]);
        assertNotEquals(null, vectors[0]);

        assertEquals(new Vector2D(64, -25), vectors[1]);
        assertNotEquals(new Vector2D(30, 53), vectors[1]);
        assertNotEquals(new Vector2D(-30, 53), vectors[1]);
        assertNotEquals(new Vector2D(-64, -25), vectors[1]);
        assertNotEquals(new Vector2D(0, 0), vectors[1]);
        assertNotEquals(null, vectors[1]);

        assertEquals(new Vector2D(-30, 53), vectors[2]);
        assertNotEquals(new Vector2D(30, 53), vectors[2]);
        assertNotEquals(new Vector2D(64, -25), vectors[2]);
        assertNotEquals(new Vector2D(-64, -25), vectors[2]);
        assertNotEquals(new Vector2D(0, 0), vectors[2]);
        assertNotEquals(null, vectors[2]);

        assertEquals(new Vector2D(-64, -25), vectors[3]);
        assertNotEquals(new Vector2D(30, 53), vectors[3]);
        assertNotEquals(new Vector2D(64, -25), vectors[3]);
        assertNotEquals(new Vector2D(-30, 53), vectors[3]);
        assertNotEquals(new Vector2D(0, 0), vectors[3]);
        assertNotEquals(null, vectors[3]);

        assertEquals(new Vector2D(0, 0), vectors[4]);
        assertNotEquals(new Vector2D(30, 53), vectors[4]);
        assertNotEquals(new Vector2D(64, -25), vectors[4]);
        assertNotEquals(new Vector2D(-30, 53), vectors[4]);
        assertNotEquals(new Vector2D(-64, -25), vectors[4]);
        assertNotEquals(null, vectors[4]);
    }

    @Test
    public void testToString() {
        assertEquals("(30,53)", vectors[0].toString());
        assertEquals("(64,-25)", vectors[1].toString());
        assertEquals("(-30,53)", vectors[2].toString());
        assertEquals("(-64,-25)", vectors[3].toString());
        assertEquals("(0,0)", vectors[4].toString());
    }

    @Test
    public void testPrecedes() {
        assertTrue(vectors[0].precedes(vectors[0]));
        assertFalse(vectors[0].precedes(vectors[1]));
        assertFalse(vectors[0].precedes(vectors[2]));
        assertFalse(vectors[0].precedes(vectors[3]));
        assertFalse(vectors[0].precedes(vectors[4]));

        assertFalse(vectors[1].precedes(vectors[0]));
        assertTrue(vectors[1].precedes(vectors[1]));
        assertFalse(vectors[1].precedes(vectors[2]));
        assertFalse(vectors[1].precedes(vectors[3]));
        assertFalse(vectors[1].precedes(vectors[4]));

        assertTrue(vectors[2].precedes(vectors[0]));
        assertFalse(vectors[2].precedes(vectors[1]));
        assertTrue(vectors[2].precedes(vectors[2]));
        assertFalse(vectors[2].precedes(vectors[3]));
        assertFalse(vectors[2].precedes(vectors[4]));

        assertTrue(vectors[3].precedes(vectors[0]));
        assertTrue(vectors[3].precedes(vectors[1]));
        assertTrue(vectors[3].precedes(vectors[2]));
        assertTrue(vectors[3].precedes(vectors[3]));
        assertTrue(vectors[3].precedes(vectors[4]));

        assertTrue(vectors[4].precedes(vectors[0]));
        assertFalse(vectors[4].precedes(vectors[1]));
        assertFalse(vectors[4].precedes(vectors[2]));
        assertFalse(vectors[4].precedes(vectors[3]));
        assertTrue(vectors[4].precedes(vectors[4]));
    }

    @Test
    public void testFollows() {
        assertTrue(vectors[0].follows(vectors[0]));
        assertFalse(vectors[0].follows(vectors[1]));
        assertTrue(vectors[0].follows(vectors[2]));
        assertTrue(vectors[0].follows(vectors[3]));
        assertTrue(vectors[0].follows(vectors[4]));

        assertFalse(vectors[1].follows(vectors[0]));
        assertTrue(vectors[1].follows(vectors[1]));
        assertFalse(vectors[1].follows(vectors[2]));
        assertTrue(vectors[1].follows(vectors[3]));
        assertFalse(vectors[1].follows(vectors[4]));

        assertFalse(vectors[2].follows(vectors[0]));
        assertFalse(vectors[2].follows(vectors[1]));
        assertTrue(vectors[2].follows(vectors[2]));
        assertTrue(vectors[2].follows(vectors[3]));
        assertFalse(vectors[2].follows(vectors[4]));

        assertFalse(vectors[3].follows(vectors[0]));
        assertFalse(vectors[3].follows(vectors[1]));
        assertFalse(vectors[3].follows(vectors[2]));
        assertTrue(vectors[3].follows(vectors[3]));
        assertFalse(vectors[3].follows(vectors[4]));

        assertFalse(vectors[4].follows(vectors[0]));
        assertFalse(vectors[4].follows(vectors[1]));
        assertFalse(vectors[4].follows(vectors[2]));
        assertTrue(vectors[4].follows(vectors[3]));
        assertTrue(vectors[4].follows(vectors[4]));
    }

    @Test
    public void testUpperRight() {
        assertEquals(new Vector2D(30, 53), vectors[0].upperRight(vectors[0]));
        assertEquals(new Vector2D(64, 53), vectors[0].upperRight(vectors[1]));
        assertEquals(new Vector2D(30, 53), vectors[0].upperRight(vectors[2]));
        assertEquals(new Vector2D(30, 53), vectors[0].upperRight(vectors[3]));
        assertEquals(new Vector2D(30, 53), vectors[0].upperRight(vectors[4]));

        assertEquals(new Vector2D(64, 53), vectors[1].upperRight(vectors[0]));
        assertEquals(new Vector2D(64, -25), vectors[1].upperRight(vectors[1]));
        assertEquals(new Vector2D(64, 53), vectors[1].upperRight(vectors[2]));
        assertEquals(new Vector2D(64, -25), vectors[1].upperRight(vectors[3]));
        assertEquals(new Vector2D(64, 0), vectors[1].upperRight(vectors[4]));

        assertEquals(new Vector2D(30, 53), vectors[2].upperRight(vectors[0]));
        assertEquals(new Vector2D(64, 53), vectors[2].upperRight(vectors[1]));
        assertEquals(new Vector2D(-30, 53), vectors[2].upperRight(vectors[2]));
        assertEquals(new Vector2D(-30, 53), vectors[2].upperRight(vectors[3]));
        assertEquals(new Vector2D(0, 53), vectors[2].upperRight(vectors[4]));

        assertEquals(new Vector2D(30, 53), vectors[3].upperRight(vectors[0]));
        assertEquals(new Vector2D(64, -25), vectors[3].upperRight(vectors[1]));
        assertEquals(new Vector2D(-30, 53), vectors[3].upperRight(vectors[2]));
        assertEquals(new Vector2D(-64, -25), vectors[3].upperRight(vectors[3]));
        assertEquals(new Vector2D(0, 0), vectors[3].upperRight(vectors[4]));

        assertEquals(new Vector2D(30, 53), vectors[4].upperRight(vectors[0]));
        assertEquals(new Vector2D(64, 0), vectors[4].upperRight(vectors[1]));
        assertEquals(new Vector2D(0, 53), vectors[4].upperRight(vectors[2]));
        assertEquals(new Vector2D(0, 0), vectors[4].upperRight(vectors[3]));
        assertEquals(new Vector2D(0, 0), vectors[4].upperRight(vectors[4]));
    }

    @Test
    public void testLowerLeft() {
        assertEquals(new Vector2D(30, 53), vectors[0].lowerLeft(vectors[0]));
        assertEquals(new Vector2D(30, -25), vectors[0].lowerLeft(vectors[1]));
        assertEquals(new Vector2D(-30, 53), vectors[0].lowerLeft(vectors[2]));
        assertEquals(new Vector2D(-64, -25), vectors[0].lowerLeft(vectors[3]));
        assertEquals(new Vector2D(0, 0), vectors[0].lowerLeft(vectors[4]));

        assertEquals(new Vector2D(30, -25), vectors[1].lowerLeft(vectors[0]));
        assertEquals(new Vector2D(64, -25), vectors[1].lowerLeft(vectors[1]));
        assertEquals(new Vector2D(-30, -25), vectors[1].lowerLeft(vectors[2]));
        assertEquals(new Vector2D(-64, -25), vectors[1].lowerLeft(vectors[3]));
        assertEquals(new Vector2D(0, -25), vectors[1].lowerLeft(vectors[4]));

        assertEquals(new Vector2D(-30, 53), vectors[2].lowerLeft(vectors[0]));
        assertEquals(new Vector2D(-30, -25), vectors[2].lowerLeft(vectors[1]));
        assertEquals(new Vector2D(-30, 53), vectors[2].lowerLeft(vectors[2]));
        assertEquals(new Vector2D(-64, -25), vectors[2].lowerLeft(vectors[3]));
        assertEquals(new Vector2D(-30, 0), vectors[2].lowerLeft(vectors[4]));

        assertEquals(new Vector2D(-64, -25), vectors[3].lowerLeft(vectors[0]));
        assertEquals(new Vector2D(-64, -25), vectors[3].lowerLeft(vectors[1]));
        assertEquals(new Vector2D(-64, -25), vectors[3].lowerLeft(vectors[2]));
        assertEquals(new Vector2D(-64, -25), vectors[3].lowerLeft(vectors[3]));
        assertEquals(new Vector2D(-64, -25), vectors[3].lowerLeft(vectors[4]));

        assertEquals(new Vector2D(0, 0), vectors[4].lowerLeft(vectors[0]));
        assertEquals(new Vector2D(0, -25), vectors[4].lowerLeft(vectors[1]));
        assertEquals(new Vector2D(-30, 0), vectors[4].lowerLeft(vectors[2]));
        assertEquals(new Vector2D(-64, -25), vectors[4].lowerLeft(vectors[3]));
        assertEquals(new Vector2D(0, 0), vectors[4].lowerLeft(vectors[4]));
    }

    @Test
    public void testAdd() {
        assertEquals(new Vector2D(60, 106), vectors[0].add(vectors[0]));
        assertEquals(new Vector2D(94, 28), vectors[0].add(vectors[1]));
        assertEquals(new Vector2D(0, 106), vectors[0].add(vectors[2]));
        assertEquals(new Vector2D(-34, 28), vectors[0].add(vectors[3]));
        assertEquals(new Vector2D(30, 53), vectors[0].add(vectors[4]));

        assertEquals(new Vector2D(94, 28), vectors[1].add(vectors[0]));
        assertEquals(new Vector2D(128, -50), vectors[1].add(vectors[1]));
        assertEquals(new Vector2D(34, 28), vectors[1].add(vectors[2]));
        assertEquals(new Vector2D(0, -50), vectors[1].add(vectors[3]));
        assertEquals(new Vector2D(64, -25), vectors[1].add(vectors[4]));

        assertEquals(new Vector2D(0, 106), vectors[2].add(vectors[0]));
        assertEquals(new Vector2D(34, 28), vectors[2].add(vectors[1]));
        assertEquals(new Vector2D(-60, 106), vectors[2].add(vectors[2]));
        assertEquals(new Vector2D(-94, 28), vectors[2].add(vectors[3]));
        assertEquals(new Vector2D(-30, 53), vectors[2].add(vectors[4]));

        assertEquals(new Vector2D(-34, 28), vectors[3].add(vectors[0]));
        assertEquals(new Vector2D(0, -50), vectors[3].add(vectors[1]));
        assertEquals(new Vector2D(-94, 28), vectors[3].add(vectors[2]));
        assertEquals(new Vector2D(-128, -50), vectors[3].add(vectors[3]));
        assertEquals(new Vector2D(-64, -25), vectors[3].add(vectors[4]));

        assertEquals(new Vector2D(30, 53), vectors[4].add(vectors[0]));
        assertEquals(new Vector2D(64, -25), vectors[4].add(vectors[1]));
        assertEquals(new Vector2D(-30, 53), vectors[4].add(vectors[2]));
        assertEquals(new Vector2D(-64, -25), vectors[4].add(vectors[3]));
        assertEquals(new Vector2D(0, 0), vectors[4].add(vectors[4]));
    }

    @Test
    public void testSubtract() {
        assertEquals(new Vector2D(0, 0), vectors[0].subtract(vectors[0]));
        assertEquals(new Vector2D(-34, 78), vectors[0].subtract(vectors[1]));
        assertEquals(new Vector2D(60, 0), vectors[0].subtract(vectors[2]));
        assertEquals(new Vector2D(94, 78), vectors[0].subtract(vectors[3]));
        assertEquals(new Vector2D(30, 53), vectors[0].subtract(vectors[4]));

        assertEquals(new Vector2D(34, -78), vectors[1].subtract(vectors[0]));
        assertEquals(new Vector2D(0, 0), vectors[1].subtract(vectors[1]));
        assertEquals(new Vector2D(94, -78), vectors[1].subtract(vectors[2]));
        assertEquals(new Vector2D(128, 0), vectors[1].subtract(vectors[3]));
        assertEquals(new Vector2D(64, -25), vectors[1].subtract(vectors[4]));

        assertEquals(new Vector2D(-60, 0), vectors[2].subtract(vectors[0]));
        assertEquals(new Vector2D(-94, 78), vectors[2].subtract(vectors[1]));
        assertEquals(new Vector2D(0, 0), vectors[2].subtract(vectors[2]));
        assertEquals(new Vector2D(34, 78), vectors[2].subtract(vectors[3]));
        assertEquals(new Vector2D(-30, 53), vectors[2].subtract(vectors[4]));

        assertEquals(new Vector2D(-94, -78), vectors[3].subtract(vectors[0]));
        assertEquals(new Vector2D(-128, 0), vectors[3].subtract(vectors[1]));
        assertEquals(new Vector2D(-34, -78), vectors[3].subtract(vectors[2]));
        assertEquals(new Vector2D(0, 0), vectors[3].subtract(vectors[3]));
        assertEquals(new Vector2D(-64, -25), vectors[3].subtract(vectors[4]));

        assertEquals(new Vector2D(-30, -53), vectors[4].subtract(vectors[0]));
        assertEquals(new Vector2D(-64, 25), vectors[4].subtract(vectors[1]));
        assertEquals(new Vector2D(30, -53), vectors[4].subtract(vectors[2]));
        assertEquals(new Vector2D(64, 25), vectors[4].subtract(vectors[3]));
        assertEquals(new Vector2D(0, 0), vectors[4].subtract(vectors[4]));
    }

    @Test
    public void testOpposite() {
        assertEquals(new Vector2D(-30, -53), vectors[0].opposite());
        assertEquals(new Vector2D(-64, 25), vectors[1].opposite());
        assertEquals(new Vector2D(30, -53), vectors[2].opposite());
        assertEquals(new Vector2D(64, 25), vectors[3].opposite());
        assertEquals(new Vector2D(0, 0), vectors[4].opposite());
    }
}
