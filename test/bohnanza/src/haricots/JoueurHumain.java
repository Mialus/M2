package haricots;

import java.util.List;
import java.util.Scanner;


public class JoueurHumain extends Joueur {

    private Scanner scanner;

    public JoueurHumain(Plantation plantation, Main main, Scanner scanner) {
        super(plantation, main);
        this.scanner = scanner;
    }

    @Override
    protected boolean veutPlanterUnDeuxiemeHaricot(Haricot carte, Plantation plantation) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public int choisirChampARecolter(Plantation plantation, Haricot haricot) {
        System.out.println("Choix du champ à récolter");
        for (Champ champ : plantation.getChamps()) {
            System.out.println(champ);
        }
        return scanner.nextInt();
    }

    @Override
    protected Haricot choisirHaricotAplanter(List<Haricot> zoneDechange) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }


    private boolean getChoixDuJoueur() {
        System.out.println("Voulez vous planter le deuxième haricot de votre main");
        System.out.println(main);
        return scanner.nextBoolean();
    }
}
