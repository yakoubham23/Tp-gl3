package com.traffic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class VehiculeTest {
    private Vehicule vehicule;
    private Feu feu;

    @BeforeEach
    void setUp() {
        feu = new Feu("TestFeu", 1, 2, 1);
        vehicule = new Vehicule("TestVehicule", feu);
    }

    @Test
    void testInitialPosition() {
        assertEquals(0, vehicule.getPosition());
    }

    @Test
    void testAvancerFeuRouge() {
        vehicule.avancer();
        assertEquals(0, vehicule.getPosition());
    }

    @Test
    void testAvancerFeuVert() {
        feu.changerEtat(); // Change to VERT
        vehicule.avancer();
        assertEquals(1, vehicule.getPosition());
    }

    @Test
    void testEstEnMouvement() {
        assertFalse(vehicule.estEnMouvement());
        feu.changerEtat(); // Change to VERT
        vehicule.avancer();
        assertTrue(vehicule.estEnMouvement());
    }

    @Test
    void testSetFeu() {
        Feu nouveauFeu = new Feu("NouveauFeu", 1, 2, 1);
        vehicule.setFeu(nouveauFeu);
        assertEquals(nouveauFeu, vehicule.getFeu());
    }

    @Test
    void testGetId() {
        assertEquals("TestVehicule", vehicule.getId());
    }
} 