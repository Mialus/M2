package haricots;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class JoueurFactory {
    public static Joueur createJoueurHumain(int nbJoueur) {
        List<Champ> lesChamps = createChamps(nbJoueur);
        return new JoueurHumain(new Plantation(lesChamps), new Main(new ArrayList<Haricot>()), new Scanner(System.in));
    }

    public static Joueur createJoueurOrdi(int nbJoueur) {
        List<Champ> lesChamps = createChamps(nbJoueur);
        return new JoueurOrdi(new Plantation(lesChamps), new Main(new ArrayList<Haricot>()));
    }

    private static List<Champ> createChamps(int nbJoueur) {
        List<Champ> lesChamps = new ArrayList<Champ>();
        lesChamps.add(new Champ());
        lesChamps.add(new Champ());
        if (nbJoueur == 3) lesChamps.add(new Champ());
        return lesChamps;
    }
}
