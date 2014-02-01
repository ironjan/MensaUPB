package de.najidev.mensaupb.stw;

import org.slf4j.*;

import java.util.*;

import de.najidev.mensaupb.*;

/**
 * Created by ljan on 31.01.14.
 */
public class StwCategoryParser {
    private static Hashtable<String, String> categoryMapping = new Hashtable<String, String>();
private static HashMap<String, Integer> sortMapping = new HashMap<String, Integer>();

    private static final Integer AKTIONS_ESSEN = Integer.valueOf(0);
    private static final Integer HK_ESSEN = Integer.valueOf(10);
    private static final Integer GRILL  = Integer.valueOf(20);
    private static final Integer PASTA  = Integer.valueOf(30);
    private static final Integer DESSERT= Integer.valueOf(40);
    private static final Integer DESSERT_TEUER= Integer.valueOf(45);
    private static final Integer SALADS = Integer.valueOf(50);
    private static final Integer WOK = Integer.valueOf(60);
    private static final Integer SOUP = Integer.valueOf(70);

    static {
        categoryMapping.put("PUB Beilage Waage", "Beilage");
        sortMapping.put("PUB Beilage Waage",SALADS);
        categoryMapping.put("PUB Salatbeilage Waage", "Beilage");
        sortMapping.put("PUB Salatbeilage Waage",SALADS);
        categoryMapping.put("Stamm Beilage klein 0,50€", "Beilage");
        sortMapping.put("Stamm Beilage klein 0,50€",SALADS);
        categoryMapping.put("Stamm Beilagensalat 0,50€", "Beilage");
        sortMapping.put("Stamm Beilagensalat 0,50€",SALADS);
        categoryMapping.put("Stamm Gemüsebeil. 1 0,50€", "Beilage");
        sortMapping.put("Stamm Gemüsebeil. 1 0,50€",SALADS);
        categoryMapping.put("Stamm Sättigungbeil 0,50€", "Beilage");
        sortMapping.put("Stamm Sättigungbeil 0,50€",SALADS);

        categoryMapping.put("Aktionsdessert 1,20€", "Dessert");
        sortMapping.put("Aktionsdessert 1,20€",DESSERT_TEUER);
        categoryMapping.put("PUB Dessert", "Dessert");
        sortMapping.put("PUB Dessert",DESSERT);
        categoryMapping.put("Stamm Dessert 0,55€", "Dessert");
        sortMapping.put("Stamm Dessert",DESSERT);

        categoryMapping.put("Aktionsessen", "Essen");
        sortMapping.put("Aktionsessen", AKTIONS_ESSEN);

        categoryMapping.put("Grill Fisch", "Essen");
        sortMapping.put("Grill Fisch", GRILL);
        categoryMapping.put("Grill Fleisch", "Essen");
        sortMapping.put("Grill Fleisch", GRILL);

        categoryMapping.put("Pastabuffet","Essen");
        sortMapping.put("Pastabuffet", PASTA);

        categoryMapping.put("PUB Fladenbrot","Essen");
        sortMapping.put("PUB Fladenbrot", HK_ESSEN);

        categoryMapping.put("PUB Hauptkompononte","Essen");
        sortMapping.put("PUB Hauptkompononte", HK_ESSEN);

        categoryMapping.put("PUB Mittags-Angebot", "Essen");
        sortMapping.put("PUB Mittags-Angebot", AKTIONS_ESSEN);
        categoryMapping.put("Stamm HK Essen 1 1,05€", "Essen");
        sortMapping.put("Stamm HK Essen 1 1,05€", AKTIONS_ESSEN);
        categoryMapping.put("Stamm HK Essen 2 1,60€", "Essen");
        sortMapping.put("Stamm HK Essen 2 1,60€", AKTIONS_ESSEN);
        categoryMapping.put("Stamm HK Essen 3 2,00€", "Essen");
        sortMapping.put("Stamm HK Essen 3 2,00€", AKTIONS_ESSEN);

        categoryMapping.put("Stamm Waage 100g/0,80€", "Essen");
        sortMapping.put("Stamm Waage 100g/0,80€",WOK);
        categoryMapping.put("WOK-Buffet Mensa", "Essen");
        sortMapping.put("WOK-Buffet Mensa",WOK);

        categoryMapping.put("Stamm Eintopf 1 f 1,80€", "Suppe");
             sortMapping.put("Stamm Eintopf 1 f 1,80€", AKTIONS_ESSEN);
   categoryMapping.put("Stamm Tagessuppe 0,55€", "Suppe");
             sortMapping.put("Stamm Tagessuppe 0,55€", SOUP);

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StwCategoryParser.class.getSimpleName());

    public static String getCategory(String key) {
        final String category = categoryMapping.get(key);
        if (BuildConfig.DEBUG) LOGGER.debug("{} -> {}", key, category);
        return category;
    }

    public static int getSort(String key){
        final Integer sort = sortMapping.get(key);
        if (BuildConfig.DEBUG) LOGGER.debug("{} -> {}", key, sort);
        return (sort == null) ? 99 : sort.intValue();
    }
}