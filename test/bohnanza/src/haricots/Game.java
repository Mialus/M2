package haricots;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class Game {

    private List<Joueur> joueurs;
    private Pioche pioche;

    public Game(Pioche pioche, List<Joueur> joueurs) {
        this.pioche = pioche;
        this.joueurs = new ArrayList<Joueur>(joueurs);
    }

    public void distribuer() {
        for (Joueur joueur : joueurs) {
            joueur.setPioche(pioche);
        }
        for (int i = 0; i < 5; i++) {
            for (Joueur joueur : joueurs) {
                joueur.metEnMain(pioche.top());
            }
        }
    }

    public void jouer(){
        boolean finDeJeu = false;
        Iterator<Joueur> joueurActifIterator = joueurs.iterator();
        Joueur joueurActif;
        while (!finDeJeu) {

            if (joueurActifIterator.hasNext())
                joueurActif = joueurActifIterator.next();
            else {
                joueurActifIterator = joueurs.iterator();
                joueurActif = joueurActifIterator.next();
            }
            joueurActif.setActif(true);

            joueurActif.planterPremierePhase();
            joueurActif.piocher2cartes();
            echangesPhase2(joueurActif);
            planterPhase3();
            finDeJeu = joueurActif.piocher3Cartes();
            joueurActif.setActif(false);
        }

    }

    private void echangesPhase2(Joueur joueurActif) {
        boolean echange = false;
        do {
            for (Joueur joueur : joueurs) {
                if (joueur.isActif()) {
                    boolean auMoinsUnEchange = joueur.echanger(joueurs);
                    echange = echange || auMoinsUnEchange;
                }
                else {
                    boolean auMoinsUnEchange = joueur.echanger(Arrays.asList(joueurActif));
                    echange = echange || auMoinsUnEchange;
                }
            }
        } while (echange);
    }

    private void planterPhase3() {
        for (Joueur joueur : joueurs) {
            joueur.planterHaricotsEchanges();
        }
    }

    public Joueur proclameVainqueur() {
        return null;  //To change body of created methods use File | Settings | File Templates.
    }
}
