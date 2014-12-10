package de.ironjan.mensaupb.library.stw;

/**
 * Created by ljan on 21.11.14.
 */
public class SortOrder {

    public static final String DISH_DEFAULT = "dish-default";
    public static final String SOUPS = "soups";
    public static final String SIDEDISH = "sidedish";
    public static final String DESSERT = "dessert";
    public static final String DESSERT_COUNTER = "dessert-counter";
    public static final String DISH_GRILL = "dish-grill";
    public static final String DISH_PASTA = "dish-pasta";
    public static final String DISH_WOK = "dish-wok";

    public static final String GC_ABENDMENSA = "happydinner";
    public static final String GC_CLASSICS_EVENING = "classics-evening";
    public static final String GC_SNACKS = "snacks";


    public static final int SORT_MAIN_DISH = 0;
    public static final int SORT_SOUP = 10;
    public static final int SORT_PASTA = 20;
    public static final int SORT_GRILL = 25;
    public static final int SORT_WOK = 30;
    public static final int SORT_SIDE_DISH = 35;
    public static final int SORT_DESSERT = 70;
    public static final int SORT_EXPENSIVE_DESSERT = 75;
    public static final int SORT_REST = 100;
    public static final int SORT_GC_ABENDMENSA = 5;
    public static final int SORT_GC_CLASSICS_EVENING = 35;
    public static final int SORT_GC_SNACKS = 50;


    static int getSortOrder(String name_de, String categoryIdentifier) {
        if (categoryIdentifier.equals(DISH_DEFAULT)) {
            return SORT_MAIN_DISH;
        } else if (categoryIdentifier.equals(SOUPS)) {
            return SORT_SOUP;
        } else if (categoryIdentifier.equals(DISH_GRILL)) {
            return SORT_GRILL;
        } else if (categoryIdentifier.equals(DISH_PASTA)) {
            return SORT_PASTA;
        } else if (categoryIdentifier.equals(DISH_WOK)) {
            return SORT_WOK;
        } else if (categoryIdentifier.equals(SIDEDISH)) {
            return SORT_SIDE_DISH;
        } else if (categoryIdentifier.equals(DESSERT)) {
            return SORT_DESSERT;
        } else if (categoryIdentifier.equals(DESSERT_COUNTER)) {
            return SORT_EXPENSIVE_DESSERT;
        } else if (categoryIdentifier.equals(GC_ABENDMENSA)) {
            return SORT_GC_ABENDMENSA;
        } else if (categoryIdentifier.equals(GC_CLASSICS_EVENING)) {
            return SORT_GC_CLASSICS_EVENING;
        } else if (categoryIdentifier.equals(GC_SNACKS)) {
            return SORT_GC_SNACKS;
        } else {
            return returnPastaOrArbitrary(name_de);
        }
    }

    private static int returnPastaOrArbitrary(String name_de) {
        if ("Pasta-Variation \"was auf den Teller passt\"".equals(name_de)) {
            return SORT_PASTA;
        }
        return SORT_REST;
    }

}
