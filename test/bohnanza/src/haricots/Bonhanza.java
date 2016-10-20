package haricots;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Bonhanza {

    public static void main(String[] args) throws NombreDeJoueursInsuffisantsException, NombreDeJoueursTropImportantException {
        List<Joueur> joueurs = inscritJoueur();
        Pioche pioche = Pioche.Factory.createPiocheFor(joueurs.size());

        Game game = new Game(pioche, joueurs);
        game.distribuer();
        game.jouer();
        Joueur vainqueur = game.proclameVainqueur();
    }

    private static List<Joueur> inscritJoueur() {
        Scanner scanner = new Scanner(System.in);
        List<Joueur> lesJoueurs = new ArrayList<Joueur>();
        System.out.println("Nombre de joueurs : ");
        int nbJoueur = Integer.valueOf(scanner.nextLine());
        for(int i=0; i<nbJoueur; i++){
            boolean erreur = false;
            do {
                System.out.println("Type de joueur : [H]umain / [O]rdinateur ");
                String typeJoueur = scanner.nextLine();
                if ("H".equals(typeJoueur)) lesJoueurs.add(JoueurFactory.createJoueurHumain(nbJoueur));
                else if ("O".equals(typeJoueur)) lesJoueurs.add(JoueurFactory.createJoueurOrdi(nbJoueur));
                else erreur = true;
            } while (erreur);
        }
        return lesJoueurs;
    }
}
