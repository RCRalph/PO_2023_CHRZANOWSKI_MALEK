package agh.ics.oop.model.element;

import agh.ics.oop.model.MapDirection;
import agh.ics.oop.model.Pose;
import agh.ics.oop.model.PoseIndicator;
import agh.ics.oop.model.Vector2D;
import agh.ics.oop.model.element.behaviour.BehaviourIndicator;
import agh.ics.oop.model.element.gene.Gene;
import agh.ics.oop.model.element.gene.ReproductionInformation;
import agh.ics.oop.presenter.SimulationPresenter;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Animal implements WorldElement {
    private static final List<Image> ANIMAL_IMAGES = List.of(
        new Image("animals/laciaalien.png"),
        new Image("animals/laciaangy.png"),
        new Image("animals/laciababy.png"),
        new Image("animals/laciaconfused.png"),
        new Image("animals/laciaderpy.png"),
        new Image("animals/laciadisgusted.png"),
        new Image("animals/laciaeepy.png"),
        new Image("animals/laciahidy.png"),
        new Image("animals/laciajudgy.png"),
        new Image("animals/lacialovely.png"),
        new Image("animals/laciamlem.png"),
        new Image("animals/laciasweety.png"),
        new Image("animals/laciatired.png"),
        new Image("animals/laciawtf.png")
    );

    private final List<Gene> genes;

    private final int birthday;

    private final EnergyParameters energyParameters;

    private final BehaviourIndicator behaviourIndicator;

    private MapDirection orientation;

    private Vector2D position;

    private int geneIndex;

    private int energyLevel;

    private int childCount = 0;

    private int deathDay = -1;

    private final ImageView imageView = new ImageView(ANIMAL_IMAGES.get(new Random().nextInt(ANIMAL_IMAGES.size())));

    private int plantsEaten = 0;

    public Animal(
        Vector2D position,
        List<Gene> genes,
        BehaviourIndicator behaviourIndicator,
        EnergyParameters energyParameters,
        int birthday
    ) {
        this(position, genes, behaviourIndicator, energyParameters, birthday, energyParameters.startEnergy());
    }

    public Animal(
        Vector2D position,
        List<Gene> genes,
        BehaviourIndicator behaviourIndicator,
        EnergyParameters energyParameters,
        int birthday,
        int startEnergyLevel
    ) {
        this.position = position;
        this.genes = genes;
        this.behaviourIndicator = behaviourIndicator;
        this.energyParameters = energyParameters;
        this.birthday = birthday;

        this.energyLevel = startEnergyLevel;
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

    public Gene getCurrentGene() {
        return this.genes.get(this.geneIndex);
    }

    public void move(PoseIndicator poseIndicator) {
        this.orientation = this.orientation.rotateByGene(this.getCurrentGene());
        this.geneIndex = this.behaviourIndicator.indicateGeneIndex(this);

        Pose pose = poseIndicator.indicatePose(this.getPose());
        this.position = pose.position();
        this.orientation = pose.orientation();

        this.energyLevel -= this.energyParameters.moveEnergy();
    }

    @Override
    public Vector2D getPosition() {
        return this.position;
    }

    @Override
    public ImageView getImageView() {
        return this.imageView;
    }

    @Override
    public void setImageViewSize(double cellSize) {
        this.imageView.setFitWidth(cellSize * 0.8);
        this.imageView.setFitHeight(cellSize * 0.8);
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
        return this.birthday;
    }

    public int getDeathDay() {
        return this.deathDay;
    }

    public int getChildCount() {
        return this.childCount;
    }

    public int getGeneIndex() {
        return this.geneIndex;
    }

    public int getPlantsEaten(){
        return plantsEaten;
    }
  
    public List<Gene> getGenes() {
        return Collections.unmodifiableList(this.genes);
    }

    public void consumePlant() {
        this.energyLevel += this.energyParameters.plantEnergy();
        this.plantsEaten++;
    }

    public ReproductionInformation reproduce() {
        ReproductionInformation result = new ReproductionInformation(
            this.energyLevel,
            Collections.unmodifiableList(this.genes)
        );

        this.childCount++;
        this.energyLevel -= this.energyParameters.reproductionEnergy();

        return result;
    }

    public void setDeathDay(int deathDay) {
        this.deathDay = deathDay;
    }
}
