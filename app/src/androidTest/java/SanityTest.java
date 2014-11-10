import android.test.suitebuilder.annotation.*;

import junit.framework.*;

/**
 * Just a working test.
 */
public class SanityTest extends TestCase {
    @SmallTest
    public void testAddition() {
        final int a = 1;
        int b = 1;
        assertEquals("Addition 1+1 != 2", 2, a + b);
    }
}
