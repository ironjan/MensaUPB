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
public class NameCleanerTest {
    private final String NO_CLEAN_NECESSARY = "NO-CLEAN-NECESSARY";
    private final String CLEAN_NECESSARY_1_VERT = "CLEAN1|NECESSARY-1-VERT";
    private final String CLEAN_NECESSARY_2_VERT = "CLEAN2|NECESSARY|2-VERT";
    private final String CLEAN_NECESSARY_3_VERT = "CLEAN3|NECESSARY|3|VERT";
    private final String BREAK_FAST_BURGER = "Breakfast Burger |Sesambrötchen |Spiegelei |Speck |Basic Hot";
    private final String CLEANED_1_VERT = "CLEAN1";
    private final String CLEANED_2_VERT = "CLEAN2";
    private final String CLEANED_3_VERT = "CLEAN3";
    private final String CLEANED_BREAK_FAST_BURGER = "Breakfast Burger";

    private final NameCleaner nameFilter = new NameCleaner();
    private final StwMenu menu = new StwMenu();

    @org.junit.Test
    public void test_deNoClean() throws Exception {
        menu.setName_de(NO_CLEAN_NECESSARY);
        Assert.assertEquals(NO_CLEAN_NECESSARY, nameFilter.clean(menu).getName_de());
    }

    @Test
    public void test_deClean1Vert() throws Exception {
        menu.setName_de(CLEAN_NECESSARY_1_VERT);
        Assert.assertEquals(CLEANED_1_VERT, nameFilter.clean(menu).getName_de());
    }

    @Test
    public void test_deClean2Vert() throws Exception {
        menu.setName_de(CLEAN_NECESSARY_2_VERT);
        Assert.assertEquals(CLEANED_2_VERT, nameFilter.clean(menu).getName_de());
    }

    @Test
    public void test_deClean3Vert() throws Exception {
        menu.setName_de(CLEAN_NECESSARY_3_VERT);
        Assert.assertEquals(CLEANED_3_VERT, nameFilter.clean(menu).getName_de());
    }

    @Test
    public void testSetBreakfastBurger() throws Exception {
        menu.setName_de(BREAK_FAST_BURGER);
        Assert.assertEquals(CLEANED_BREAK_FAST_BURGER, nameFilter.clean(menu).getName_de());
    }

    @Test
    public void test_enNoClean() throws Exception {
        menu.setName_en(NO_CLEAN_NECESSARY);
        Assert.assertEquals(NO_CLEAN_NECESSARY, nameFilter.clean(menu).getName_en());
    }

    @Test
    public void test_enClean1Vert() throws Exception {
        menu.setName_en(CLEAN_NECESSARY_1_VERT);
        Assert.assertEquals(CLEANED_1_VERT, nameFilter.clean(menu).getName_en());
    }

    @Test
    public void test_enClean2Vert() throws Exception {
        menu.setName_en(CLEAN_NECESSARY_2_VERT);
        Assert.assertEquals(CLEANED_2_VERT, nameFilter.clean(menu).getName_en());
    }

    @Test
    public void test_enClean3Vert() throws Exception {
        menu.setName_en(CLEAN_NECESSARY_3_VERT);
        Assert.assertEquals(CLEANED_3_VERT, nameFilter.clean(menu).getName_en());
    }

}