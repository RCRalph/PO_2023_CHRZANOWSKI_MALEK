package agh.ics.oop.model.element;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AnimalTest {
    /*@Test
    public void testIsAt() {
        assertTrue(new Animal().isAt(new Vector2D(2, 2)));
        assertFalse(new Animal().isAt(new Vector2D(0, 0)));
        assertFalse(new Animal().isAt(new Vector2D(2, -8)));
        assertFalse(new Animal().isAt(new Vector2D(-5, 12)));
        assertFalse(new Animal().isAt(new Vector2D(-4, -3)));

        assertTrue(new Animal(new Vector2D(2, 2)).isAt(new Vector2D(2, 2)));
        assertTrue(new Animal(new Vector2D(0, 0)).isAt(new Vector2D(0, 0)));
        assertTrue(new Animal(new Vector2D(1, 3)).isAt(new Vector2D(1, 3)));
        assertTrue(new Animal(new Vector2D(4, 3)).isAt(new Vector2D(4, 3)));
        assertTrue(new Animal(new Vector2D(3, 1)).isAt(new Vector2D(3, 1)));

        assertFalse(new Animal(new Vector2D(0, 0)).isAt(new Vector2D(2, 2)));
        assertFalse(new Animal(new Vector2D(2, 2)).isAt(new Vector2D(0, 0)));
        assertFalse(new Animal(new Vector2D(3, 1)).isAt(new Vector2D(1, 3)));
        assertFalse(new Animal(new Vector2D(1, 4)).isAt(new Vector2D(4, 3)));
        assertFalse(new Animal(new Vector2D(1, 3)).isAt(new Vector2D(3, 1)));
    }

    @Test
    public void testMove() {
        Animal animal;
        MoveValidator validator = new RectangularMap(5, 5);

        animal = new Animal();
        assertEquals(MapDirection.NORTH, animal.getOrientation());
        assertEquals(new Vector2D(2, 2), animal.getPosition());

        animal = new Animal(new Vector2D(2, 3));
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals(MapDirection.NORTH, animal.getOrientation());
        assertEquals(new Vector2D(2, 4), animal.getPosition());

        animal = new Animal(new Vector2D(2, 3));
        animal.move(validator, MoveDirection.LEFT);
        assertEquals(MapDirection.WEST, animal.getOrientation());
        assertEquals(new Vector2D(2, 3), animal.getPosition());

        animal = new Animal(new Vector2D(2, 3));
        animal.move(validator, MoveDirection.BACKWARD);
        assertEquals(MapDirection.NORTH, animal.getOrientation());
        assertEquals(new Vector2D(2, 2), animal.getPosition());

        animal = new Animal(new Vector2D(2, 3));
        animal.move(validator, MoveDirection.RIGHT);
        assertEquals(MapDirection.EAST, animal.getOrientation());
        assertEquals(new Vector2D(2, 3), animal.getPosition());

        animal = new Animal(new Vector2D(2, 4));
        animal.move(validator, MoveDirection.RIGHT);
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals(MapDirection.EAST, animal.getOrientation());
        assertEquals(new Vector2D(3, 4), animal.getPosition());

        animal = new Animal(new Vector2D(2, 4));
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals(MapDirection.NORTH, animal.getOrientation());
        assertEquals(new Vector2D(2, 4), animal.getPosition());

        animal = new Animal(new Vector2D(0, 4));
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.BACKWARD);
        assertEquals(MapDirection.WEST, animal.getOrientation());
        assertEquals(new Vector2D(1, 4), animal.getPosition());

        animal = new Animal(new Vector2D(0, 4));
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals(MapDirection.WEST, animal.getOrientation());
        assertEquals(new Vector2D(0, 4), animal.getPosition());

        animal = new Animal(new Vector2D(0, 4));
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals(MapDirection.SOUTH, animal.getOrientation());
        assertEquals(new Vector2D(0, 3), animal.getPosition());

        animal = new Animal(new Vector2D(1, 2));
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.RIGHT);
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals(MapDirection.NORTH, animal.getOrientation());
        assertEquals(new Vector2D(1, 3), animal.getPosition());
    }

    @Test
    public void testToString() {
        Animal animal;
        MoveValidator validator = new RectangularMap(5, 5);

        animal = new Animal();
        assertEquals("N", animal.toString());

        animal = new Animal(new Vector2D(2, 3));
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals("N", animal.toString());

        animal = new Animal(new Vector2D(2, 3));
        animal.move(validator, MoveDirection.LEFT);
        assertEquals("W", animal.toString());

        animal = new Animal(new Vector2D(2, 3));
        animal.move(validator, MoveDirection.BACKWARD);
        assertEquals("N", animal.toString());

        animal = new Animal(new Vector2D(2, 3));
        animal.move(validator, MoveDirection.RIGHT);
        assertEquals("E", animal.toString());

        animal = new Animal(new Vector2D(2, 4));
        animal.move(validator, MoveDirection.RIGHT);
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals("E", animal.toString());

        animal = new Animal(new Vector2D(2, 4));
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals("N", animal.toString());

        animal = new Animal(new Vector2D(0, 4));
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.BACKWARD);
        assertEquals("W", animal.toString());

        animal = new Animal(new Vector2D(0, 4));
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals("S", animal.toString());

        animal = new Animal(new Vector2D(1, 2));
        animal.move(validator, MoveDirection.LEFT);
        animal.move(validator, MoveDirection.RIGHT);
        animal.move(validator, MoveDirection.FORWARD);
        assertEquals("N", animal.toString());
    }*/
}
