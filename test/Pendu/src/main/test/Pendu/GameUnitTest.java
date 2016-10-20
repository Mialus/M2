package Pendu;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.Scanner;

public class GameUnitTest {


    @Test (expected = MotReveleException.class)
    public void testPlayWithCheat() throws MotReveleException {
        Joueur joueur = Mockito.mock(Joueur.class);
        Mot mot = Mockito.mock(Mot.class);
        Mockito.when(mot.estTrouve()).thenReturn(false);
        Mockito.when(mot.contient(Mockito.anyChar())).thenThrow(new MotReveleException("cheater"));
        Game game = new Game(joueur, mot);
        game.play();
    }

    @Test
    public void testPlayFinSurJoueurMort() throws MotReveleException {
        Joueur joueur = Mockito.mock(Joueur.class);
        Mot mot = Mockito.mock(Mot.class);
        Mockito.when(mot.estTrouve()).thenReturn(false);
        Mockito.when(mot.contient(Mockito.anyChar())).thenReturn(false);
        Mockito.when(joueur.estMort()).thenReturn(false, false, false, true);
        Game game = new Game(joueur, mot);
        game.play();
        Mockito.verify(mot, Mockito.never()).decouvre(Mockito.anyChar());
        Mockito.verify(mot).revelerMot();
        Mockito.verify(joueur,Mockito.times(3)).perdVie();
    }

    @Test
    public void testPlayFinSurMotTrouve() throws MotReveleException {
        Joueur joueur = Mockito.mock(Joueur.class);
        Mot mot = Mockito.mock(Mot.class);
        Mockito.when(mot.estTrouve()).thenReturn(false, false, false, false, true); // les appels successifs de la méthode estTrouve retournerons false pour les 4 premiers et true pour tous les autres

        Mockito.when(mot.contient('t')).thenReturn(true);
        Mockito.when(mot.contient('e')).thenReturn(true);
        Mockito.when(mot.contient('s')).thenReturn(true); // le comportement sur les caractères t e s sont définis, les autres retournent faux


        Mockito.when(joueur.estMort()).thenReturn(false);
        Mockito.when(joueur.proposeLettre(Mockito.any(Scanner.class))).thenReturn('t', 'r', 'e', 's');
        Game game = new Game(joueur, mot);
        game.play();
        Mockito.verify(mot, Mockito.times(3)).decouvre(Mockito.anyChar());
        Mockito.verify(mot, Mockito.never()).revelerMot();
        Mockito.verify(joueur,Mockito.times(1)).perdVie();
    }


}
