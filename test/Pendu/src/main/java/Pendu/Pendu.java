package Pendu;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

public class Pendu {

    public static void main(String[] args) throws FileNotFoundException, MotReveleException {
        Dictionnary dico = new Dictionnary(new FileInputStream(new File("resources/francais-divers1.txt")), new Random());
        Joueur joueur = new Joueur();
        Mot mot = new Mot.Factory().createMot(dico);
        Game jeu = new Game(joueur, mot);
        jeu.play();
    }
}
