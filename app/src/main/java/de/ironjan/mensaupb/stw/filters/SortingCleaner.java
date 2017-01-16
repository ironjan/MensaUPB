package de.ironjan.mensaupb.stw.filters;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Applies sort order values for menus.
 */
public class SortingCleaner implements Cleaner {

    public static final String DISH_DEFAULT = "dish-default";
    public static final String SOUPS = "soups";
    public static final String SIDEDISH = "sidedish";
    public static final String DESSERT = "dessert";
    public static final String DESSERT_COUNTER = "dessert-counter";
    public static final String DISH_GRILL = "dish-grill";
    public static final String DISH_PASTA = "dish-pasta";
    public static final String DISH_WOK = "dish-wok";

    private static final String GC_ABENDMENSA = "happydinner";
    private static final String GC_CLASSICS_EVENING = "classics-evening";
    private static final String GC_SNACKS = "snacks";


    private static final int SORT_MAIN_DISH = 0;
    private static final int SORT_SOUP = 10;
    private static final int SORT_PASTA = 20;
    private static final int SORT_GRILL = 25;
    private static final int SORT_WOK = 30;
    private static final int SORT_SIDE_DISH = 35;
    private static final int SORT_DESSERT = 70;
    private static final int SORT_EXPENSIVE_DESSERT = 75;
    private static final int SORT_REST = 100;
    private static final int SORT_GC_ABENDMENSA = 5;
    private static final int SORT_GC_CLASSICS_EVENING = 35;
    private static final int SORT_GC_SNACKS = 50;
    public static final String PASTA_NAME_DE = "Pasta-Variation \"was auf den Teller passt\"";

    static int getSortOrder(StwMenu menu) {
        final String name_de = menu.getName_de();
        final String categoryIdentifier = menu.getCategoryIdentifier();

        switch (categoryIdentifier) {
            case DISH_DEFAULT:
                return SORT_MAIN_DISH;
            case SOUPS:
                return SORT_SOUP;
            case DISH_GRILL:
                return SORT_GRILL;
            case DISH_PASTA:
                return SORT_PASTA;
            case DISH_WOK:
                return SORT_WOK;
            case SIDEDISH:
                return SORT_SIDE_DISH;
            case DESSERT:
                return SORT_DESSERT;
            case DESSERT_COUNTER:
                return SORT_EXPENSIVE_DESSERT;
            case GC_ABENDMENSA:
                return SORT_GC_ABENDMENSA;
            case GC_CLASSICS_EVENING:
                return SORT_GC_CLASSICS_EVENING;
            case GC_SNACKS:
                return SORT_GC_SNACKS;
            default:
                return PASTA_NAME_DE.equals(name_de)
                        ? SORT_PASTA :
                        SORT_REST;
        }
    }

    @Override
    public StwMenu clean(StwMenu menu) {
        StwMenu copy = menu.copy();

        copy.setSortOrder(getSortOrder(menu));

        return copy;
    }
}
