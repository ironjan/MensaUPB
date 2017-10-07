package de.ironjan.mensaupb.stw.filters;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Applies sort order values for menus.
 */
public class SortingFilter extends FilterBase {


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


    private static final int SORT_DISHES = 0;
    /** When in doubt: we place things in the front */
    private static final int SORT_REST = 0;
    private static final int SORT_SIDE_DISH = 35;
    private static final int SORT_DESSERT = 70;
    private static final int SORT_GC_ABENDMENSA = 5;
    private static final int SORT_GC_CLASSICS_EVENING = 35;
    private static final int SORT_GC_SNACKS = 50;

    static int getSortOrder(final StwMenu menu) {
        switch (menu.getCategoryIdentifier()) {
            case DISH_DEFAULT:
                return SORT_DISHES;
            case SOUPS:
                return SORT_DISHES;
            case DISH_GRILL:
                return SORT_DISHES;
            case DISH_PASTA:
                return SORT_DISHES;
            case DISH_WOK:
                return SORT_DISHES;
            case SIDEDISH:
                return SORT_SIDE_DISH;
            case DESSERT:
                return SORT_DESSERT;
            case DESSERT_COUNTER:
                return SORT_DESSERT;
            case GC_ABENDMENSA:
                return SORT_GC_ABENDMENSA;
            case GC_CLASSICS_EVENING:
                return SORT_GC_CLASSICS_EVENING;
            case GC_SNACKS:
                return SORT_GC_SNACKS;
            default:
                return SORT_REST;
        }
    }

    @Override
    public StwMenu filter(StwMenu menu) {
        int sortOrder = getSortOrder(menu);

        StwMenu copy = menu.copy();
        copy.setSortOrder(sortOrder);
        copy.setCategory_de(getCategory_de(sortOrder));
        copy.setCategory_en(getCategory_en(sortOrder));

        return copy;
    }

    private String getCategory_de(int sortOrder) {
        switch (sortOrder){
            case SORT_SIDE_DISH:
                return "Beilage";
            case SORT_DESSERT:
                return "Dessert";
            default:
                return "Hauptgerichte";
        }
    }
    private String getCategory_en(int sortOrder) {
        switch (sortOrder){
            case SORT_SIDE_DISH:
                return "Side Dish";
            case SORT_DESSERT:
                return "Dessert";
            default:
                return "Main Dishes";
        }
    }
}
