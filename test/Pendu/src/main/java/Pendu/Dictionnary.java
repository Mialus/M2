package Pendu;

import java.io.InputStream;
import java.util.*;

public class Dictionnary {

    List<String> lesMots;
    private Random random;

    public Dictionnary(InputStream file, Random random) {
        this.random = random;
        lesMots = new ArrayList<String>();
        initialise(file);
    }

    private void initialise(InputStream file) { // file devient paramètre de la fonction pour la rendre indépendante du fichier sur disque
        Scanner reader;
        reader = new Scanner(file);
        while (reader.hasNext())
            lesMots.add(reader.nextLine());
    }


    public String unMotAuHazard() { // Random passe en attribut de la classe et initialisé dans le constructuer : suppression de la dépendance entre Dictionnary et Random
        return lesMots.get(random.nextInt(lesMots.size()));
    }
}
