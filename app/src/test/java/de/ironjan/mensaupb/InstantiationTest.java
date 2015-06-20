package de.ironjan.mensaupb;

import android.app.Activity;

import junit.framework.Assert;

import android.os.Build;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import org.robolectric.util.ActivityController;

import de.ironjan.mensaupb.app_info.About_;
import de.ironjan.mensaupb.menus_ui.MenuDetails_;
import de.ironjan.mensaupb.menus_ui.Menus_;
import de.ironjan.mensaupb.prefs.Settings_;

/**
 * Test to make sure that every Activity can be opened
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(RobolectricTestRunner.class)
public class InstantiationTest {
    @Test
    public void test_AboutCanBeOpened(){
        ActivityController controller = Robolectric.buildActivity(About_.class).create().start();
        Activity activity = (Activity) controller.get();
        Assert.assertNotNull(activity);
    }
    @Test
    public void test_MenusCanBeOpened(){
        ActivityController controller = Robolectric.buildActivity(Menus_.class).create().start();
        Activity activity = (Activity) controller.get();
        Assert.assertNotNull(activity);
    }
    @Test
    public void test_MenuDetailsCanBeOpened(){
        // TODO pass arguments for menu
        ActivityController controller = Robolectric.buildActivity(MenuDetails_.class).create().start();
        Activity activity = (Activity) controller.get();
        Assert.assertNotNull(activity);
    }
    @Test
    public void test_MenuSettingsCanBeOpened(){
        ActivityController controller = Robolectric.buildActivity(Settings_.class).create().start();
        Activity activity = (Activity) controller.get();
        Assert.assertNotNull(activity);
    }

}
