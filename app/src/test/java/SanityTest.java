import junit.framework.Assert;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

/**
 * Just a working test.
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(RobolectricTestRunner.class)
public class SanityTest {
    @Test
    public void testAddition() {
        final int a = 1;
        int b = 1;
        Assert.assertEquals("Addition 1+1 != 2", 2, a + b);
    }
}