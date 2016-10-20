package Pendu;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

import java.util.Random;

public class MotUnitTest {

    private Mot mot;

    @Before
    public void setUp() {
        Dictionnary dico = Mockito.mock(Dictionnary.class);
        Mockito.when(dico.unMotAuHazard()).thenReturn("mercredi");
        mot = new Mot.Factory().createMot(dico);
    }

    @Test
    public void testReveler() {
        Assert.assertEquals("mercredi", mot.revelerMot());
    }

    @Test
    public void testContient() throws MotReveleException {
        Assert.assertTrue(mot.contient('e'));
        Assert.assertFalse(mot.contient('a'));
    }

    @Test (expected = MotReveleException.class)
    public void testContientApresRevele() throws MotReveleException {
        mot.revelerMot();
        mot.contient('e');
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDecouvreApresRevele() throws MotReveleException {
        mot.revelerMot();
        thrown.expect(MotReveleException.class);
        mot.decouvre('e');
    }

    @Test
    public void testEstTrouveApresRevele() throws MotReveleException {
        mot.revelerMot();
        thrown.expect(MotReveleException.class);
        thrown.expectMessage("la méthode estTrouve ne peut être appelée sur un mot révélé");
        mot.estTrouve();
    }


    @Test
    public void testBuildAffichage() throws MotReveleException {
        Assert.assertEquals("--------\n", mot.buildAffichage().toString());
        mot.decouvre('e');
        Assert.assertEquals("-e---e--\n", mot.buildAffichage().toString());
        mot.decouvre('a');
        Assert.assertEquals("-e---e--\n", mot.buildAffichage().toString());
    }


    @Test
    public void testEstTrouve() throws MotReveleException {
        mot.decouvre('e');
        mot.decouvre('m');
        mot.decouvre('i');
        mot.decouvre('d');
        mot.decouvre('r');
        Assert.assertFalse(mot.estTrouve());
        mot.decouvre('c');
        Assert.assertTrue(mot.estTrouve());
    }
}
