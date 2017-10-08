package de.ironjan.mensaupb.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.view.MenuItem;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.android.controller.ActivityController;
import org.robolectric.annotation.Config;
import org.robolectric.fakes.RoboMenuItem;


import de.ironjan.mensaupb.BuildConfig;
import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.app_info.About_;
import de.ironjan.mensaupb.menus_ui.Menus;
import de.ironjan.mensaupb.menus_ui.Menus_;

import static org.robolectric.Shadows.shadowOf;

/**
 * Test for Menus class.
 *
 * @see Menus
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 21)
public class MenusActivityTest {
    private Activity mActivity;

    @Before
    public void setupActivity() {
        ActivityController controller = Robolectric.buildActivity(Menus_.class).create().start().resume();
        mActivity = (Activity) controller.get();
        Assert.assertNotNull(mActivity);
    }

    @Test
    public void test_ClickOnAbAboutOpensAbout() {
        MenuItem item = new RoboMenuItem(R.id.ab_about);
        mActivity.onOptionsItemSelected(item);
        Intent expectedIntent = new Intent(mActivity, About_.class);
        Intent startedActivity = shadowOf(mActivity).getNextStartedActivity();
        Assert.assertEquals(expectedIntent, startedActivity);
    }

}
