package haricots;

import junit.framework.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created with IntelliJ IDEA.
 * User: fabrice
 * Date: 20/10/13
 * Time: 23:27
 * To change this template use File | Settings | File Templates.
 */
public class MainUnitTest {

    @Test
    public void testMainVide() {
        Main main = new Main(Collections.EMPTY_LIST);
        Assert.assertTrue(main.isEmpty());
    }

    @Test
    public void testRetirePremiereCarte() {
        Haricot haricot1 = Mockito.mock(Haricot.class);
        Main main = new Main(new ArrayList<Haricot>(Arrays.asList(haricot1)));
        Assert.assertEquals(haricot1, main.retirePremiereCarte());
        Assert.assertTrue(main.isEmpty());
    }
}
