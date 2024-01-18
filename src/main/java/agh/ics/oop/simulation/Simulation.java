package agh.ics.oop.simulation;

import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.Animal;
import agh.ics.oop.model.element.DarwinistAnimalComparator;
import agh.ics.oop.model.element.EnergyParameters;
import agh.ics.oop.model.element.behaviour.BehaviourIndicator;
import agh.ics.oop.model.element.gene.ChildGenesIndicator;
import agh.ics.oop.model.element.gene.Gene;
import agh.ics.oop.model.map.Boundary;
import agh.ics.oop.model.map.WorldMap;
import javafx.beans.binding.ObjectExpression;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class Simulation implements Runnable {
    private static final int ACTION_TIMEOUT = 100;

    private final SimulationParameters parameters;

    private final List<SimulationChangeListener> listeners = new ArrayList<>();

    private final ChildGenesIndicator childGenesIndicator;

    private final BehaviourIndicator behaviourIndicator;

    private final WorldMap worldMap;

    private final List<Animal> animals = new ArrayList<>();

    private final Map<Animal, List<Animal>> animalChildren = new IdentityHashMap<>();

    private final EnergyParameters energyParameters;

    private SimulationAction simulationAction = SimulationAction.SLEEP.next();

    private final SimulationEngine engine;

    private int currentDay;

    private Animal followedAnimal;

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
        this.animalChildren.put(animal, new ArrayList<>());
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
                this.animalChildren.get(animals.get(0)).add(child);
                this.animalChildren.get(animals.get(1)).add(child);
            }
        }
    }

    public void initialize() {
        this.generateAnimals();
        this.worldMap.growPlants(this.parameters.startPlantCount());
        this.currentDay = 1;
        this.simulationChanged(this.getSimulationStatistics());
        this.simulationChanged("Initialized simulation");
    }

    public void subscribe(SimulationChangeListener listener) {
        this.listeners.add(listener);
        this.engine.subscribe(listener);
    }

    public void unsubscribe(SimulationChangeListener listener) {
        this.engine.unsubscribe(listener);
        this.listeners.remove(listener);
    }

    private void simulationChanged(String message) {
        for (SimulationChangeListener listener : this.listeners) {
            listener.simulationChanged(message, this.worldMap);
        }
    }

    private void simulationChanged(SimulationStatistics statistics) {
        for (SimulationChangeListener listener : this.listeners) {
          listener.simulationChanged("Updating statistics", statistics);
        }
    }
  
    private void simulationChanged(Animal followedAnimal){
        for (SimulationChangeListener listener: this.listeners){
            listener.simulationChanged(worldMap, followedAnimal);
        }
    }

    private void simulationChanged(int descendantCount){
        for (SimulationChangeListener listener: this.listeners){
            listener.simulationChanged(descendantCount);
        }
    }

    private void simulationChanged(WorldMap map, List<Animal> animals){
        for (SimulationChangeListener listener: this.listeners){
            listener.simulationChanged(map, Collections.unmodifiableCollection(animals));
        }
    }

    public SimulationEngine getEngine() {
        return this.engine;
    }

    private void setFollowing(){
        for(Animal animal: this.animals){
            if(animal.isBeingFollowed()){
                this.followedAnimal = animal;
                simulationChanged(animal);
            }
            animal.setBeingFollowed(false);
        }
    }

    @Override
    public void run() {
        while (this.engine.getExecutionStatus() == SimulationExecutionStatus.RUNNING && this.worldMap.aliveAnimalCount() > 0) {
            setFollowing();
            switch (this.simulationAction) {
                case REMOVE_DEAD_ANIMALS -> {
                    this.simulationChanged("Removing dead animals");
                    this.worldMap.removeDeadAnimals(this.currentDay);
                }
                case MOVE_ANIMALS -> {
                    this.simulationChanged("Moving animals");
                    this.simulationChanged(this.worldMap, this.animals);
                    this.worldMap.moveAnimals();
                }
                case CONSUME_PLANTS -> {
                    this.simulationChanged("Animals consuming plants");
                    this.worldMap.consumePlants();
                }
                case REPRODUCE_ANIMALS -> {
                    this.simulationChanged("Reproducing animals");
                    this.reproduceAnimals();
                }
                case GROW_PLANTS -> {
                    this.simulationChanged("Growing new plants");
                    this.worldMap.growPlants(this.parameters.dailyPlantGrowth());
                }
                case SLEEP -> {
                    this.currentDay++;
                    this.simulationChanged(this.getSimulationStatistics());
                    if(!Objects.isNull(this.followedAnimal)) {
                        this.simulationChanged(this.getAnimalChildrenCount(
                                new HashMap<Animal, Integer>(), this.followedAnimal)
                        );
                    }
                }
            }

            this.simulationAction = this.simulationAction.next();

            try {
                TimeUnit.MILLISECONDS.sleep(ACTION_TIMEOUT);
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

    private int getAnimalChildrenCount(Map<Animal, Integer> animalChildrenCount, Animal animal) {
        animalChildrenCount.putIfAbsent(animal,
            this.animalChildren.get(animal).size() +
                this.animalChildren.get(animal)
                    .stream()
                    .mapToInt(item -> this.getAnimalChildrenCount(animalChildrenCount, item))
                    .sum()
        );

        return animalChildrenCount.get(animal);
    }

    private Map<List<Gene>, Integer> getGenomePopularity() {
        List<List<Gene>> genomes = this.animals
            .stream()
            .filter(item -> item.getEnergyLevel() > 0)
            .map(Animal::getGenes)
            .toList();

        Map<List<Gene>, Integer> genomePopularity = new HashMap<>();
        for (List<Gene> genome : genomes) {
            genomePopularity.putIfAbsent(genome, 0);
            genomePopularity.put(genome, genomePopularity.get(genome) + 1);
        }

        return Collections.unmodifiableMap(genomePopularity);
    }

    public SimulationStatistics getSimulationStatistics() {
        double averageEnergyLevel = this.worldMap.getAnimals()
            .stream()
            .mapToDouble(Animal::getEnergyLevel)
            .average()
            .orElse(0);

        double averageLifeSpan = this.animals
            .stream()
            .filter(item -> item.getEnergyLevel() <= 0)
            .mapToDouble(
                item -> (item.getDeathDay() == -1 ? this.currentDay : item.getDeathDay()) - item.getBirthday() + 1
            )
            .average()
            .orElse(0);

        Map<Animal, Integer> animalChildrenCount = new HashMap<>();

        double averageChildren = this.worldMap.getAnimals()
            .stream()
            .mapToInt(item -> this.getAnimalChildrenCount(animalChildrenCount, item))
            .average()
            .orElse(0);

        return new SimulationStatistics(
            this.currentDay,
            this.worldMap.aliveAnimalCount(),
            this.worldMap.plantCount(),
            this.worldMap.freeFieldCount(),
            averageEnergyLevel,
            averageLifeSpan,
            averageChildren,
            this.getGenomePopularity()
        );
    }
}
