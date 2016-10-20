package haricots;

import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 01/11/13
 * Time: 15:48
 * To change this template use File | Settings | File Templates.
 */
public class ChampUnitTest {


    @Test
    public void testIsEmpty() {
        Champ champ = new Champ();
        Assert.assertTrue(champ.isEmpty());
    }

    @Test
    public void testIsEmptyPlanter() throws Exception{
        Champ champ = new Champ();
        Haricot haricot = Mockito.mock(Haricot.class);
        champ.planter(haricot);
        Assert.assertFalse(champ.isEmpty());
    }

    @Test
    public void testPlanter() throws Exception{
        Champ champ = new Champ();
        Haricot haricot = Mockito.mock(Haricot.class);
        Mockito.when(haricot.getVariete()).thenReturn(Variete.HARRY_CAUCHEMAR);
        champ.planter(haricot);
        Assert.assertEquals(Variete.HARRY_CAUCHEMAR, champ.getVarieteHaricot());
        Assert.assertEquals(1, champ.getNbHaricots());
    }

    @Test (expected = PlantationInterditeException.class)
    public void testPlanterInterdit() throws Exception {
        Champ champ = new Champ();
        Haricot haricot = Mockito.mock(Haricot.class);
        Mockito.when(haricot.getVariete()).thenReturn(Variete.HARRY_CAUCHEMAR);
        champ.planter(haricot);
        Haricot haricot1 = Mockito.mock(Haricot.class);
        Mockito.when(haricot1.getVariete()).thenReturn(Variete.HARRY_COCHON);
        champ.planter(haricot1);
    }
    @Test
    public void testGetVarieteChamp() {
        Champ champ = new Champ();

    }
}
