package agh.ics.oop.presenter;

import agh.ics.oop.Simulation;
import agh.ics.oop.SimulationParameters;
import agh.ics.oop.model.map.UndergroundTunnelsWorldMap;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;

import java.net.URL;
import java.util.*;

public class ConfigurationPresenter implements Initializable {
    private static final String NEW_CONFIGURATION_TEXT = "Create new configuration...";

    private final Map<String, SimulationParameters> configurations = new HashMap<>();

    private final SimpleBooleanProperty isReadOnly = new SimpleBooleanProperty();

    private final SimpleBooleanProperty shouldShowTunnels = new SimpleBooleanProperty();

    @FXML
    private ChoiceBox<String> configurationVariant;

    @FXML
    private TextField configurationName;

    @FXML
    private Spinner<Integer> mapWidth;

    @FXML
    private Spinner<Integer> mapHeight;

    @FXML
    private ChoiceBox<String> mapVariant;

    @FXML
    private Spinner<Integer> tunnelCount;

    @FXML
    private Spinner<Integer> geneSize;

    @FXML
    private ChoiceBox<String> mutationVariant;

    @FXML
    private Spinner<Integer> minMutationCount;

    @FXML
    private Spinner<Integer> maxMutationCount;

    @FXML
    private ChoiceBox<String> plantGrowthVariant;

    @FXML
    private Spinner<Integer> initialPlantCount;

    @FXML
    private Spinner<Integer> dailyPlantGrowth;

    @FXML
    private Spinner<Integer> plantEnergy;

    @FXML
    private Spinner<Integer> initialAnimalCount;

    @FXML
    private Spinner<Integer> initialAnimalEnergy;

    @FXML
    private Spinner<Integer> animalMovementEnergy;

    @FXML
    private Spinner<Integer> healthyAnimalEnergy;

    @FXML
    private Spinner<Integer> animalReproductionEnergy;

    @FXML
    private ChoiceBox<String> animalBehaviourVariant;

    @FXML
    private Label messageLabel;

    @FXML
    private Button saveButton;

    @FXML
    private Button runButton;

    private void loadSimulationConfigurations() {
        this.configurations.clear();

        this.configurations.put("Non-editable", new SimulationParameters(
            "Non-editable",
            0,
            0,
            "Underground tunnels",
            0,
            0,
            0,
            0,
            "Slight correction",
            0,
            0,
            0,
            "Forested equator",
            0,
            0,
            0,
            0,
            0,
            "Full predestination"
        ));
    }

    private List<String> getConfigurationVariants() {
        List<String> result = new ArrayList<>(this.configurations.keySet());
        result.add(NEW_CONFIGURATION_TEXT);

        return result;
    }

