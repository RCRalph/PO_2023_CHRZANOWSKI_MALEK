package agh.ics.oop.simulation;

public enum SimulationExecutionStatus {
    INITIALIZED(true, false, true),
    RUNNING(false, true, true),
    PAUSED(true, false, true),
    STOPPED(false, false, false);

    private final boolean startable;

    private final boolean pausable;

    private final boolean stoppable;

    SimulationExecutionStatus(boolean startable, boolean pausable, boolean stoppable) {
        this.startable = startable;
        this.pausable = pausable;
        this.stoppable = stoppable;
    }

    public boolean isStartable() {
        return this.startable;
    }

    public boolean isPausable() {
        return this.pausable;
    }

    public boolean isStoppable() {
        return this.stoppable;
    }
}
