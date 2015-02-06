package de.ironjan.mensaupb.stw.filters;

import junit.framework.*;

import de.ironjan.mensaupb.stw.*;

public class AwkwardTranslationFilterTest extends TestCase {
    public static final String BAD_TRANSLATION = "Default Menu";
    public static final String OTHER_TRANSLATION = "Other";
    public static final String BETTER_TRANSLATION = "Recommendation";

    AwkwardTranslationFilter awkwardTranslationFilter = new AwkwardTranslationFilter();
    RawMenu menu = new RawMenu();

    public void test_IsAlreadyBetterTranslation() throws Exception {
        menu.setCategory_en(BETTER_TRANSLATION);
        assertEquals(BETTER_TRANSLATION, awkwardTranslationFilter.filter(menu).getCategory_en());
    }

    public void test_IsOtherTranslation() throws Exception {
        menu.setCategory_en(OTHER_TRANSLATION);
        assertEquals(OTHER_TRANSLATION, awkwardTranslationFilter.filter(menu).getCategory_en());
    }

    public void test_BadTranslation() throws Exception {
        menu.setCategory_en(BAD_TRANSLATION);
        assertEquals(BETTER_TRANSLATION, awkwardTranslationFilter.filter(menu).getCategory_en());
    }


}