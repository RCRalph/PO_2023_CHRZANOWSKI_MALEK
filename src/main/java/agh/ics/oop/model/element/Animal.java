package agh.ics.oop.model.element;

import agh.ics.oop.SimulationParameters;
import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.PoseIndicator;
import agh.ics.oop.model.Pose;
import agh.ics.oop.model.Vector2D;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement {
    private MapDirection orientation;

    private Vector2D position;

    private final List<Gene> genes;

    private int geneIndex;

    private int energyLevel;

    public Animal(Vector2D position, List<Gene> genes, SimulationParameters simulationParameters) {
        this.position = position;
        this.genes = genes;

        this.energyLevel = simulationParameters.startAnimalEnergy();
        this.orientation = MapDirection.random();
        this.geneIndex = new Random().nextInt(genes.size());
    }

    @Override
    public String toString() {
        return this.orientation.toString();
    }

    public boolean isAt(Vector2D position) {
        return this.position.equals(position);
    }

    private Gene getCurrentGene() {
        return this.genes.get(this.geneIndex);
    }

    public void move(PoseIndicator poseIndicator, BehaviourIndicator behaviourIndicator) {
        this.orientation = this.orientation.rotateByGene(this.getCurrentGene());
        this.geneIndex = behaviourIndicator.indicateGeneIndex(this.geneIndex);

        Pose pose = poseIndicator.indicatePose(this.getPose());
        this.position = pose.position();
        this.orientation = pose.orientation();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    public MapDirection getOrientation() {
        return this.orientation;
    }

    public Pose getPose() {
        return new Pose(this.position, this.orientation);
    }

    public int getEnergyLevel() {
        return this.energyLevel;
    }

    // TODO: Add methods for move energy loss and reproduction energy loss
    private void updateEnergyLevel(int energyChange) {
        this.energyLevel += energyChange;
    }

    public ReproductionInformation getReproductionInformation() {
        return new ReproductionInformation(
            this.energyLevel,
            Collections.unmodifiableList(this.genes)
        );
    }
}
