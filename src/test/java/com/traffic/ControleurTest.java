package com.traffic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ControleurTest {
    private Controleur controleur;
    private Feu feu;
    private Vehicule vehicule;
    private Pieton pieton;

    @BeforeEach
    void setUp() {
        controleur = new Controleur();
        feu = new Feu("TestFeu", 1, 2, 1);
        vehicule = new Vehicule("TestVehicule", feu);
        pieton = new Pieton("TestPieton", feu);
    }

    @Test
    void testAjouterFeu() {
        controleur.ajouterFeu(feu);
        assertEquals(1, controleur.getFeux().size());
        assertEquals(feu, controleur.getFeux().get(0));
    }

    @Test
    void testAjouterVehicule() {
        controleur.ajouterVehicule(vehicule);
        assertEquals(1, controleur.getVehicules().size());
        assertEquals(vehicule, controleur.getVehicules().get(0));
    }

    @Test
    void testAjouterPieton() {
        controleur.ajouterPieton(pieton);
        assertEquals(1, controleur.getPietons().size());
        assertEquals(pieton, controleur.getPietons().get(0));
    }

    @Test
    void testCoordonnerFeuxUnCycle() {
        controleur.ajouterFeu(feu);
        Thread thread = new Thread(() -> {
            controleur.coordonnerFeux();
        });
        thread.start();

        try {
            Thread.sleep(2000); // Attendre 2 secondes
            controleur.arreter();
            thread.join(1000); // Attendre que le thread se termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertFalse(thread.isAlive());
    }

    @Test
    void testSurveillerUsagers() {
        controleur.ajouterFeu(feu);
        controleur.ajouterVehicule(vehicule);
        controleur.ajouterPieton(pieton);

        Thread thread = new Thread(() -> {
            controleur.surveillerUsagers();
        });
        thread.start();

        try {
            Thread.sleep(2000); // Attendre 2 secondes
            controleur.arreter();
            thread.join(1000); // Attendre que le thread se termine
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        assertFalse(thread.isAlive());
    }
}