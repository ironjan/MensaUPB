package de.ironjan.mensaupb.stw.filters;

import junit.framework.Assert;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Test for CategoryCleaner which replaces "category" with "subcategory" if the latter is non-empty.
 *
 * @see CategoryCleaner
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(RobolectricTestRunner.class)
public class CategoryCleanerTest {
    private static final String EMPTY = "";
    private static final String CATEGORY = "CATEGORY";
    private static final String SUB_CATEGORY = "SUB_CATEGORY";

    CategoryCleaner categoryFilter = new CategoryCleaner();

    @org.junit.Test
    public void test_deFilterEmptySub() throws Exception {
        StwMenu menu = new StwMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(EMPTY);
        StwMenu filtered = categoryFilter.clean(menu);
        Assert.assertEquals(CATEGORY, filtered.getCategory_de());
    }

    @Test
    public void test_deFilterNullSub() throws Exception {
        StwMenu menu = new StwMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(null);
        StwMenu filtered = categoryFilter.clean(menu);
        Assert.assertEquals(CATEGORY, filtered.getCategory_de());
    }

    @Test
    public void test_deFilterNonEmptySub() throws Exception {
        StwMenu menu = new StwMenu();
        menu.setCategory_de(CATEGORY);
        menu.setSubcategory_de(SUB_CATEGORY);
        StwMenu filtered = categoryFilter.clean(menu);
        Assert.assertEquals(SUB_CATEGORY, filtered.getCategory_de());
    }

    @Test
    public void test_enFilterEmptySub() throws Exception {
        StwMenu menu = new StwMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(EMPTY);
        StwMenu filtered = categoryFilter.clean(menu);
        Assert.assertEquals(CATEGORY, filtered.getCategory_en());
    }

    @Test
    public void test_enFilterNullSub() throws Exception {
        StwMenu menu = new StwMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(null);
        StwMenu filtered = categoryFilter.clean(menu);
        Assert.assertEquals(CATEGORY, filtered.getCategory_en());
    }

    @Test
    public void test_enFilterNonEmptySub() throws Exception {
        StwMenu menu = new StwMenu();
        menu.setCategory_en(CATEGORY);
        menu.setSubcategory_en(SUB_CATEGORY);
        StwMenu filtered = categoryFilter.clean(menu);
        Assert.assertEquals(SUB_CATEGORY, filtered.getCategory_en());
    }


}