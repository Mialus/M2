package haricots;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Pioche {

    private List<Haricot> laPioche;
    private List<Haricot> laDefausse;
    private Random random;
    private int nbTourDePioche;
    private boolean piocheEpuise;

    Pioche(List<Haricot> lesCartes, Random random) {
        this.random = random;
        laPioche = new ArrayList<Haricot>(lesCartes);
        laDefausse = new ArrayList<Haricot>();
        nbTourDePioche = 1;
        piocheEpuise = false;
    }

    public boolean isEmpty() {
        return laPioche.isEmpty();
    }

    public Haricot top() {
        Haricot carte = laPioche.remove(random.nextInt(laPioche.size()));
        if (isEmpty())
            if (troisiemeFois()) piocheEpuise = true;
        return carte;
    }

    public void melanger() {
        nbTourDePioche++;
        laPioche.addAll(laDefausse);
        laDefausse.clear();
    }

    private boolean troisiemeFois() {
        return nbTourDePioche >= 3;
    }

    public boolean isEpuisee() {
        return piocheEpuise;
    }


    public static class Factory {
        public static Pioche createPioche() {
            int[] qte = new int[]{6, 22, 20, 18, 16, 14, 12, 10, 4, 24, 8};
            return createPioche(qte);
        }

        public static Pioche createPiocheFor3players() {
            int[] qte = new int[]{6, 22, 20, 18, 16, 14, 12, 10, 0, 24, 8};
            return createPioche(qte);
        }

        public static Pioche createPiocheFor4or5players() {
            int[] qte = new int[]{6, 22, 20, 18, 16, 14, 12, 10, 4, 0, 8};
            return createPioche(qte);
        }

        public static Pioche createPiocheFor6or7players() {
            int[] qte = new int[]{0, 22, 20, 18, 16, 14, 12, 10, 0, 24, 8};
            return createPioche(qte);
        }

        private static Pioche createPioche(int[] qte) {
            List<Haricot> lesCartes = new ArrayList<Haricot>();
            for (Variete variete : Variete.values()) {
                for (int i = 0; i < qte[variete.ordinal()]; i++) {
                    lesCartes.add(new Haricot(variete));
                }
            }
            return new Pioche(lesCartes, new Random());
        }

        public static Pioche createPiocheFor(int size) throws NombreDeJoueursTropImportantException, NombreDeJoueursInsuffisantsException {
            if (size <3) throw new NombreDeJoueursInsuffisantsException();
            if (size < 4) return createPiocheFor3players();
            if (size < 6) return createPiocheFor4or5players();
            if (size < 8) return createPiocheFor6or7players();
            throw new NombreDeJoueursTropImportantException();
        }
    }
}
