import org.junit.*;
import org.junit.runner.*;
import org.robolectric.*;
import org.robolectric.annotation.*;

import static org.junit.Assert.*;


@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class SanityTest {

	@Test
	public void sanity(){
		int i=1;
		assertEquals("insanity!", 2, i+1);
	}

}
