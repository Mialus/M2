package haricots;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoueurOrdi extends Joueur {

    public JoueurOrdi(Plantation plantation, Main main) {
        super(plantation, main);
    }

    @Override
    protected boolean veutPlanterUnDeuxiemeHaricot(Haricot carte, Plantation plantation) {
        return plantation.hasEmptyField() || plantation.hasFieldOfSameVarieteAs(carte);
    }

    @Override
    protected int choisirChampARecolter(Plantation plantation, Haricot haricot) {
        List<Champ> champs = plantation.getChamps();
        List<Champ> champsWithMoreThanOneBean = removeChampsWithOneBean(champs);
        if (champsWithMoreThanOneBean.size()==1) return plantation.getNumeroChamp(champsWithMoreThanOneBean.get(0));
        Champ meilleurChamp;
        if (champsWithMoreThanOneBean.isEmpty()) {
            meilleurChamp = chercheMeilleurRapport(plantation, champs, haricot);
        }
        else    {
            meilleurChamp = chercheMeilleurRapport(plantation, champsWithMoreThanOneBean, haricot);
        }
        return plantation.getNumeroChamp(meilleurChamp);
    }

    @Override
    protected Haricot choisirHaricotAplanter(List<Haricot> zoneDechange) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private Champ chercheMeilleurRapport(Plantation plantation, List<Champ> champs, Haricot haricot) {
        Map<Champ, Integer> costMap = new HashMap<Champ, Integer>();
        Champ meilleurChamp;
        for (Champ champ : champs) {
            int cout = calculeCout(champ, main);
            costMap.put(champ, cout);
        }
        meilleurChamp = getBestField(costMap);
        return meilleurChamp;
    }

    private int calculeCout(Champ champ, Main main) {
        int cost =0;
        List<Variete> varieteEnMain = main.getVarieteEnMain();
        if (varieteEnMain.contains(champ.getVarieteHaricot())) {
            cost += varieteEnMain.indexOf(champ.getVarieteHaricot());
        } else cost += 50;
        if (champ.getNbHaricots() >= champ.getHaricotMetre()[champ.getHaricotMetre().length-1])
            cost += 100;
        return cost;
    }


    Champ getBestField(Map<Champ, Integer> costMap) {
        int maxCost = 0;
        List<Champ> bestField = new ArrayList<Champ>();
        for (Map.Entry<Champ, Integer> champIntegerEntry : costMap.entrySet()) {
            Integer cost = champIntegerEntry.getValue();
            if (cost.intValue() >maxCost) {
                maxCost = cost.intValue();
                bestField.clear();
                bestField.add(champIntegerEntry.getKey());
            } else if (cost==maxCost) {
                bestField.add(champIntegerEntry.getKey());
            }
        }
        if (bestField.size()==1) return bestField.get(0);
        return bestField.get(0);
    }

    private int choixSurVarieteUniquement(Plantation plantation, Main main) {
        List<Champ> champs = plantation.getChamps();
        List<Variete> varieteEnMain = main.getVarieteEnMain();
        List<Champ> champsInutiles = new ArrayList<Champ>();
        for (Champ champ : champs) {
            if (!varieteEnMain.contains(champ.getVarieteHaricot())) champsInutiles.add(champ);
        }

        return 0;

    }

    private List<Champ> removeChampsWithOneBean(List<Champ> champs) {
        List<Champ> result = new ArrayList<Champ>();
        for (Champ champ : champs) {
            if (champ.getNbHaricots()>1) result.add(champ);
        }
        return result;
    }

}
