package de.ironjan.mensaupb.stw.filters;

import junit.framework.Assert;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(RobolectricTestRunner.class)
public class AwkwardTranslationCleanerTest {
    public static final String BAD_TRANSLATION = "Default Menu";
    public static final String OTHER_TRANSLATION = "Other";
    public static final String BETTER_TRANSLATION = "Recommendation";

    AwkwardTranslationCleaner awkwardTranslationFilter = new AwkwardTranslationCleaner();
    StwMenu menu = new StwMenu();

    @Test
    public void test_IsAlreadyBetterTranslation() throws Exception {
        menu.setCategory_en(BETTER_TRANSLATION);
        Assert.assertEquals(BETTER_TRANSLATION, awkwardTranslationFilter.clean(menu).getCategory_en());
    }

    @Test
    public void test_IsOtherTranslation() throws Exception {
        menu.setCategory_en(OTHER_TRANSLATION);
        Assert.assertEquals(OTHER_TRANSLATION, awkwardTranslationFilter.clean(menu).getCategory_en());
    }

    @Test
    public void test_BadTranslation() throws Exception {
        menu.setCategory_en(BAD_TRANSLATION);
        Assert.assertEquals(BETTER_TRANSLATION, awkwardTranslationFilter.clean(menu).getCategory_en());
    }


}