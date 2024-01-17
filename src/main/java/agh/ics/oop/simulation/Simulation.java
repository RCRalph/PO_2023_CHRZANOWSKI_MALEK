package agh.ics.oop.simulation;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.DarwinistAnimalComparator;
import agh.ics.oop.model.element.EnergyParameters;
import agh.ics.oop.model.element.behaviour.BehaviourIndicator;
import agh.ics.oop.model.element.gene.ChildGenesIndicator;
import agh.ics.oop.model.element.gene.Gene;
import agh.ics.oop.model.map.WorldMap;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Simulation implements Runnable {
    private final SimulationParameters parameters;

    private final List<SimulationChangeListener> listeners = new ArrayList<>();

    private final ChildGenesIndicator childGenesIndicator;

    private final BehaviourIndicator behaviourIndicator;

    private final WorldMap worldMap;

    private final List<Animal> animals = new ArrayList<>();

    private final EnergyParameters energyParameters;

    private SimulationAction simulationAction = SimulationAction.SLEEP.next();

    private final SimulationEngine engine;

    private int currentDay;

    public Simulation(
        SimulationParameters parameters,
        WorldMap worldMap,
        BehaviourIndicator behaviourIndicator,
        ChildGenesIndicator childGenesIndicator
    ) {
        this.parameters = parameters;
        this.worldMap = worldMap;
        this.behaviourIndicator = behaviourIndicator;
        this.childGenesIndicator = childGenesIndicator;

        this.energyParameters = this.parameters.getEnergyParameters();
        this.engine = new SimulationEngine(this);
    }

    private void placeAnimal(Animal animal) {
        this.animals.add(animal);
        this.worldMap.placeAnimal(animal);
    }

    private void generateAnimals() {
        for (int i = 0; i < this.parameters.startAnimalCount(); i++) {
            Animal animal = new Animal(
                Vector2D.random(this.worldMap.getCurrentBounds()),
                Gene.randomList(this.parameters.geneCount()),
                this.behaviourIndicator,
                this.energyParameters,
                this.currentDay
            );

            this.placeAnimal(animal);
        }
    }

    private void reproduceAnimals() {
        for (Vector2D position : this.worldMap.getAnimalPositions()) {
            List<Animal> animals = this.worldMap.animalsAt(position)
                .stream()
                .filter(animal -> animal.getEnergyLevel() >= this.parameters.healthyAnimalEnergy())
                .sorted(new DarwinistAnimalComparator())
                .limit(2)
                .toList();

            if (animals.size() == 2) {
                Animal child = new Animal(
                    position,
                    this.childGenesIndicator.getChildGenes(
                        animals.get(0).reproduce(),
                        animals.get(1).reproduce()
                    ),
                    this.behaviourIndicator,
                    this.energyParameters,
                    this.currentDay,
                    this.energyParameters.reproductionEnergy() * 2
                );

                this.placeAnimal(child);
            }
        }
    }

    public void initialize() {
        this.generateAnimals();
        this.worldMap.growPlants(this.parameters.startPlantCount());
        this.currentDay = 1;
        this.simulationChanged("Initialized simulation");
    }

    public void subscribe(SimulationChangeListener listener) {
        this.listeners.add(listener);
    }

    public void unsubscribe(SimulationChangeListener listener) {
        this.listeners.remove(listener);
    }

    private void simulationChanged(String message) {
        for (SimulationChangeListener listener : this.listeners) {
            listener.simulationChanged(this.worldMap, message);
        }
    }

    private void simulationChanged(String message, int day) {
        for (SimulationChangeListener listener : this.listeners) {
            listener.simulationChanged(this.worldMap, String.format("Day %d: %s", day, message));
        }
    }

    public SimulationEngine getEngine() {
        return this.engine;
    }

    @Override
    public void run() {
        while (this.engine.getExecutionStatus() == SimulationExecutionStatus.RUNNING && this.worldMap.aliveAnimalCount() > 0) {
            switch (this.simulationAction) {
                case REMOVE_DEAD_ANIMALS -> {
                    this.worldMap.removeDeadAnimals(this.currentDay);
                    this.simulationChanged("Removed dead animals", this.currentDay);
                }
                case MOVE_ANIMALS -> {
                    this.worldMap.moveAnimals();
                    this.simulationChanged("Moved animals", this.currentDay);
                }
                case CONSUME_PLANTS -> {
                    this.worldMap.consumePlants();
                    this.simulationChanged("Animals consumed plants", this.currentDay);
                }
                case REPRODUCE_ANIMALS -> {
                    this.reproduceAnimals();
                    this.simulationChanged("Reproduced animals", this.currentDay);
                }
                case GROW_PLANTS -> {
                    this.worldMap.growPlants(this.parameters.dailyPlantGrowth());
                    this.simulationChanged("Grew new plants", this.currentDay);
                }
                case SLEEP -> {
                    this.simulationChanged("Progressing to the next day...");
                    this.currentDay++;
                }
            }

            this.simulationAction = this.simulationAction.next();

            try {
                TimeUnit.MILLISECONDS.sleep(100);
            } catch (InterruptedException exception) {
                this.engine.stop();
                this.simulationChanged("Simulation interrupted");
            }
        }

        if (this.worldMap.aliveAnimalCount() == 0) {
            this.engine.stop();
            this.simulationChanged("All animals are dead");
        }
    }
}
