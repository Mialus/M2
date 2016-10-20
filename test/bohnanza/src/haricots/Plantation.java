package haricots;

import com.sun.corba.se.impl.oa.poa.ActiveObjectMap;

import java.util.ArrayList;
import java.util.List;

public class Plantation {
    List<Champ> champs;

    public Plantation(List<Champ> champs) {
        this.champs = new ArrayList<Champ>(champs);
    }

    public boolean planter(Haricot haricot) {
        try {
            if (planterInFieldWithSameBeans(haricot)) return true;
            return planterInEmptyField(haricot);
        } catch (PlantationInterditeException e) {
            return false;
        }
    }

    private boolean planterInEmptyField(Haricot haricot) throws PlantationInterditeException {
        for (Champ champ : champs) {
            if (champ.isEmpty()) {
                champ.planter(haricot);
                return true;
            }
        }
        return false;
    }

    private boolean planterInFieldWithSameBeans(Haricot haricot) throws PlantationInterditeException {
        Champ champ = fieldWithSameBeans(haricot);
        if (champ != null) {
            champ.planter(haricot);
            return true;
        }
        return false;
    }

    private Champ fieldWithSameBeans(Haricot haricot) {
        for (Champ champ : champs) {
            if (champ.getVarieteHaricot() == haricot.getVariete()) {
                return champ;
            }
        }
        return null;
    }

    public int recolte(Champ champ) {
        int[] haricotMetre = champ.getHaricotMetre();
        int thunes;
        for (int i = 0; i < haricotMetre.length; i++) {
            if (champ.getNbHaricots() < haricotMetre[i]) {
                if (i == 0) thunes = 0;
                else
                    thunes = champ.getVarieteHaricot().getThunes(i - 1);
                int numeroChamp = champs.indexOf(champ);
                champs.remove(champ);
                champs.add(numeroChamp, new  Champ());
                return thunes;
            }
        }
        thunes = champ.getVarieteHaricot().getThunes(haricotMetre.length - 1);
        int numeroChamp = champs.indexOf(champ);
        champs.remove(champ);
        champs.add(numeroChamp, new Champ());
        return thunes;
    }

    public List<Champ> getChamps() {
        return champs;
    }

    public Champ getChampsNumero(int numero) {
        return champs.get(numero - 1);
    }

    public int getNumeroChamp(Champ champ) {
        return champs.indexOf(champ)+1;
    }

    public boolean hasEmptyField() {
        for (Champ champ : champs) {
            if (champ.isEmpty()) return true;
        }
        return false;
    }

    public boolean hasFieldOfSameVarieteAs(Haricot carte) {
        return fieldWithSameBeans(carte)!=null;
    }
}