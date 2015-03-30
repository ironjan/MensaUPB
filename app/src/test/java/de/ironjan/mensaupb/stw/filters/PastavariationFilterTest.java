package de.ironjan.mensaupb.stw.filters;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ironjan.mensaupb.stw.rest_api.RawMenu;

/**
 * TestCase to test the PastavariationFilter.
 */
@Config(emulateSdk = 18)
@RunWith(RobolectricTestRunner.class)
public class PastavariationFilterTest {
    private static final String CATEGORY_DESSERT = "dessert";
    private static final String CATEGORY_DEFAULT = "dish-default";
    private static final String CATEGORY_DEFAULT_DE_NAME = "Vorschlagsmenü";
    private static final String CATEGORY_DESSERT_DE_NAME = "Dessert (de)";
    private static final String CATEGORY_DEFAULT_EN_NAME = "Recommendation";
    private static final String CATEGORY_DESSERT_EN_NAME = "Dessert (en)";
    private final static String PASTA_VARIATION_NAME = "Pasta-Variation \"was auf den Teller passt\"";
    final String NON_PASTA_NAME = "NON_PASTA_NAME";
    PastavariationFilter filter = new PastavariationFilter();

    @org.junit.Test
    public void test_filterNullMenu() throws Exception {
        RawMenu NULL_MENU = null;
        RawMenu filtered = this.filter.filter(NULL_MENU);
        Assert.assertEquals(null, filtered);
    }

    @Test
    public void test_filterNonPastaMenu() throws Exception {
        RawMenu nonPastaMenu = new RawMenu();
        nonPastaMenu.setName_de(NON_PASTA_NAME);
        nonPastaMenu.setCategoryIdentifier(CATEGORY_DESSERT);
        nonPastaMenu.setCategory_de(CATEGORY_DESSERT_DE_NAME);
        nonPastaMenu.setCategory_en(CATEGORY_DESSERT_EN_NAME);

        RawMenu filtered = this.filter.filter(nonPastaMenu);
        Assert.assertEquals(CATEGORY_DESSERT, filtered.getCategoryIdentifier());
        Assert.assertEquals(CATEGORY_DESSERT_DE_NAME, filtered.getCategory_de());
        Assert.assertEquals(CATEGORY_DESSERT_EN_NAME, filtered.getCategory_en());
    }

    @Test
    public void test_filterPastaMenu() throws Exception {
        RawMenu pastaMenu = new RawMenu();
        pastaMenu.setName_de(PASTA_VARIATION_NAME);
        pastaMenu.setCategoryIdentifier(CATEGORY_DESSERT);
        pastaMenu.setCategory_de(CATEGORY_DESSERT_DE_NAME);
        pastaMenu.setCategory_en(CATEGORY_DESSERT_EN_NAME);

        RawMenu filtered = this.filter.filter(pastaMenu);
        Assert.assertEquals(CATEGORY_DEFAULT, filtered.getCategoryIdentifier());
        Assert.assertEquals(CATEGORY_DEFAULT_DE_NAME, filtered.getCategory_de());
        Assert.assertEquals(CATEGORY_DEFAULT_EN_NAME, filtered.getCategory_en());
    }
}
