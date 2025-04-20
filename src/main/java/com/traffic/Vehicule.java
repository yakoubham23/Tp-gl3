package com.traffic;

public class Vehicule {
    private String id;
    private int position;
    private Feu feu;
    private int vitesse;
    private static final int POSITION_MAX = 1000;
    private static final int VITESSE_MAX = 10; // Vitesse maximale en unités par seconde
    private static final int VITESSE_MIN = 1; // Vitesse minimale en unités par seconde

    public Vehicule(String id, Feu feu) {
        this.id = id;
        this.position = 0;
        this.feu = feu;
        this.vitesse = 5; // Vitesse par défaut
    }

    public void setVitesse(int vitesse) {
        // Limiter la vitesse entre VITESSE_MIN et VITESSE_MAX
        this.vitesse = Math.max(VITESSE_MIN, Math.min(vitesse, VITESSE_MAX));
    }

    public void attendreFeuVert() throws InterruptedException {
        while (feu.getEtat() == Feu.Etat.ROUGE) {
            System.out.println("Véhicule " + id + " arrêté au feu rouge. Position: " + position);
            Thread.sleep(100); // Vérification plus fréquente
        }
    }

    public void avancer() throws InterruptedException {
        if (feu.getEtat() != Feu.Etat.ROUGE) {
            position = (position + vitesse) % POSITION_MAX;
            System.out.println("Véhicule " + id + " avance. Position: " + position + " (Feu: " + feu.getEtat() + ")");
            Thread.sleep(100); // Délai plus court pour des mouvements plus fluides
        }
    }

    public int getPosition() {
        return position;
    }

    public String getId() {
        return id;
    }

    public boolean estEnMouvement() {
        return feu.getEtat() != Feu.Etat.ROUGE;
    }

    public void setFeu(Feu feu) {
        this.feu = feu;
    }

    public Feu getFeu() {
        return feu;
    }

    public int getVitesse() {
        return vitesse;
    }
}