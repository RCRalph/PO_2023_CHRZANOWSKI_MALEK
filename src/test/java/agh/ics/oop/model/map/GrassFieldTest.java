package agh.ics.oop.model.map;

import agh.ics.oop.model.MoveDirection;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.WorldElement;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GrassFieldTest {
    @Test
    public void testCanMoveTo() {
        GrassField map = new GrassField(20);

        assertDoesNotThrow(() -> {
            map.place(new Animal(new Vector2D(13, 4)));
            map.place(new Animal(new Vector2D(1, 18)));
            map.place(new Animal(new Vector2D(7, 12)));
            map.place(new Animal(new Vector2D(3, 15)));
            map.place(new Animal(new Vector2D(9, 6)));
            map.place(new Animal(new Vector2D(11, 2)));
        });

        assertFalse(map.canMoveTo(new Vector2D(13, 4)));
        assertFalse(map.canMoveTo(new Vector2D(1, 18)));
        assertFalse(map.canMoveTo(new Vector2D(7, 12)));
        assertFalse(map.canMoveTo(new Vector2D(3, 15)));
        assertFalse(map.canMoveTo(new Vector2D(9, 6)));
        assertFalse(map.canMoveTo(new Vector2D(11, 2)));

        assertTrue(map.canMoveTo(new Vector2D(0, 0)));
        assertTrue(map.canMoveTo(new Vector2D(5, 5)));
        assertTrue(map.canMoveTo(new Vector2D(12, 8)));
        assertTrue(map.canMoveTo(new Vector2D(2, 3)));
        assertTrue(map.canMoveTo(new Vector2D(15, 10)));
        assertTrue(map.canMoveTo(new Vector2D(6, 2)));
        assertTrue(map.canMoveTo(new Vector2D(9, 11)));
        assertTrue(map.canMoveTo(new Vector2D(1, 19)));
        assertTrue(map.canMoveTo(new Vector2D(14, 7)));
        assertTrue(map.canMoveTo(new Vector2D(8, 13)));
        assertTrue(map.canMoveTo(new Vector2D(20, 20)));
        assertTrue(map.canMoveTo(new Vector2D(-1, -1)));
        assertTrue(map.canMoveTo(new Vector2D(-1, 0)));
        assertTrue(map.canMoveTo(new Vector2D(0, -1)));
    }

    @Test
    public void testPlace() {
        GrassField map = new GrassField(20);

        assertDoesNotThrow(() -> {
            map.place(new Animal(new Vector2D(13, 4)));
            map.place(new Animal(new Vector2D(1, 18)));
            map.place(new Animal(new Vector2D(7, 12)));
            map.place(new Animal(new Vector2D(3, 15)));
            map.place(new Animal(new Vector2D(9, 6)));
            map.place(new Animal(new Vector2D(11, 2)));
        });

        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2D(13, 4))));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2D(1, 18))));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2D(7, 12))));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2D(3, 15))));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2D(9, 6))));
        assertThrows(PositionAlreadyOccupiedException.class, () -> map.place(new Animal(new Vector2D(11, 2))));
    }

    @Test
    public void testMove() {
        GrassField map = new GrassField(30);

        assertDoesNotThrow(() -> {
            map.place(new Animal(new Vector2D(3, 7)));
            map.place(new Animal(new Vector2D(9, 12)));
            map.place(new Animal(new Vector2D(2, 14)));
            map.place(new Animal(new Vector2D(8, 5)));
            map.place(new Animal(new Vector2D(0, 10)));
            map.place(new Animal(new Vector2D(6, 11)));
            map.place(new Animal(new Vector2D(1, 13)));
            map.place(new Animal(new Vector2D(4, 15)));
            map.place(new Animal(new Vector2D(7, 3)));
            map.place(new Animal(new Vector2D(12, 9)));
        });

        WorldElement firstAnimal = map.objectAt(new Vector2D(1, 13)),
            secondAnimal = map.objectAt(new Vector2D(2, 14));

        map.move(firstAnimal, MoveDirection.FORWARD);
        map.move(firstAnimal, MoveDirection.RIGHT);
        map.move(firstAnimal, MoveDirection.FORWARD);
        map.move(secondAnimal, MoveDirection.LEFT);
        map.move(secondAnimal, MoveDirection.FORWARD);
        map.move(firstAnimal, MoveDirection.LEFT);
        map.move(firstAnimal, MoveDirection.FORWARD);

        assertSame(firstAnimal, map.objectAt(new Vector2D(1, 15)));
        assertSame(secondAnimal, map.objectAt(new Vector2D(2, 14)));
    }

    @Test
    public void testIsOccupied() {
        GrassField map = new GrassField(10);

        assertDoesNotThrow(() -> {
            map.place(new Animal(new Vector2D(3, 7)));
            map.place(new Animal(new Vector2D(9, 12)));
            map.place(new Animal(new Vector2D(2, 14)));
            map.place(new Animal(new Vector2D(8, 5)));
            map.place(new Animal(new Vector2D(0, 10)));
            map.place(new Animal(new Vector2D(6, 11)));
            map.place(new Animal(new Vector2D(1, 13)));
            map.place(new Animal(new Vector2D(4, 15)));
            map.place(new Animal(new Vector2D(7, 3)));
            map.place(new Animal(new Vector2D(12, 9)));
            map.place(new Animal(new Vector2D(19, 1)));
        });

        for (WorldElement item : map.getElements()) {
            assertTrue(map.isOccupied(item.getPosition()));
        }

        // Positions outside possible grass area and placed animals
        assertFalse(map.isOccupied(new Vector2D(17, 6)));
        assertFalse(map.isOccupied(new Vector2D(50, 18)));
        assertFalse(map.isOccupied(new Vector2D(13, 2)));
        assertFalse(map.isOccupied(new Vector2D(10, 16)));
        assertFalse(map.isOccupied(new Vector2D(14, 11)));
        assertFalse(map.isOccupied(new Vector2D(30, 9)));
        assertFalse(map.isOccupied(new Vector2D(8, 74)));
        assertFalse(map.isOccupied(new Vector2D(60, 20)));
        assertFalse(map.isOccupied(new Vector2D(12, 4)));
    }

    @Test
    public void testObjectAt() {
        GrassField map = new GrassField(15);

        List<Animal> animals = List.of(
            new Animal(new Vector2D(3, 7)),
            new Animal(new Vector2D(9, 12)),
            new Animal(new Vector2D(2, 14)),
            new Animal(new Vector2D(8, 5)),
            new Animal(new Vector2D(0, 10)),
            new Animal(new Vector2D(6, 11)),
            new Animal(new Vector2D(1, 13)),
            new Animal(new Vector2D(7, 3)),
            new Animal(new Vector2D(12, 9))
        );

        for (Animal animal : animals) {
            assertDoesNotThrow(() -> map.place(animal));
        }

        for (Animal animal : animals) {
            assertSame(animal, map.objectAt(animal.getPosition()));
        }
    }

    @Test
    public void testGetElements() {
        int grassCount = 15;

        GrassField map = new GrassField(grassCount);

        List<Animal> animals = List.of(
            new Animal(new Vector2D(3, 7)),
            new Animal(new Vector2D(9, 12)),
            new Animal(new Vector2D(2, 14)),
            new Animal(new Vector2D(8, 5)),
            new Animal(new Vector2D(0, 10)),
            new Animal(new Vector2D(6, 11)),
            new Animal(new Vector2D(1, 13)),
            new Animal(new Vector2D(7, 3)),
            new Animal(new Vector2D(12, 9))
        );

        for (Animal animal : animals) {
            assertDoesNotThrow(() -> map.place(animal));
        }

        assertEquals(grassCount + animals.size(), map.getElements().size());
        assertTrue(map.getElements().containsAll(animals));
    }

    @Test
    public void testGetGrassPositions() {
        final Vector2D LOWER_LEFT = new Vector2D(0, 0);

        GrassField map;

        assertThrows(IllegalArgumentException.class, () -> new GrassField(-10));

        for (int i = 0; i < 10; i++) {
            for (int grassCount : new int[] {0, 1, 2, 5, 10, 20, 50, 100, 200, 500, 1000}) {
                map = new GrassField(grassCount);

                Vector2D upperRight = new Vector2D(
                    (int) Math.sqrt(grassCount * 10),
                    (int) Math.sqrt(grassCount * 10)
                );

                assertEquals(grassCount, map.getGrassPositions().size());

                for (Vector2D position : map.getGrassPositions()) {
                    assertTrue(LOWER_LEFT.precedes(position));
                    assertTrue(upperRight.follows(position));
                }
            }
        }
    }
}
