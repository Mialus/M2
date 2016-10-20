package Pendu;

import java.util.ArrayList;
import java.util.List;

public class Mot {
    private String motCache;
    private List<Character> lettresDecouvertes;
    private List<Character> lettresDuMot;
    private boolean motRevele;

    private Mot(String motCache, List<Character> lettresDuMot) {
        this.motCache = motCache;
        this.lettresDuMot = lettresDuMot;
        lettresDecouvertes = new ArrayList<Character>();
        motRevele = false;
    }

    public void affiche() {
        StringBuilder builder = buildAffichage();
        System.out.println(builder.toString());
    }

    StringBuilder buildAffichage() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < motCache.length(); i++) {
              if (lettresDecouvertes.contains(motCache.charAt(i)) )
                  builder.append(motCache.charAt(i));
            else
                  builder.append("-");
        }
        builder.append('\n');
        return builder;
    }

    public boolean contient(char lettre) throws MotReveleException {
        if (motRevele)
            throw new MotReveleException("la methode contient ne peut être appelée sur un mot révélé");
        return motCache.indexOf(lettre) != -1;
    }

    public void decouvre(char lettre) throws MotReveleException {
        if (motRevele)
            throw new MotReveleException("la méthode decouvre ne peut être appelée sur un mot révélé");
        lettresDecouvertes.add(lettre);
    }

    public boolean estTrouve() throws MotReveleException {
        if (motRevele)
            throw new MotReveleException("la méthode estTrouve ne peut être appelée sur un mot révélé");
        return lettresDecouvertes.containsAll(lettresDuMot);
    }

    public String revelerMot() { // l'appel de revelerMot interdit toute utilisation ultérieure des méthodes contient decouvre et estTrouve
        motRevele = true;
        return motCache;
    }

    static class Factory {
        public  Mot createMot(Dictionnary dico) {
            String mot = dico.unMotAuHazard();
            List<Character> lettres = new ArrayList<Character>();
            for (int i = 0; i < mot.length(); i++) {
                if (!lettres.contains(mot.charAt(i))) {
                    lettres.add(mot.charAt(i));
                }
            }
            return new Mot(mot, lettres);
        }
    }
}
