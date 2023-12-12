package agh.ics.oop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class SimulationEngine {
    private final Collection<Simulation> simulations;

    private final List<Thread> threads = new ArrayList<>();

    private final ExecutorService executorService = Executors.newFixedThreadPool(4);

    private boolean executorServiceUsed = false;

    public SimulationEngine(Collection<Simulation> simulations) {
        this.simulations = simulations;
    }

    public void runAsync() {
        for (Simulation simulation : this.simulations) {
            Thread thread = new Thread(simulation);
            this.threads.add(thread);
            thread.start();
        }
    }

    public void awaitSimulationsEnd() throws InterruptedException {
        for (Thread thread : this.threads) {
            thread.join();
        }

        if (this.executorServiceUsed && !this.executorService.awaitTermination(10, TimeUnit.SECONDS)) {
            this.executorService.shutdownNow();
        }
    }

    public void runAsyncInThreadPool() {
        this.executorServiceUsed = true;

        for (Simulation simulation : this.simulations) {
            this.executorService.submit(simulation);
        }

        executorService.shutdown();
    }
}
