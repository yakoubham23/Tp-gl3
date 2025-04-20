package com.traffic.gui;

import com.traffic.Controleur;
import com.traffic.Feu;
import com.traffic.Pieton;
import com.traffic.Vehicule;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class TrafficLightApp extends Application {
    private Controleur controleur;
    private Circle redLight;
    private Circle yellowLight;
    private Circle greenLight;
    private Button startButton;
    private Button stopButton;
    private Button addVehicleButton;
    private Button addPedestrianButton;
    private StatusPanel statusPanel;
    private Spinner<Integer> redTimeSpinner;
    private Spinner<Integer> greenTimeSpinner;
    private Spinner<Integer> yellowTimeSpinner;
    private Spinner<Integer> vehicleSpeedSpinner;
    private Spinner<Integer> pedestrianSpeedSpinner;

    @Override
    public void start(Stage primaryStage) {
        controleur = new Controleur();
        Feu feu = new Feu("Feu1", 5, 5, 2);
        controleur.ajouterFeu(feu);

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));

        // Traffic Light
        VBox trafficLight = createTrafficLight();

        // Timing Controls
        GridPane timingControls = createTimingControls();

        // Control Buttons
        HBox controls = createControls();

        // Status Panel
        statusPanel = new StatusPanel(controleur);

        root.getChildren().addAll(trafficLight, timingControls, controls, statusPanel);

        Scene scene = new Scene(root, 500, 700);
        primaryStage.setTitle("Système de Feu de Signalisation");
        primaryStage.setScene(scene);
        primaryStage.show();

        // Handle window closing
        primaryStage.setOnCloseRequest(e -> {
            controleur.arreter();
            Platform.exit();
        });
    }

    private GridPane createTimingControls() {
        GridPane timingControls = new GridPane();
        timingControls.setHgap(10);
        timingControls.setVgap(10);
        timingControls.setPadding(new Insets(10));
        timingControls.setAlignment(Pos.CENTER);

        // Red Light Duration
        Label redLabel = new Label("Durée Rouge (s):");
        redTimeSpinner = new Spinner<>(5, 60, 30, 1);
        redTimeSpinner.setPrefWidth(80);

        // Green Light Duration
        Label greenLabel = new Label("Durée Vert (s):");
        greenTimeSpinner = new Spinner<>(5, 60, 25, 1);
        greenTimeSpinner.setPrefWidth(80);

        // Yellow Light Duration
        Label yellowLabel = new Label("Durée Jaune (s):");
        yellowTimeSpinner = new Spinner<>(2, 10, 5, 1);
        yellowTimeSpinner.setPrefWidth(80);

        // Vehicle Speed
        Label vehicleSpeedLabel = new Label("Vitesse Véhicule (m/s):");
        vehicleSpeedSpinner = new Spinner<>(1, 20, 10, 1);
        vehicleSpeedSpinner.setPrefWidth(80);

        // Pedestrian Speed
        Label pedestrianSpeedLabel = new Label("Vitesse Piéton (m/s):");
        pedestrianSpeedSpinner = new Spinner<>(1, 10, 5, 1);
        pedestrianSpeedSpinner.setPrefWidth(80);

        timingControls.addRow(0, redLabel, redTimeSpinner);
        timingControls.addRow(1, greenLabel, greenTimeSpinner);
        timingControls.addRow(2, yellowLabel, yellowTimeSpinner);
        timingControls.addRow(3, vehicleSpeedLabel, vehicleSpeedSpinner);
        timingControls.addRow(4, pedestrianSpeedLabel, pedestrianSpeedSpinner);

        return timingControls;
    }

    private VBox createTrafficLight() {
        VBox trafficLight = new VBox(10);
        trafficLight.setStyle("-fx-background-color: black; -fx-padding: 10;");
        trafficLight.setAlignment(Pos.CENTER);

        redLight = new Circle(30, Color.DARKRED);
        yellowLight = new Circle(30, Color.DARKGOLDENROD);
        greenLight = new Circle(30, Color.DARKGREEN);

        trafficLight.getChildren().addAll(redLight, yellowLight, greenLight);
        return trafficLight;
    }

    private HBox createControls() {
        HBox controls = new HBox(10);
        controls.setAlignment(Pos.CENTER);

        startButton = new Button("Démarrer");
        stopButton = new Button("Arrêter");
        addVehicleButton = new Button("Ajouter Véhicule");
        addPedestrianButton = new Button("Ajouter Piéton");

        stopButton.setDisable(true);

        startButton.setOnAction(e -> startSimulation());
        stopButton.setOnAction(e -> stopSimulation());
        addVehicleButton.setOnAction(e -> addVehicle());
        addPedestrianButton.setOnAction(e -> addPedestrian());

        controls.getChildren().addAll(startButton, stopButton, addVehicleButton, addPedestrianButton);
        return controls;
    }

    private void startSimulation() {
        startButton.setDisable(true);
        stopButton.setDisable(false);

        // Get timing values from spinners
        int redDuration = redTimeSpinner.getValue();
        int greenDuration = greenTimeSpinner.getValue();
        int yellowDuration = yellowTimeSpinner.getValue();
        int vehicleSpeed = vehicleSpeedSpinner.getValue();
        int pedestrianSpeed = pedestrianSpeedSpinner.getValue();

        // Set traffic light timing
        controleur.getFeux().get(0).setDureeRouge(redDuration);
        controleur.getFeux().get(0).setDureeVert(greenDuration);
        controleur.getFeux().get(0).setDureeJaune(yellowDuration);

        // Set vehicle and pedestrian speeds
        for (Vehicule vehicule : controleur.getVehicules()) {
            vehicule.setVitesse(vehicleSpeed);
        }
        for (Pieton pieton : controleur.getPietons()) {
            pieton.setVitesse(pedestrianSpeed);
        }

        // Start traffic light
        controleur.demarrer();

        // Start UI update thread
        new Thread(() -> {
            while (controleur.estEnFonctionnement()) {
                Platform.runLater(() -> {
                    statusPanel.updateTrafficLight(controleur.getFeux().get(0).getEtat());
                    updateTrafficLightUI();
                });
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        // Start vehicle and pedestrian threads
        for (Vehicule vehicule : controleur.getVehicules()) {
            new Thread(() -> {
                while (controleur.estEnFonctionnement()) {
                    try {
                        vehicule.attendreFeuVert();
                        vehicule.avancer();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (Pieton pieton : controleur.getPietons()) {
            new Thread(() -> {
                while (controleur.estEnFonctionnement()) {
                    try {
                        pieton.attendreFeuVert();
                        pieton.traverser();
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    private void stopSimulation() {
        controleur.arreter();
        startButton.setDisable(false);
        stopButton.setDisable(true);
    }

    private void updateTrafficLightUI() {
        if (controleur.getFeux().isEmpty())
            return;

        Feu feu = controleur.getFeux().get(0);
        Platform.runLater(() -> {
            switch (feu.getEtat()) {
                case ROUGE:
                    redLight.setFill(Color.RED);
                    yellowLight.setFill(Color.DARKGOLDENROD);
                    greenLight.setFill(Color.DARKGREEN);
                    break;
                case JAUNE:
                    redLight.setFill(Color.DARKRED);
                    yellowLight.setFill(Color.YELLOW);
                    greenLight.setFill(Color.DARKGREEN);
                    break;
                case VERT:
                    redLight.setFill(Color.DARKRED);
                    yellowLight.setFill(Color.DARKGOLDENROD);
                    greenLight.setFill(Color.LIME);
                    break;
            }
        });
    }

    private void addVehicle() {
        if (!controleur.getFeux().isEmpty()) {
            Vehicule vehicule = new Vehicule("V" + (controleur.getVehicules().size() + 1),
                    controleur.getFeux().get(0));
            vehicule.setVitesse(vehicleSpeedSpinner.getValue());
            controleur.ajouterVehicule(vehicule);
        }
    }

    private void addPedestrian() {
        if (!controleur.getFeux().isEmpty()) {
            Pieton pieton = new Pieton("P" + (controleur.getPietons().size() + 1),
                    controleur.getFeux().get(0));
            pieton.setVitesse(pedestrianSpeedSpinner.getValue());
            controleur.ajouterPieton(pieton);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}