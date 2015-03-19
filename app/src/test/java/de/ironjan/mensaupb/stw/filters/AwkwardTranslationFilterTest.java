package de.ironjan.mensaupb.stw.filters;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ironjan.mensaupb.stw.RawMenu;

@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class AwkwardTranslationFilterTest {
    public static final String BAD_TRANSLATION = "Default Menu";
    public static final String OTHER_TRANSLATION = "Other";
    public static final String BETTER_TRANSLATION = "Recommendation";

    AwkwardTranslationFilter awkwardTranslationFilter = new AwkwardTranslationFilter();
    RawMenu menu = new RawMenu();

    @Test
    public void test_IsAlreadyBetterTranslation() throws Exception {
        menu.setCategory_en(BETTER_TRANSLATION);
        Assert.assertEquals(BETTER_TRANSLATION, awkwardTranslationFilter.filter(menu).getCategory_en());
    }

    @Test
    public void test_IsOtherTranslation() throws Exception {
        menu.setCategory_en(OTHER_TRANSLATION);
        Assert.assertEquals(OTHER_TRANSLATION, awkwardTranslationFilter.filter(menu).getCategory_en());
    }

    @Test
    public void test_BadTranslation() throws Exception {
        menu.setCategory_en(BAD_TRANSLATION);
        Assert.assertEquals(BETTER_TRANSLATION, awkwardTranslationFilter.filter(menu).getCategory_en());
    }


}