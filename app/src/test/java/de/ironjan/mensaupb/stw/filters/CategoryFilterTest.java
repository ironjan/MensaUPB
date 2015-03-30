package de.ironjan.mensaupb.stw.filters;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ironjan.mensaupb.stw.rest_api.RawMenu;

/**
 * Test for CategoryFilter which replaces "category" with "subcategory" if the latter is non-empty.
 *
 * @see de.ironjan.mensaupb.stw.filters.CategoryFilter
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class CategoryFilterTest {
    private static final String EMPTY = "";
    private static final String CATEGORY = "CATEGORY";
    private static final String SUB_CATEGORY = "SUB_CATEGORY";

    CategoryFilter categoryFilter = new CategoryFilter();

    @org.junit.Test
    public void test_deFilterEmptySub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(EMPTY);
        RawMenu filtered = categoryFilter.filter(menu);
        Assert.assertEquals(CATEGORY, filtered.getCategory_de());
    }

    @Test
    public void test_deFilterNullSub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(null);
        RawMenu filtered = categoryFilter.filter(menu);
        Assert.assertEquals(CATEGORY, filtered.getCategory_de());
    }

    @Test
    public void test_deFilterNonEmptySub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(SUB_CATEGORY);
        RawMenu filtered = categoryFilter.filter(menu);
        Assert.assertEquals(SUB_CATEGORY, filtered.getCategory_de());
    }

    @Test
    public void test_enFilterEmptySub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(EMPTY);
        RawMenu filtered = categoryFilter.filter(menu);
        Assert.assertEquals(CATEGORY, filtered.getCategory_en());
    }

    @Test
    public void test_enFilterNullSub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(null);
        RawMenu filtered = categoryFilter.filter(menu);
        Assert.assertEquals(CATEGORY, filtered.getCategory_en());
    }

    @Test
    public void test_enFilterNonEmptySub() throws Exception {
        RawMenu menu = new RawMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(SUB_CATEGORY);
        RawMenu filtered = categoryFilter.filter(menu);
        Assert.assertEquals(SUB_CATEGORY, filtered.getCategory_en());
    }


}