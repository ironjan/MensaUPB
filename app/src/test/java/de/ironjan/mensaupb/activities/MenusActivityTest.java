package de.ironjan.mensaupb.activities;

import android.app.Activity;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

/**
 * Test for Menus class.
 *
 * @see Menus
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class MenusActivityTest {
    private Activity mActivity;

    @Before
    public void setupActivity() {
        ActivityController controller = Robolectric.buildActivity(Menus_.class).create().start().resume();
        mActivity = (Activity) controller.get();
        Assert.assertNotNull(mActivity);
    }

    @Test
    public void test_ShowsEitherLoadingListOrNoFood() {
        Assert.fail("Not implemented yet.");
    }

    @Test
    public void test_ClickOnAbSettingsOpensSettings() {
// fixme implement test when TestMenuItem could be found
//        MenuItem item = new TestMenuItem(R.id.ab_settings);
//        mActivity.onOptionsItemSelected(item);
//        Intent expectedIntent = new Intent(mActivity, Settings_.class);
//        Intent startedActivity = shadowOf(mActivity).getNextStartedActivity();
//        Assert.assertEquals(expectedIntent, startedActivity);
    }

    @Test
    public void test_ClickOnAbAboutOpensAbout() {
        // fixme implement test when TestMenuItem could be found
//        MenuItem item = new TestMenuItem(R.id.ab_about).performClick();
//        mActivity.onOptionsItemSelected(item);
//        Intent expectedIntent = new Intent(mActivity, About_.class);
//        Intent startedActivity = shadowOf(mActivity).getNextStartedActivity();
//        Assert.assertEquals(expectedIntent, startedActivity);
    }

}
