package agh.ics.oop.simulation;

import java.util.ArrayList;
import java.util.List;

public class SimulationEngine {
    private final Simulation simulation;

    private SimulationExecutionStatus simulationExecutionStatus;

    private Thread simulationThread;

    private final List<SimulationChangeListener> listeners = new ArrayList<>();

    public SimulationEngine(Simulation simulation) {
        this.simulation = simulation;
    }

    public SimulationExecutionStatus getExecutionStatus() {
        return this.simulationExecutionStatus;
    }

    public synchronized void initialize() {
        if (this.simulationExecutionStatus == null) {
            this.simulation.initialize();
            this.simulationExecutionStatus = SimulationExecutionStatus.INITIALIZED;
        } else {
            throw new IllegalStateException(String.format(
                "Cannot initialize a %s simulation", this.simulationExecutionStatus
            ));
        }
    }

    public synchronized void start() {
        if (this.simulationExecutionStatus.isStartable()) {
            this.simulationExecutionStatus = SimulationExecutionStatus.RUNNING;

            this.simulationThread = new Thread(this.simulation);
            this.simulationThread.start();

            this.simulationStatusChanged("Simulation started");
        } else {
            throw new IllegalStateException(String.format(
                "Cannot start a %s simulation", this.simulationExecutionStatus
            ));
        }
    }

    public synchronized void pause() throws InterruptedException {
        if (this.simulationExecutionStatus.isPausable()) {
            this.simulationExecutionStatus = SimulationExecutionStatus.PAUSED;
            this.simulationThread.join();
            this.simulationStatusChanged("Simulation paused");
        } else {
            throw new IllegalStateException(String.format(
                "Cannot pause a %s thread", this.simulationExecutionStatus
            ));
        }
    }

    public synchronized void stop() {
        if (this.simulationExecutionStatus.isStoppable()) {
            this.simulationExecutionStatus = SimulationExecutionStatus.STOPPED;

            try {
                this.simulationThread.join();
            } catch (InterruptedException ignored) {}

            this.simulationStatusChanged("Simulation stopped");
        } else {
            throw new IllegalStateException(String.format(
                "Cannot stop a %s thread", this.simulationExecutionStatus
            ));
        }
    }

    private void simulationStatusChanged(String message) {
        for (SimulationChangeListener listener : this.listeners) {
            listener.simulationChanged(message);
        }
    }

    public void subscribe(SimulationChangeListener listener) {
        this.listeners.add(listener);
        this.simulation.subscribe(listener);
    }

    public void unsubscribe(SimulationChangeListener listener) {
        this.listeners.remove(listener);
        this.simulation.unsubscribe(listener);
    }
}
