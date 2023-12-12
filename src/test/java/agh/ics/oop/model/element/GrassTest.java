package agh.ics.oop.model.element;

import agh.ics.oop.model.Vector2D;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GrassTest {
    @Test
    public void testIsAt() {
        assertTrue(new Grass(new Vector2D(2, 2)).isAt(new Vector2D(2, 2)));
        assertTrue(new Grass(new Vector2D(0, 0)).isAt(new Vector2D(0, 0)));
        assertTrue(new Grass(new Vector2D(1, 3)).isAt(new Vector2D(1, 3)));
        assertTrue(new Grass(new Vector2D(4, 3)).isAt(new Vector2D(4, 3)));
        assertTrue(new Grass(new Vector2D(3, 1)).isAt(new Vector2D(3, 1)));

        assertFalse(new Grass(new Vector2D(0, 0)).isAt(new Vector2D(2, 2)));
        assertFalse(new Grass(new Vector2D(2, 2)).isAt(new Vector2D(0, 0)));
        assertFalse(new Grass(new Vector2D(3, 1)).isAt(new Vector2D(1, 3)));
        assertFalse(new Grass(new Vector2D(1, 4)).isAt(new Vector2D(4, 3)));
        assertFalse(new Grass(new Vector2D(1, 3)).isAt(new Vector2D(3, 1)));
    }
}
