package agh.ics.oop.model.element;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.PoseIndicator;
import agh.ics.oop.model.Pose;
import agh.ics.oop.model.Vector2D;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement {
    private final List<Gene> genes;

    private final int birthday;

    private final EnergyParameters energyParameters;

    private final BehaviourIndicator behaviourIndicator;

    private MapDirection orientation;

    private Vector2D position;

    private int geneIndex;

    private int energyLevel;

    private int childCount = 0;

    public Animal(
        Vector2D position,
        List<Gene> genes,
        BehaviourIndicator behaviourIndicator,
        EnergyParameters energyParameters,
        int birthday
    ) {
        this.position = position;
        this.genes = genes;
        this.behaviourIndicator = behaviourIndicator;
        this.energyParameters = energyParameters;
        this.birthday = birthday;

        this.energyLevel = this.energyParameters.startEnergy();
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

    public void move(PoseIndicator poseIndicator) {
        this.orientation = this.orientation.rotateByGene(this.getCurrentGene());
        this.geneIndex = this.behaviourIndicator.indicateGeneIndex(this.geneIndex);

        Pose pose = poseIndicator.indicatePose(this.getPose());
        this.position = pose.position();
        this.orientation = pose.orientation();

        this.energyLevel -= this.energyParameters.moveEnergy();
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

    public int getBirthday() {
        return birthday;
    }

    public int getChildCount() {
        return childCount;
    }

    public void eatPlant() {
        this.energyLevel += this.energyParameters.plantEnergy();
    }

    public ReproductionInformation getReproductionInformation() {
        ReproductionInformation result = new ReproductionInformation(
            this.energyLevel,
            Collections.unmodifiableList(this.genes)
        );

        this.childCount++;
        this.energyLevel -= this.energyParameters.reproductionEnergy();

        return result;
    }
}
