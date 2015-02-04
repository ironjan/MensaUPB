package de.ironjan.mensaupb.stw.filters;

import junit.framework.*;

import de.ironjan.mensaupb.stw.*;

public class NameFilterTest extends TestCase {
    private final String NO_CLEAN_NECESSARY = "NO-CLEAN-NECESSARY";
    private final String CLEAN_NECESSARY_1_VERT = "CLEAN1|NECESSARY-1-VERT";
    private final String CLEAN_NECESSARY_2_VERT = "CLEAN2|NECESSARY|2-VERT";
    private final String CLEAN_NECESSARY_3_VERT = "CLEAN3|NECESSARY|3|VERT";
    private final String BREAK_FAST_BURGER = "Breakfast Burger |Sesambrötchen |Spiegelei |Speck |Basic Hot";
    private final String CLEANED_1_VERT = "CLEAN1";
    private final String CLEANED_2_VERT = "CLEAN2";
    private final String CLEANED_3_VERT = "CLEAN3";
    private final String CLEANED_BREAK_FAST_BURGER = "Breakfast Burger";

    private final NameFilter nameFilter = new NameFilter();
    private final RawMenu menu = new RawMenu();

    public void test_deNoClean() throws Exception {
        menu.setName_de(NO_CLEAN_NECESSARY);
        assertEquals(NO_CLEAN_NECESSARY, nameFilter.filter(menu).getName_de());
    }

    public void test_deClean1Vert() throws Exception {
        menu.setName_de(CLEAN_NECESSARY_1_VERT);
        assertEquals(CLEANED_1_VERT, nameFilter.filter(menu).getName_de());
    }

    public void test_deClean2Vert() throws Exception {
        menu.setName_de(CLEAN_NECESSARY_2_VERT);
        assertEquals(CLEANED_2_VERT, nameFilter.filter(menu).getName_de());
    }

    public void test_deClean3Vert() throws Exception {
        menu.setName_de(CLEAN_NECESSARY_3_VERT);
        assertEquals(CLEANED_3_VERT, nameFilter.filter(menu).getName_de());
    }

    public void testSetBreakfastBurger() throws Exception {
        menu.setName_de(BREAK_FAST_BURGER);
        assertEquals(CLEANED_BREAK_FAST_BURGER, nameFilter.filter(menu).getName_de());
    }

    public void test_enNoClean() throws Exception {
        menu.setName_en(NO_CLEAN_NECESSARY);
        assertEquals(NO_CLEAN_NECESSARY, nameFilter.filter(menu).getName_en());
    }

    public void test_enClean1Vert() throws Exception {
        menu.setName_en(CLEAN_NECESSARY_1_VERT);
        assertEquals(CLEANED_1_VERT, nameFilter.filter(menu).getName_en());
    }

    public void test_enClean2Vert() throws Exception {
        menu.setName_en(CLEAN_NECESSARY_2_VERT);
        assertEquals(CLEANED_2_VERT, nameFilter.filter(menu).getName_en());
    }

    public void test_enClean3Vert() throws Exception {
        menu.setName_en(CLEAN_NECESSARY_3_VERT);
        assertEquals(CLEANED_3_VERT, nameFilter.filter(menu).getName_en());
    }

}