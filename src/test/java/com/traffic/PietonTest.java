package com.traffic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class PietonTest {
    private Pieton pieton;
    private Feu feu;

    @BeforeEach
    void setUp() {
        feu = new Feu("TestFeu", 1, 2, 1);
        pieton = new Pieton("TestPieton", feu);
    }
 
    @Test
    void testInitialPosition() {
        assertEquals(0, pieton.getPosition());
    }

    @Test
    void testTraverserFeuRouge() {
        pieton.traverser();
        assertEquals(0, pieton.getPosition());
        assertTrue(pieton.estEnAttente());
    }

    @Test
    void testTraverserFeuVert() {
        feu.changerEtat(); // Change to VERT
        pieton.traverser();
        assertEquals(1, pieton.getPosition());
        assertFalse(pieton.estEnAttente());
    }

    @Test
    void testSetFeu() {
        Feu nouveauFeu = new Feu("NouveauFeu", 1, 2, 1);
        pieton.setFeu(nouveauFeu);
        assertEquals(nouveauFeu, pieton.getFeu());
    }

    @Test
    void testGetId() {
        assertEquals("TestPieton", pieton.getId());
    }
} 


