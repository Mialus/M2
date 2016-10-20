package Pendu;

import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;

public class JoueurUnitTest {

    @Test
    public void testEstMort() {
        Joueur joueur = new Joueur();
        Assert.assertFalse(joueur.estMort());
        joueur.perdVie();
        joueur.perdVie();
        joueur.perdVie();
        joueur.perdVie();
        joueur.perdVie();
        joueur.perdVie();
        joueur.perdVie();
        joueur.perdVie();
        joueur.perdVie();
        Assert.assertFalse(joueur.estMort());
        joueur.perdVie();
        Assert.assertTrue(joueur.estMort());
    }

    @Test
    public void testProposeLettre() {
        Joueur joueur = new Joueur();
        Assert.assertEquals('x', joueur.proposeLettre(new Scanner("x")));
        Assert.assertEquals('x', joueur.proposeLettre(new Scanner("boonjour\nx")));
    }
}
