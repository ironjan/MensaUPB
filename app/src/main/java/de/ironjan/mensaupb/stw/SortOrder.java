package de.ironjan.mensaupb.stw;

/**
 * Class to get the sort order of a Menu.
 * <p/>
 * TODO extract into filter
 */
class SortOrder {

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


    static int getSortOrder(String name_de, String categoryIdentifier) {
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
