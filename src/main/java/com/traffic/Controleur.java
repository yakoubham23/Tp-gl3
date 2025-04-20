package com.traffic;

import java.util.ArrayList;
import java.util.List;

public class Controleur {
    private List<Feu> feux;
    private List<Vehicule> vehicules;
    private List<Pieton> pietons;
    private boolean enFonctionnement;

    public Controleur() {
        feux = new ArrayList<>();
        vehicules = new ArrayList<>();
        pietons = new ArrayList<>();
        enFonctionnement = false;
    }

    public void ajouterFeu(Feu feu) {
        feux.add(feu);
    }

    public void ajouterVehicule(Vehicule vehicule) {
        vehicules.add(vehicule);
    }

    public void ajouterPieton(Pieton pieton) {
        pietons.add(pieton);
    }

    public void demarrer() {
        enFonctionnement = true;
        for (Feu feu : feux) {
            feu.demarrer();
        }
    }

    public void arreter() {
        enFonctionnement = false;
        for (Feu feu : feux) {
            feu.arreter();
        }
    }

    public List<Feu> getFeux() {
        return feux;
    }

    public List<Vehicule> getVehicules() {
        return vehicules;
    }

    public List<Pieton> getPietons() {
        return pietons;
    }

    public boolean estEnFonctionnement() {
        return enFonctionnement;
    }
}