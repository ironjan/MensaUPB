package de.najidev.mensaupb.stw;

import org.slf4j.*;

import java.util.*;

import de.najidev.mensaupb.BuildConfig;

/**
 * Created by ljan on 31.01.14.
 */
public class StwCategoryParser {
    private static Hashtable<String, String> categoryMapping = new Hashtable<String, String>();
    private static HashMap<String, Integer> sortMapping = new HashMap<String, Integer>();


    public static final String CATEGORY_ESSEN = "Essen";

    private static final String CATEGORY_GRILL = "Grill";

    public static final String CATEGORY_PASTA = "Pasta";

    public static final String CATEGORY_SALAD_BUFFET = "Salatbuffet";

    public static final String CATEGORY_WOK = "Wok";

    public static final String CATEGORY_EINTOPF = "Eintopf";

    public static final String CATEGORY_SOUP = "Suppe";

    public static final int SORT_MAIN_DISHES = 0,
            SORT_SOUP = 5,
            SORT_HK = 20,
            SORT_GRILL = 25,
            SORT_SALAD = 30,
            SORT_DESSERT = 32,
            SORT_BUFFET = 35,
            SORT_DISH_EXPENSIVE = 40,
            SORT_SMALL_SOUP = 60,
            SORT_DESSERT_EXPENSIVE = 90;

    static {
        categoryMapping.put("PUB Beilage Waage", "Beilage");
        sortMapping.put("PUB Beilage Waage", SORT_SALAD);
        categoryMapping.put("PUB Salatbeilage Waage", "Beilage");
        sortMapping.put("PUB Salatbeilage Waage", SORT_SALAD);
        categoryMapping.put("Stamm Beilage klein 0,50€", "Beilage");
        sortMapping.put("Stamm Beilage klein 0,50€", SORT_SALAD);
        categoryMapping.put("Stamm Beilagensalat 0,50€", "Beilage");
        sortMapping.put("Stamm Beilagensalat 0,50€", SORT_SALAD);
        categoryMapping.put("Stamm Gemüsebeil. 1 0,50€", "Beilage");
        sortMapping.put("Stamm Gemüsebeil. 1 0,50€", SORT_SALAD);
        categoryMapping.put("Stamm Sättigungbeil 0,50€", "Beilage");
        sortMapping.put("Stamm Sättigungbeil 0,50€", SORT_SALAD);

        categoryMapping.put("Aktionsdessert 1,20€", "Dessert");
        sortMapping.put("Aktionsdessert 1,20€", SORT_DESSERT_EXPENSIVE);
        categoryMapping.put("PUB Dessert", "Dessert");
        sortMapping.put("PUB Dessert", SORT_DESSERT);
        categoryMapping.put("Stamm Dessert 0,55€", "Dessert");
        sortMapping.put("Stamm Dessert", SORT_DESSERT);

        categoryMapping.put("Aktionsessen", CATEGORY_ESSEN);
        sortMapping.put("Aktionsessen", SORT_DISH_EXPENSIVE);

        categoryMapping.put("Grill Fisch", CATEGORY_GRILL);
        sortMapping.put("Grill Fisch", SORT_GRILL);
        categoryMapping.put("Grill Fleisch", CATEGORY_GRILL);
        sortMapping.put("Grill Fleisch", SORT_GRILL);

        categoryMapping.put("Pastabuffet", CATEGORY_PASTA);
        sortMapping.put("Pastabuffet", SORT_BUFFET);

        categoryMapping.put("PUB Fladenbrot", CATEGORY_ESSEN);
        sortMapping.put("PUB Fladenbrot", SORT_MAIN_DISHES);

        categoryMapping.put("PUB Hauptkompononte", CATEGORY_ESSEN);
        sortMapping.put("PUB Hauptkompononte", SORT_HK);

        categoryMapping.put("PUB Mittags-Angebot", CATEGORY_ESSEN);
        sortMapping.put("PUB Mittags-Angebot", SORT_MAIN_DISHES);
        categoryMapping.put("Stamm HK Essen 1 1,05€", CATEGORY_ESSEN);
        sortMapping.put("Stamm HK Essen 1 1,05€", SORT_HK);
        categoryMapping.put("Stamm HK Essen 2 1,60€", CATEGORY_ESSEN);
        sortMapping.put("Stamm HK Essen 2 1,60€", SORT_HK);
        categoryMapping.put("Stamm HK Essen 3 2,00€", CATEGORY_ESSEN);
        sortMapping.put("Stamm HK Essen 3 2,00€", SORT_HK);

        categoryMapping.put("Stamm Waage 100g/0,80€", CATEGORY_SALAD_BUFFET);
        sortMapping.put("Stamm Waage 100g/0,80€", SORT_BUFFET);
        categoryMapping.put("WOK-Buffet Mensa", CATEGORY_WOK);
        sortMapping.put("WOK-Buffet Mensa", SORT_BUFFET);

        categoryMapping.put("Stamm Eintopf 1 f 1,80€", CATEGORY_EINTOPF);
        sortMapping.put("Stamm Eintopf 1 f 1,80€", SORT_SOUP);
        categoryMapping.put("Stamm Tagessuppe 0,55€", CATEGORY_SOUP);
        sortMapping.put("Stamm Tagessuppe 0,55€", SORT_SMALL_SOUP);

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StwCategoryParser.class.getSimpleName());

    public static String getCategory(String key) {
        final String category = categoryMapping.get(key);
        if (BuildConfig.DEBUG) LOGGER.debug("{} -> {}", key, category);
        return category;
    }

    public static int getSort(String key) {
        final Integer sort = sortMapping.get(key);
        if (BuildConfig.DEBUG) LOGGER.debug("{} -> {}", key, sort);
        return (sort == null) ? 99 : sort.intValue();
    }
}
