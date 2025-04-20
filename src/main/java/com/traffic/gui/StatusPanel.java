package com.traffic.gui;

import com.traffic.Controleur;
import com.traffic.Feu;
import com.traffic.Pieton;
import com.traffic.Vehicule;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

public class StatusPanel extends VBox {
    private Label vehiclesLabel;
    private Label pedestriansLabel;
    private Label statusLabel;
    private Controleur controleur;

    public StatusPanel(Controleur controleur) {
        this.controleur = controleur;
        this.setPadding(new Insets(10));
        this.setSpacing(5);

        vehiclesLabel = new Label("Véhicules: 0");
        pedestriansLabel = new Label("Piétons: 0");
        statusLabel = new Label("État: Arrêté");

        this.getChildren().addAll(vehiclesLabel, pedestriansLabel, statusLabel);

        startUpdateThread();
    }

    private void startUpdateThread() {
        Thread updateThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    Thread.sleep(500);
                    updateStatus();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        updateThread.setDaemon(true);
        updateThread.start();
    }

    private void updateStatus() {
        Platform.runLater(() -> {
            StringBuilder vehiclesStatus = new StringBuilder("Véhicules:\n");
            for (Vehicule v : controleur.getVehicules()) {
                vehiclesStatus.append(String.format("%s - Position: %d%s\n",
                        v.getId(), v.getPosition(),
                        v.estEnMouvement() ? " (En mouvement)" : " (Arrêté)"));
            }

            StringBuilder pedestriansStatus = new StringBuilder("Piétons:\n");
            for (Pieton p : controleur.getPietons()) {
                pedestriansStatus.append(String.format("%s - Position: %d%s\n",
                        p.getId(), p.getPosition(),
                        p.estEnAttente() ? " (En attente)" : " (En mouvement)"));
            }

            vehiclesLabel.setText(vehiclesStatus.toString());
            pedestriansLabel.setText(pedestriansStatus.toString());
        });
    }

    public void updateTrafficLight(Feu.Etat etat) {
        String status = "État: ";
        switch (etat) {
            case ROUGE:
                status += "Feu Rouge";
                break;
            case VERT:
                status += "Feu Vert";
                break;
            case JAUNE:
                status += "Feu Jaune";
                break;
        }
        statusLabel.setText(status);
    }
}