package de.ironjan.mensaupb.stw.filters;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Cleaner to recategorize Pastavariation in Mensa Academice to receommendation.
 */
public class PastavariationCleaner implements Cleaner {
    private final static String PASTA_VARIATION_STRING = "Pasta-Variation \"was auf den Teller passt\"";
    private static final String CATEGORY_DEFAULT = "dish-default";
    private static final String CATEGORY_DEFAULT_DE_NAME = "Vorschlagsmenü";
    private static final String CATEGORY_DEFAULT_EN_NAME = "Recommendation";

    @Override
    public StwMenu clean(StwMenu menu) {
        if (menu == null) {
            return null;
        }
        final StwMenu copy = menu.copy();
        final String name_de = copy.getName_de();
        if (PASTA_VARIATION_STRING.equals(name_de)) {
            copy.setCategoryIdentifier(CATEGORY_DEFAULT);
            copy.setCategory_de(CATEGORY_DEFAULT_DE_NAME);
            copy.setCategory_en(CATEGORY_DEFAULT_EN_NAME);
        }
        return copy;
    }
}
