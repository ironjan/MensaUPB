package de.ironjan.mensaupb.stw.filters;

import junit.framework.Assert;

import android.os.Build;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * TestCase to test the PastavariationCleaner.
 */
@Config(sdk = Build.VERSION_CODES.JELLY_BEAN_MR2)
@RunWith(RobolectricTestRunner.class)
public class PastavariationCleanerTest {
    private static final String CATEGORY_DESSERT = "dessert";
    private static final String CATEGORY_DEFAULT = "dish-default";
    private static final String CATEGORY_DEFAULT_DE_NAME = "Vorschlagsmenü";
    private static final String CATEGORY_DESSERT_DE_NAME = "Dessert (de)";
    private static final String CATEGORY_DEFAULT_EN_NAME = "Recommendation";
    private static final String CATEGORY_DESSERT_EN_NAME = "Dessert (en)";
    private final static String PASTA_VARIATION_NAME = "Pasta-Variation \"was auf den Teller passt\"";
    final String NON_PASTA_NAME = "NON_PASTA_NAME";
    PastavariationCleaner filter = new PastavariationCleaner();

    @org.junit.Test
    public void test_filterNullMenu() throws Exception {
        StwMenu NULL_MENU = null;
        StwMenu filtered = this.filter.clean(NULL_MENU);
        Assert.assertEquals(null, filtered);
    }

    @Test
    public void test_filterNonPastaMenu() throws Exception {
        StwMenu nonPastaMenu = new StwMenu();
        nonPastaMenu.setName_de(NON_PASTA_NAME);
        nonPastaMenu.setCategoryIdentifier(CATEGORY_DESSERT);
        nonPastaMenu.setCategory_de(CATEGORY_DESSERT_DE_NAME);
        nonPastaMenu.setCategory_en(CATEGORY_DESSERT_EN_NAME);

        StwMenu filtered = this.filter.clean(nonPastaMenu);
        Assert.assertEquals(CATEGORY_DESSERT, filtered.getCategoryIdentifier());
        Assert.assertEquals(CATEGORY_DESSERT_DE_NAME, filtered.getCategory_de());
        Assert.assertEquals(CATEGORY_DESSERT_EN_NAME, filtered.getCategory_en());
    }

    @Test
    public void test_filterPastaMenu() throws Exception {
        StwMenu pastaMenu = new StwMenu();
        pastaMenu.setName_de(PASTA_VARIATION_NAME);
        pastaMenu.setCategoryIdentifier(CATEGORY_DESSERT);
        pastaMenu.setCategory_de(CATEGORY_DESSERT_DE_NAME);
        pastaMenu.setCategory_en(CATEGORY_DESSERT_EN_NAME);

        StwMenu filtered = this.filter.clean(pastaMenu);
        Assert.assertEquals(CATEGORY_DEFAULT, filtered.getCategoryIdentifier());
        Assert.assertEquals(CATEGORY_DEFAULT_DE_NAME, filtered.getCategory_de());
        Assert.assertEquals(CATEGORY_DEFAULT_EN_NAME, filtered.getCategory_en());
    }
}
