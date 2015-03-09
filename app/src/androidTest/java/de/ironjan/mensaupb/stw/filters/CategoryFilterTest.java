package de.ironjan.mensaupb.stw.filters;

import junit.framework.*;

import de.ironjan.mensaupb.stw.*;

/**
 * Test for CategoryFilter which replaces "category" with "subcategory" if the latter is non-empty.
 *
 * @see de.ironjan.mensaupb.stw.filters.CategoryFilter
 */
public class CategoryFilterTest extends TestCase {
    private static final String EMPTY = "";
    private static final String CATEGORY = "CATEGORY";
    private static final String SUB_CATEGORY = "SUB_CATEGORY";

    CategoryFilter categoryFilter = new CategoryFilter();

    public void test_deFilterEmptySub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(EMPTY);
        RawMenu filtered = categoryFilter.filter(menu);
        assertEquals(CATEGORY, filtered.getCategory_de());
    }

    public void test_deFilterNullSub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(null);
        RawMenu filtered = categoryFilter.filter(menu);
        assertEquals(CATEGORY, filtered.getCategory_de());
    }

    public void test_deFilterNonEmptySub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(SUB_CATEGORY);
        RawMenu filtered = categoryFilter.filter(menu);
        assertEquals(SUB_CATEGORY, filtered.getCategory_de());
    }

    public void test_enFilterEmptySub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(EMPTY);
        RawMenu filtered = categoryFilter.filter(menu);
        assertEquals(CATEGORY, filtered.getCategory_en());
    }

    public void test_enFilterNullSub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(null);
        RawMenu filtered = categoryFilter.filter(menu);
        assertEquals(CATEGORY, filtered.getCategory_en());
    }

    public void test_enFilterNonEmptySub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(SUB_CATEGORY);
        RawMenu filtered = categoryFilter.filter(menu);
        assertEquals(SUB_CATEGORY, filtered.getCategory_en());
    }


}