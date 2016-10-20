package haricots;

import java.util.ArrayList;
import java.util.List;

public abstract class Joueur {
    protected Main main;
    protected Plantation plantation;
    protected int thunes;
    protected List<Haricot> zoneDechange;
    private boolean actif;
    protected Pioche pioche;


    public Joueur(Plantation plantation, Main main) {
        this.main = main;
        this.plantation = plantation;
        thunes = 0;
        zoneDechange = new ArrayList<Haricot>();
    }

    public boolean planterPremierePhase() {
        if (main.isEmpty()) return false;

        planterPremierHaricotDeLaMain();
        if (veutPlanterUnDeuxiemeHaricot(main.getCarte(0), plantation)) {
            planterPremierHaricotDeLaMain();
        }
        return true;
    }

    private void planterPremierHaricotDeLaMain() {
        Haricot haricot;
        haricot = main.retirePremiereCarte();
        planter(haricot);
    }

    private void planter(Haricot haricot) {
        if (!plantation.planter(haricot)) {
            int numChamp = choisirChampARecolter(plantation, haricot);
            Champ champ = plantation.getChampsNumero(numChamp);
            thunes += plantation.recolte(champ);
            plantation.planter(haricot);
        }
    }

    protected abstract boolean veutPlanterUnDeuxiemeHaricot(Haricot carte, Plantation plantation);

    protected abstract int choisirChampARecolter(Plantation plantation, Haricot haricot);

    public void planterHaricotsEchanges() {
        while (!zoneDechange.isEmpty()){
            Haricot haricot  = choisirHaricotAplanter(zoneDechange);
            planter(haricot);
        }
    }

    protected abstract Haricot choisirHaricotAplanter(List<Haricot> zoneDechange);

    protected boolean echanger(List<Joueur> joueurs) {
        throw new RuntimeException("not yet implemented");
    }

    public void piocher2cartes() {
        for (int i = 0; i < 2; i++) {
            Haricot carte = pioche.top();
            zoneDechange.add(carte);
            if (pioche.isEpuisee()) return;
            if (pioche.isEmpty())
                pioche.melanger();
        }
    }

    public boolean piocher3Cartes() {
        for (int i = 0; i < 3; i++) {
            Haricot carte;
            carte = pioche.top();
            if (pioche.isEpuisee())
                return true; // le jeu est terminé
            if (pioche.isEmpty())
                pioche.melanger();
            main.add(carte);
        }
        return false; // le jeu continue
    }

    public Plantation getPlantation() {
        return plantation;
    }

    public int getThunes() {
        return thunes;
    }

    public void setActif(boolean actif) {
        this.actif = actif;
    }

    public boolean isActif() {
        return actif;
    }

    public void setPioche(Pioche pioche) {
        this.pioche = pioche;
    }

    public void metEnMain(Haricot top) {
        main.add(top);
    }
}
