package com.traffic;

public class Feu {
    public enum Etat {
        ROUGE,
        VERT,
        JAUNE
    }

    private String id;
    private Etat etat;
    private int dureeRouge;
    private int dureeVert;
    private int dureeJaune;
    private boolean enFonctionnement;

    public Feu(String id, int dureeRouge, int dureeVert, int dureeJaune) {
        this.id = id;
        this.dureeRouge = dureeRouge;
        this.dureeVert = dureeVert;
        this.dureeJaune = dureeJaune;
        this.etat = Etat.ROUGE;
        this.enFonctionnement = false;
    }

    public void demarrer() {
        enFonctionnement = true;
        Thread thread = new Thread(() -> {
            while (enFonctionnement) {
                try {
                    switch (etat) {
                        case ROUGE:
                            Thread.sleep(dureeRouge * 1000);
                            etat = Etat.VERT;
                            break;
                        case VERT:
                            Thread.sleep(dureeVert * 1000);
                            etat = Etat.JAUNE;
                            break;
                        case JAUNE:
                            Thread.sleep(dureeJaune * 1000);
                            etat = Etat.ROUGE;
                            break;
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    break;
                }
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    public void arreter() {
        enFonctionnement = false;
    }

    public Etat getEtat() {
        return etat;
    }

    public String getId() {
        return id;
    }

    public void setDureeRouge(int dureeRouge) {
        this.dureeRouge = dureeRouge;
    }

    public void setDureeVert(int dureeVert) {
        this.dureeVert = dureeVert;
    }

    public void setDureeJaune(int dureeJaune) {
        this.dureeJaune = dureeJaune;
    }

    public int getDureeRouge() {
        return dureeRouge;
    }

    public int getDureeVert() {
        return dureeVert;
    }

    public int getDureeJaune() {
        return dureeJaune;
    }

    public boolean estVert() {
        return etat == Etat.VERT;
    }

    public boolean estRouge() {
        return etat == Etat.ROUGE;
    }

    public boolean estJaune() {
        return etat == Etat.JAUNE;
    }
} 