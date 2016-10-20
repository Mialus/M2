package Pendu;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.io.*;
import java.util.Random;

public class DictionnaryUnitTest {

    @Test
    public void testInitialise() {
        InputStream file = new ByteArrayInputStream("toto\ntiti\n".getBytes());
        Random random = Mockito.mock(Random.class);
        Mockito.when(random.nextInt(2)).thenReturn(1, 0);
        Dictionnary dictionnary = new Dictionnary(file, random);
        Assert.assertEquals("titi", dictionnary.unMotAuHazard());
        Assert.assertEquals("toto", dictionnary.unMotAuHazard());

    }
}