    private void initializeFormFields() {
        this.configurationVariant.setItems(FXCollections.observableList(this.getConfigurationVariants()));

        this.configurationName.disableProperty().bind(this.isReadOnly);

        this.mapWidth.disableProperty().bind(this.isReadOnly);
        this.mapWidth.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE)
        );

        this.mapHeight.disableProperty().bind(this.isReadOnly);
        this.mapHeight.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE)
        );

        List<String> mapVariants = new ArrayList<>(Simulation.WORLD_MAPS.keySet());
        this.mapVariant.setItems(FXCollections.observableList(mapVariants));
        this.mapVariant.disableProperty().bind(this.isReadOnly);
        this.mapVariant.setOnAction(event -> this.shouldShowTunnels.setValue(
            Simulation.WORLD_MAPS.get(this.mapVariant.getValue()) != UndergroundTunnelsWorldMap.class
        ));

        this.tunnelCount.disableProperty().bind(this.isReadOnly.or(this.shouldShowTunnels));
        this.tunnelCount.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );


        List<String> plantGrowthVariants = new ArrayList<>(Simulation.PLANT_GROWTH_INDICATORS.keySet());
        this.plantGrowthVariant.setItems(FXCollections.observableList(plantGrowthVariants));
        this.plantGrowthVariant.disableProperty().bind(this.isReadOnly);

        this.initialPlantCount.disableProperty().bind(this.isReadOnly);
        this.initialPlantCount.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        this.dailyPlantGrowth.disableProperty().bind(this.isReadOnly);
        this.dailyPlantGrowth.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        this.plantEnergy.disableProperty().bind(this.isReadOnly);
        this.plantEnergy.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        this.geneSize.disableProperty().bind(this.isReadOnly);
        this.geneSize.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE)
        );

        List<String> mutationVariants = new ArrayList<>(Simulation.CHILD_GENES_INDICATORS.keySet());
        this.mutationVariant.setItems(FXCollections.observableList(mutationVariants));
        this.mutationVariant.disableProperty().bind(this.isReadOnly);

        this.minMutationCount.disableProperty().bind(this.isReadOnly);
        this.minMutationCount.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        this.maxMutationCount.disableProperty().bind(this.isReadOnly);
        this.maxMutationCount.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        this.initialAnimalCount.disableProperty().bind(this.isReadOnly);
        this.initialAnimalCount.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE)
        );

        this.initialAnimalEnergy.disableProperty().bind(this.isReadOnly);
        this.initialAnimalEnergy.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        this.animalMovementEnergy.disableProperty().bind(this.isReadOnly);
        this.animalMovementEnergy.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        this.healthyAnimalEnergy.disableProperty().bind(this.isReadOnly);
        this.healthyAnimalEnergy.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(1, Integer.MAX_VALUE)
        );

        this.animalReproductionEnergy.disableProperty().bind(this.isReadOnly);
        this.animalReproductionEnergy.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        List<String> behaviourIndicators = new ArrayList<>(Simulation.BEHAVIOUR_INDICATORS.keySet());
        this.animalBehaviourVariant.setItems(FXCollections.observableList(behaviourIndicators));
        this.animalBehaviourVariant.disableProperty().bind(this.isReadOnly);

        this.saveButton.disableProperty().bind(this.isReadOnly);

        this.runButton.setOnAction(event -> this.runSimulation());
    }

    private void setConfigurationParameters() {
        SimulationParameters parameters = this.configurations.get(this.configurationVariant.getValue());

        this.configurationName.setText(parameters.configurationName());

        this.mapWidth.getValueFactory().setValue(parameters.mapWidth());
        this.mapHeight.getValueFactory().setValue(parameters.mapHeight());
        this.mapVariant.setValue(parameters.worldMapVariant());
        this.tunnelCount.getValueFactory().setValue(parameters.tunnelCount());

        this.geneSize.getValueFactory().setValue(parameters.geneCount());
        this.mutationVariant.setValue(parameters.childGenesIndicatorVariant());
        this.minMutationCount.getValueFactory().setValue(parameters.minimumMutationCount());
        this.maxMutationCount.getValueFactory().setValue(parameters.maximumMutationCount());

        this.plantGrowthVariant.setValue(parameters.plantGrowthIndicatorVariant());
        this.initialPlantCount.getValueFactory().setValue(parameters.startPlantCount());
        this.dailyPlantGrowth.getValueFactory().setValue(parameters.dailyPlantGrowth());
        this.plantEnergy.getValueFactory().setValue(parameters.plantEnergy());

        this.initialAnimalCount.getValueFactory().setValue(parameters.startAnimalCount());
        this.animalMovementEnergy.getValueFactory().setValue(parameters.animalMoveEnergy());
        this.healthyAnimalEnergy.getValueFactory().setValue(parameters.healthyAnimalEnergy());
        this.animalReproductionEnergy.getValueFactory().setValue(parameters.reproductionEnergy());
        this.animalBehaviourVariant.setValue(parameters.animalBehaviourIndicatorVariant());
    }

    private SimulationParameters getSimulationParameters() {
        return new SimulationParameters(
            this.configurationName.getText().strip(),

            this.mapWidth.getValue(),
            this.mapHeight.getValue(),
            this.mapVariant.getValue(),
            Simulation.WORLD_MAPS.get(this.mapVariant.getValue()) == UndergroundTunnelsWorldMap.class ?
                this.tunnelCount.getValue() : 0,

            this.geneSize.getValue(),
            this.minMutationCount.getValue(),
            this.maxMutationCount.getValue(),
            this.mutationVariant.getValue(),

            this.initialPlantCount.getValue(),
            this.dailyPlantGrowth.getValue(),
            this.plantEnergy.getValue(),
            this.plantGrowthVariant.getValue(),

            this.initialAnimalCount.getValue(),
            this.initialAnimalEnergy.getValue(),
            this.animalMovementEnergy.getValue(),
            this.healthyAnimalEnergy.getValue(),
            this.animalReproductionEnergy.getValue(),
            this.animalBehaviourVariant.getValue()
        );
    }

    private void runSimulation() {
        SimulationParameters parameters = this.getSimulationParameters();

        Optional<String> validationMessage = parameters.getValidationMessage();

        if (validationMessage.isPresent()) {
            this.messageLabel.setTextFill(Paint.valueOf("RED"));
            this.messageLabel.setText(validationMessage.get());
        } else {
            this.messageLabel.setTextFill(Paint.valueOf("BLACK"));
            this.messageLabel.setText("Running simulation...");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        this.configurationVariant.setOnAction(event -> {
            if (!this.configurationVariant.getValue().equals(NEW_CONFIGURATION_TEXT)) {
                this.setConfigurationParameters();
            } else {
                this.configurationName.clear();
            }

            this.isReadOnly.setValue(!this.configurationVariant.getValue().equals(NEW_CONFIGURATION_TEXT));
        });

        this.loadSimulationConfigurations();
        this.initializeFormFields();
        this.configurationVariant.setValue(this.getConfigurationVariants().get(0));
    }
}
