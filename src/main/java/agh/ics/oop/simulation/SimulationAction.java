package agh.ics.oop.simulation;

public enum SimulationAction {
    REMOVE_DEAD_ANIMALS,
    MOVE_ANIMALS,
    CONSUME_PLANTS,
    REPRODUCE_ANIMALS,
    GROW_PLANTS,
    SLEEP;

    public SimulationAction next() {  // nie wygodniej by było korzystać z values()? wtedy mamy pewność, że każdy krok jest dokładnie raz
        return switch (this) {
            case REMOVE_DEAD_ANIMALS -> MOVE_ANIMALS;
            case MOVE_ANIMALS -> CONSUME_PLANTS;
            case CONSUME_PLANTS -> REPRODUCE_ANIMALS;
            case REPRODUCE_ANIMALS -> GROW_PLANTS;
            case GROW_PLANTS -> SLEEP;
            case SLEEP -> REMOVE_DEAD_ANIMALS;
        };
    }
}
