package haricots;

public class Champ {
    private Variete varieteHaricot;
    private int nbHaricots;

    public boolean isEmpty() {
        return nbHaricots==0;
    }

    public void planter(Haricot haricot) throws PlantationInterditeException {
        if (varieteHaricot != null && varieteHaricot != haricot.getVariete()) throw new PlantationInterditeException();
        if (varieteHaricot == null)
            varieteHaricot = haricot.getVariete();
        nbHaricots += 1;
    }

    public Variete getVarieteHaricot() {
        return varieteHaricot;
    }

    public int getNbHaricots() {
        return nbHaricots;
    }

    public int[] getHaricotMetre() {
        return varieteHaricot.getHaricometre();
    }
}
