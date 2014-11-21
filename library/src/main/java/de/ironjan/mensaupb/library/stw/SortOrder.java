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

    static int getSortOrder(String categoryIdentifier) {
        if (categoryIdentifier.equals(DISH_DEFAULT)) {
            return 0;
        } else if (categoryIdentifier.equals(SOUPS)) {
            return 10;
        } else if (categoryIdentifier.equals(DISH_GRILL)) {
            return 25;
        } else if (categoryIdentifier.equals(DISH_PASTA)) {
            return 20;
        } else if (categoryIdentifier.equals(DISH_WOK)) {
            return 30;
        } else if (categoryIdentifier.equals(SIDEDISH)) {
            return 30;
        } else if (categoryIdentifier.equals(DESSERT)) {
            return 35;
        } else if (categoryIdentifier.equals(DESSERT_COUNTER)) {
            return 37;
        } else {
            return 100;
        }
    }
}
