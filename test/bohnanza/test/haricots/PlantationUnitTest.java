package haricots;

import junit.framework.Assert;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PlantationUnitTest {

    @Test
    public void testPlanterHaricotsDansChampsVides() throws AucunChampVideException, PlantationInterditeException {
        // DEFINE
        Champ champ = mock(Champ.class);
        when(champ.isEmpty()).thenReturn(true);
        Haricot haricot = mock(Haricot.class);
        Plantation plantation = new Plantation(Arrays.asList(champ));
        // WHEN
        assertTrue(plantation.planter(haricot));
        // THEN
        verify(champ).planter(haricot);
    }

    @Test
    public void testPlanteHaricotsDansBonChampAucunChampVide() throws AucunChampVideException, PlantationInterditeException {
        // DEFINE
        Champ champ1 = mock(Champ.class);
        when(champ1.isEmpty()).thenReturn(false);
        when(champ1.getVarieteHaricot()).thenReturn(Variete.HARRY_COLT);
        Champ champ2 = mock(Champ.class);
        when(champ2.isEmpty()).thenReturn(false);
        when(champ2.getVarieteHaricot()).thenReturn(Variete.HARRY_CORIACE);
        Haricot haricot = mock(Haricot.class);
        when(haricot.getVariete()).thenReturn(Variete.HARRY_CORIACE);
        Plantation plantation = new Plantation(Arrays.asList(champ1, champ2));
        // WHEN
        assertTrue(plantation.planter(haricot));
        // THEN
        verify(champ2).planter(haricot);
    }

    @Test
    public void testPlanteHaricotsDansBonChampPlanteAvecChampVide() throws AucunChampVideException, PlantationInterditeException {
        // DEFINE
        Champ champ1 = mock(Champ.class);
        when(champ1.isEmpty()).thenReturn(true);
        Champ champ2 = mock(Champ.class);
        when(champ2.isEmpty()).thenReturn(false);
        when(champ2.getVarieteHaricot()).thenReturn(Variete.HARRY_CORIACE);
        Haricot haricot = mock(Haricot.class);
        when(haricot.getVariete()).thenReturn(Variete.HARRY_CORIACE);
        Plantation plantation = new Plantation(Arrays.asList(champ1, champ2));
        // WHEN
        assertTrue(plantation.planter(haricot));
        // THEN
        verify(champ2).planter(haricot);
    }

    @Test
    public void testPlanteHaricotsDansChampVideAvecChampPlante() throws AucunChampVideException, PlantationInterditeException {
        // DEFINE
        Champ champ1 = mock(Champ.class);
        when(champ1.isEmpty()).thenReturn(true);
        Champ champ2 = mock(Champ.class);
        when(champ2.isEmpty()).thenReturn(false);
        when(champ2.getVarieteHaricot()).thenReturn(Variete.HARRY_COLT);
        Haricot haricot = mock(Haricot.class);
        when(haricot.getVariete()).thenReturn(Variete.HARRY_CORIACE);
        Plantation plantation = new Plantation(Arrays.asList(champ1, champ2));
        // WHEN
        assertTrue(plantation.planter(haricot));
        // THEN
        verify(champ1).planter(haricot);
    }

    @Test
    public void testPlanteHaricotsImpossible() throws AucunChampVideException {
        // DEFINE
        Champ champ1 = mock(Champ.class);
        when(champ1.isEmpty()).thenReturn(false);
        when(champ1.getVarieteHaricot()).thenReturn(Variete.HARRY_COLT);
        Champ champ2 = mock(Champ.class);
        when(champ2.isEmpty()).thenReturn(false);
        when(champ2.getVarieteHaricot()).thenReturn(Variete.HARRY_COLT);
        Haricot haricot = mock(Haricot.class);
        when(haricot.getVariete()).thenReturn(Variete.HARRY_CORIACE);
        Plantation plantation = new Plantation(Arrays.asList(champ1, champ2));
        // WHEN
        assertFalse(plantation.planter(haricot));
    }

    @Test
    public void testRecolteChamp() {
        Champ champ1 = mock(Champ.class);
        when(champ1.isEmpty()).thenReturn(false);
        when(champ1.getVarieteHaricot()).thenReturn(Variete.HARRY_COLT);
        when(champ1.getNbHaricots()).thenReturn(6);
        when(champ1.getHaricotMetre()).thenReturn(Variete.HARRY_COLT.getHaricometre());
        Plantation plantation = new Plantation(Arrays.asList(champ1));
        Assert.assertEquals(2, plantation.recolte(champ1));
    }


    @Test
    public void testRecolteChampPasAssezPlante() {
        Champ champ1 = mock(Champ.class);
        when(champ1.isEmpty()).thenReturn(false);
        when(champ1.getVarieteHaricot()).thenReturn(Variete.HARRY_COLT);
        when(champ1.getNbHaricots()).thenReturn(3);
        when(champ1.getHaricotMetre()).thenReturn(Variete.HARRY_COLT.getHaricometre());
        Plantation plantation = new Plantation(Arrays.asList(champ1));
        Assert.assertEquals(0, plantation.recolte(champ1));
    }

    @Test
    public void testRecolteChampPlein() {
        Champ champ1 = mock(Champ.class);
        when(champ1.isEmpty()).thenReturn(false);
        when(champ1.getVarieteHaricot()).thenReturn(Variete.HARRY_COLT);
        when(champ1.getNbHaricots()).thenReturn(15);
        when(champ1.getHaricotMetre()).thenReturn(Variete.HARRY_COLT.getHaricometre());
        Plantation plantation = new Plantation(Arrays.asList(champ1));
        Assert.assertEquals(4, plantation.recolte(champ1));
    }
}
