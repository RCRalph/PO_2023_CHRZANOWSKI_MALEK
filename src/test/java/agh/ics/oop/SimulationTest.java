package agh.ics.oop;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.map.RectangularMap;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SimulationTest {
    /*@Test
    public void testRun() {
        // Simulation 1
        Simulation simulation = new Simulation(
            OptionsParser.parse(new String[] {
                "f", "b", "r", "l", "f", "f", "r", "r",
                "f", "f", "f", "f", "f", "f", "f", "f"
            }),
            List.of(new Vector2D(2,2), new Vector2D(3,4)),
            new RectangularMap(5, 5)
        );

        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertEquals(2, animals.size());

        assertEquals(MapDirection.SOUTH, animals.get(0).getOrientation());
        assertEquals(new Vector2D(2, 0), animals.get(0).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(1).getOrientation());
        assertEquals(new Vector2D(3, 4), animals.get(1).getPosition());

        // Simulation 2
        simulation = new Simulation(
            OptionsParser.parse(new String[] {
                "f"
            }),
            List.of(new Vector2D(1,3), new Vector2D(0,2)),
            new RectangularMap(5, 5)
        );

        simulation.run();

        animals = simulation.getAnimals();

        assertEquals(2, animals.size());

        assertEquals(MapDirection.NORTH, animals.get(0).getOrientation());
        assertEquals(new Vector2D(1, 4), animals.get(0).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(1).getOrientation());
        assertEquals(new Vector2D(0, 2), animals.get(1).getPosition());

        // Simulation 3
        simulation = new Simulation(
            OptionsParser.parse(new String[] {
                "r", "f", "f", "f", "f", "f", "f", "f",
                "l", "r", "r", "b", "f", "f", "f", "f"
            }),
            List.of(new Vector2D(1,3), new Vector2D(0,2)),
            new RectangularMap(5, 5)
        );

        simulation.run();

        animals = simulation.getAnimals();

        assertEquals(2, animals.size());

        assertEquals(MapDirection.EAST, animals.get(0).getOrientation());
        assertEquals(new Vector2D(4, 3), animals.get(0).getPosition());

        assertEquals(MapDirection.EAST, animals.get(1).getOrientation());
        assertEquals(new Vector2D(2, 4), animals.get(1).getPosition());

        // Simulation 4
        simulation = new Simulation(
            OptionsParser.parse(new String[] {
                "r", "f", "f", "f", "f", "f", "f", "f",
                "l", "r", "r", "b", "f", "f", "f", "f"
            }),
            List.of(new Vector2D(1,3), new Vector2D(0,2)),
            new RectangularMap(5, 5)
        );

        simulation.run();

        animals = simulation.getAnimals();

        assertEquals(2, animals.size());

        assertEquals(MapDirection.EAST, animals.get(0).getOrientation());
        assertEquals(new Vector2D(4, 3), animals.get(0).getPosition());

        assertEquals(MapDirection.EAST, animals.get(1).getOrientation());
        assertEquals(new Vector2D(2, 4), animals.get(1).getPosition());

        // Simulation 5
        simulation = new Simulation(
            OptionsParser.parse(new String[] {
                "l", "f", "r", "b", "l", "f", "r", "b", "l", "f",
                "r", "b", "l", "f", "r", "b", "l", "f", "r", "b",
                "l", "f", "f", "r", "f", "r", "b", "l", "f", "r"
            }),
            List.of(
                new Vector2D(4, 4), new Vector2D(2, 4),
                new Vector2D(2, 2), new Vector2D(0, 4)
            ),
            new RectangularMap(5, 5)
        );

        simulation.run();

        animals = simulation.getAnimals();

        assertEquals(4, animals.size());

        assertEquals(MapDirection.SOUTH, animals.get(0).getOrientation());
        assertEquals(new Vector2D(4, 2), animals.get(0).getPosition());

        assertEquals(MapDirection.SOUTH, animals.get(1).getOrientation());
        assertEquals(new Vector2D(2, 4), animals.get(1).getPosition());

        assertEquals(MapDirection.EAST, animals.get(2).getOrientation());
        assertEquals(new Vector2D(2, 2), animals.get(2).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(3).getOrientation());
        assertEquals(new Vector2D(0, 0), animals.get(3).getPosition());

        // Simulation 6
        simulation = new Simulation(
            OptionsParser.parse(new String[] {
                "r", "f", "b", "f", "l", "r", "r",
                "l", "l", "r", "b", "l", "l", "b",
                "l", "l", "b", "f", "f", "r", "b",
                "l", "b", "r", "f", "f", "l", "f",
                "r", "f", "f", "r", "l", "b", "r"
            }),
            List.of(new Vector2D(8,3), new Vector2D(12,7)),
            new RectangularMap(15, 15)
        );

        simulation.run();

        animals = simulation.getAnimals();

        assertEquals(2, animals.size());

        assertEquals(MapDirection.SOUTH, animals.get(0).getOrientation());
        assertEquals(new Vector2D(7, 2), animals.get(0).getPosition());

        assertEquals(MapDirection.EAST, animals.get(1).getOrientation());
        assertEquals(new Vector2D(10, 11), animals.get(1).getPosition());

        // Simulation 7
        simulation = new Simulation(
            OptionsParser.parse(new String[] {
                "b", "f", "l", "b", "f", "r", "r", "r", "r", "b", "f", "l",
                "l", "f", "f", "r", "b", "l", "r", "r", "b", "f", "r", "l",
                "r", "f", "f", "b", "b", "f", "b", "f", "l", "l", "f", "r",
                "f", "b", "b", "f", "r", "l", "f", "f", "b", "r", "f", "l",
                "l", "b", "b", "f", "l", "f", "b", "l", "f", "l", "l", "l"
            }),
            List.of(
                new Vector2D(13, 4), new Vector2D(1, 18),
                new Vector2D(7, 12), new Vector2D(3, 15),
                new Vector2D(9, 6), new Vector2D(11, 2)
            ),
            new RectangularMap(20, 20)
        );

        simulation.run();

        animals = simulation.getAnimals();

        assertEquals(6, animals.size());

        assertEquals(MapDirection.EAST, animals.get(0).getOrientation());
        assertEquals(new Vector2D(12, 2), animals.get(0).getPosition());

        assertEquals(MapDirection.EAST, animals.get(1).getOrientation());
        assertEquals(new Vector2D(2, 18), animals.get(1).getPosition());

        assertEquals(MapDirection.WEST, animals.get(2).getOrientation());
        assertEquals(new Vector2D(9, 13), animals.get(2).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(3).getOrientation());
        assertEquals(new Vector2D(4, 14), animals.get(3).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(4).getOrientation());
        assertEquals(new Vector2D(9, 6), animals.get(4).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(5).getOrientation());
        assertEquals(new Vector2D(12, 1), animals.get(5).getPosition());
    }

    @Test
    public void testRunEdgeCases() {
        // All empty
        assertThrows(
            IllegalArgumentException.class,
            () -> new Simulation(
                OptionsParser.parse(new String[] {}),
                new ArrayList<>(),
                new RectangularMap(5, 5)
            )
        );

        // Empty positions
        assertThrows(
            IllegalArgumentException.class,
            () -> new Simulation(
                OptionsParser.parse(new String[] {"l", "r", "b", "f"}),
                new ArrayList<>(),
                new RectangularMap(5, 5)
            )
        );

        // Empty moves
        Simulation simulation = new Simulation(
            OptionsParser.parse(new String[] {}),
            List.of(
                new Vector2D(4, 4), new Vector2D(2, 4),
                new Vector2D(2, 2), new Vector2D(0, 4)
            ),
            new RectangularMap(5, 5)
        );

        simulation.run();

        List<Animal> animals = simulation.getAnimals();

        assertEquals(MapDirection.NORTH, animals.get(0).getOrientation());
        assertEquals(new Vector2D(4, 4), animals.get(0).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(1).getOrientation());
        assertEquals(new Vector2D(2, 4), animals.get(1).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(2).getOrientation());
        assertEquals(new Vector2D(2, 2), animals.get(2).getPosition());

        assertEquals(MapDirection.NORTH, animals.get(3).getOrientation());
        assertEquals(new Vector2D(0, 4), animals.get(3).getPosition());
    }

    @Test
    public void testRunInvalidArguments() {
        // Some invalid arguments
        assertThrows(
            IllegalArgumentException.class,
            () -> new Simulation(
                OptionsParser.parse(new String[] {"k", "a", "l", "e", "t", "a"}),
                List.of(
                    new Vector2D(4, 4), new Vector2D(2, 4),
                    new Vector2D(2, 2), new Vector2D(0, 4)
                ),
                new RectangularMap(5, 5)
            )
        );

        // All invalid arguments
        assertThrows(
            IllegalArgumentException.class,
            () -> new Simulation(
                OptionsParser.parse(new String[] {"k", "a", "d", "e", "t", "a"}),
                List.of(
                    new Vector2D(4, 4), new Vector2D(2, 4),
                    new Vector2D(2, 2), new Vector2D(0, 4)
                ),
                new RectangularMap(5, 5)
            )
        );
    }*/
}
