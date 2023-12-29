package agh.ics.oop.presenter;

import agh.ics.oop.SimulationWindow;
import agh.ics.oop.model.map.UndergroundTunnelsWorldMap;
import agh.ics.oop.simulation.InvalidSimulationConfigurationException;
import agh.ics.oop.simulation.Simulation;
import agh.ics.oop.simulation.SimulationParameters;
import com.google.gson.*;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ConfigurationPresenter implements Initializable {
    private static final String NEW_CONFIGURATION_TEXT = "Create new configuration...";

    private static final String CONFIGURATION_FILE_NAME = "./configurations.json";

    private final Map<String, SimulationParameters> configurations = new HashMap<>();

    private final ObservableList<String> observableConfigurationNames = FXCollections.observableList(new ArrayList<>());

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

    private void showErrorMessage(String message) {
        this.messageLabel.setTextFill(Paint.valueOf("RED"));
        this.messageLabel.setText(message);
    }

    private void showMessage(String message) {
        this.messageLabel.setTextFill(Paint.valueOf("BLACK"));
        this.messageLabel.setText(message);
    }

    private void updateConfigurations(SimulationParameters parameters) {
        this.configurations.put(parameters.configurationName(), parameters);
        this.observableConfigurationNames.add(this.observableConfigurationNames.size() - 1, parameters.configurationName());
    }

    private void loadSimulationConfigurations() {
        this.observableConfigurationNames.add(NEW_CONFIGURATION_TEXT);

        try (FileReader reader = new FileReader(CONFIGURATION_FILE_NAME)) {
            JsonArray jsonArray = JsonParser.parseReader(reader).getAsJsonArray();

            for (JsonElement jsonElement : jsonArray) {
                SimulationParameters parameters = SimulationParameters.fromJsonObject(jsonElement.getAsJsonObject());

                if (parameters.getValidationMessage(this.configurations.keySet()).isEmpty()) {
                    this.updateConfigurations(parameters);
                }
            }

            this.showMessage("Loaded configurations!");
        } catch (IOException exception) {
            this.showErrorMessage(exception.toString());
        }
    }

    @FXML
    private void saveConfiguration() {
        SimulationParameters parameters = this.getSimulationParameters();

        Optional<String> validationMessage = parameters.getValidationMessage(this.configurations.keySet());
        if (validationMessage.isPresent()) {
            this.showErrorMessage(validationMessage.get());
            return;
        }

        this.updateConfigurations(parameters);

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        JsonArray jsonArray = new JsonArray();
        for (SimulationParameters item : this.configurations.values()) {
            jsonArray.add(item.toJsonObject());
        }

        try (FileWriter fileWriter = new FileWriter(CONFIGURATION_FILE_NAME)) {
            gson.toJson(jsonArray, fileWriter);
            this.showMessage("Configuration saved!");
        } catch (IOException exception) {
            this.showErrorMessage(exception.toString());
        }
    }

    private void initializeFormFields() {
        this.configurationVariant.setItems(this.observableConfigurationNames);

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
            Simulation.WORLD_MAPS.get(this.mapVariant.getValue()) == UndergroundTunnelsWorldMap.class
        ));
        this.mapVariant.setValue(mapVariants.get(0));

        this.tunnelCount.disableProperty().bind(this.isReadOnly.or(this.shouldShowTunnels.not()));
        this.tunnelCount.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        List<String> plantGrowthVariants = new ArrayList<>(Simulation.PLANT_GROWTH_INDICATORS.keySet());
        this.plantGrowthVariant.setItems(FXCollections.observableList(plantGrowthVariants));
        this.plantGrowthVariant.disableProperty().bind(this.isReadOnly);
        this.plantGrowthVariant.setValue(plantGrowthVariants.get(0));

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
        this.mutationVariant.setValue(mutationVariants.get(0));

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
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        this.animalReproductionEnergy.disableProperty().bind(this.isReadOnly);
        this.animalReproductionEnergy.setValueFactory(
            new SpinnerValueFactory.IntegerSpinnerValueFactory(0, Integer.MAX_VALUE)
        );

        List<String> behaviourIndicators = new ArrayList<>(Simulation.BEHAVIOUR_INDICATORS.keySet());
        this.animalBehaviourVariant.setItems(FXCollections.observableList(behaviourIndicators));
        this.animalBehaviourVariant.disableProperty().bind(this.isReadOnly);
        this.animalBehaviourVariant.setValue(behaviourIndicators.get(0));

        this.saveButton.disableProperty().bind(this.isReadOnly);
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
        this.initialAnimalEnergy.getValueFactory().setValue(parameters.animalStartEnergy());
        this.animalMovementEnergy.getValueFactory().setValue(parameters.animalMoveEnergy());
        this.healthyAnimalEnergy.getValueFactory().setValue(parameters.healthyAnimalEnergy());
        this.animalReproductionEnergy.getValueFactory().setValue(parameters.reproductionEnergy());
        this.animalBehaviourVariant.setValue(parameters.animalBehaviourIndicatorVariant());
    }

    private SimulationParameters getSimulationParameters() {
        return new SimulationParameters(
            this.configurationName.getText(),

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

    @FXML
    private void runSimulation() {
        SimulationParameters parameters = this.getSimulationParameters();

        Optional<String> validationMessage = parameters.getValidationMessage();
        if (validationMessage.isPresent()) {
            this.showErrorMessage(validationMessage.get());
            return;
        }

        try {
            Simulation simulation = new Simulation(parameters);

            new SimulationWindow(
                String.format("%s (%s)", parameters.configurationName(), UUID.randomUUID()),
                simulation
            ).start(new Stage());

            this.showMessage("Running simulation...");
        } catch (InvalidSimulationConfigurationException exception) {
            this.showErrorMessage(exception.getMessage());
        } catch (IOException exception) {
            this.showErrorMessage("An error has occurred: " + exception);
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
        this.configurationVariant.setValue(this.observableConfigurationNames.get(0));
    }
}
