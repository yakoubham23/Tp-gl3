package com.traffic;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class FeuTest {
    private Feu feu;

    @BeforeEach
    void setUp() {
        feu = new Feu("TestFeu", 2, 3, 1);
    }

    @Test
    void testInitialEtat() {
        assertEquals(Feu.Etat.ROUGE, feu.getEtat());
    }

    @Test
    void testChangementEtat() {
        feu.changerEtat();
        assertEquals(Feu.Etat.VERT, feu.getEtat());

        feu.changerEtat();
        assertEquals(Feu.Etat.JAUNE, feu.getEtat());

        feu.changerEtat();
        assertEquals(Feu.Etat.ROUGE, feu.getEtat());
    }

    @Test
    void testEstVert() {
        assertFalse(feu.estVert());
        feu.changerEtat();
        assertTrue(feu.estVert());
    }

    @Test
    void testEstRouge() {
        assertTrue(feu.estRouge());
        feu.changerEtat();
        assertFalse(feu.estRouge());
    }

    @Test
    void testEstJaune() {
        assertFalse(feu.estJaune());
        feu.changerEtat();
        feu.changerEtat();
        assertTrue(feu.estJaune());
    }

    @Test
    void testGetId() {
        assertEquals("TestFeu", feu.getId());
    }
}