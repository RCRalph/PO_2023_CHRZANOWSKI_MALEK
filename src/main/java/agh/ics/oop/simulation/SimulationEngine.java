package agh.ics.oop.simulation;

public class SimulationEngine {
    private final Simulation simulation;

    private final Thread simulationThread;

    public SimulationEngine(Simulation simulation) {
        this.simulation = simulation;
        this.simulationThread = new Thread(this.simulation);
    }

    public synchronized void start() {
        switch (this.simulation.executionStatus) {
            case INITIALIZED -> {
                this.simulationThread.start();
                this.simulation.executionStatus = SimulationStatus.RUNNING;
            }
            case PAUSED -> this.simulation.executionStatus = SimulationStatus.RUNNING;
            default -> throw new IllegalThreadStateException("Invalid start status");
        }
    }

    public synchronized void pause() {
        if (this.simulation.executionStatus == SimulationStatus.RUNNING) {
            this.simulation.executionStatus = SimulationStatus.PAUSED;
        } else {
            throw new IllegalThreadStateException("Invalid pause status");
        }
    }

    public synchronized void stop() throws InterruptedException {
        if (this.simulation.executionStatus != SimulationStatus.STOPPED) {
            this.simulation.executionStatus = SimulationStatus.STOPPED;
            this.simulationThread.join();
        } else {
            throw new IllegalThreadStateException("Invalid stop status");
        }
    }
}
