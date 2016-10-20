package haricots;

import java.util.ArrayList;
import java.util.List;

public class Main {
    private List<Haricot> main;

    public Main(List<Haricot> main) {
        this.main = new ArrayList<Haricot>(main);
    }

    public boolean isEmpty() {
        return main.isEmpty();
    }

    public Haricot retirePremiereCarte() {
        return main.remove(0);
    }

    public List<Variete> getVarieteEnMain() {
        List<Variete> varietesEnMain = new ArrayList<Variete>();
        for (Haricot haricot : main) {
            varietesEnMain.add(haricot.getVariete());
        }
        return varietesEnMain;
    }

    public Haricot getCarte(int rang) {
        return main.get(rang);
    }

    public void add(Haricot carte) {
        main.add(carte);
    }
}
