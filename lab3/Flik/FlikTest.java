import org.junit.Test;
import static org.junit.Assert.*;

public class FlikTest {
    /** Test if Flik's method works. */
    @Test
    public void testIsSameNumber() {
        boolean actual = Flik.isSameNumber(500, 500);
        assertTrue(actual);
    }

    /** Test if Horrible Steve's complaint makes sense. */
    @Test
    public void testMess() {
        boolean actual = HorribleSteve.mess();
        assertTrue(actual);
    }
}
