package de.najidev.mensaupb.stw;

import org.slf4j.*;

import java.util.*;

import de.najidev.mensaupb.*;

/**
 * Created by ljan on 31.01.14.
 */
public class StwCategoryParser {
    private static Hashtable<String, String> mapping = new Hashtable<String, String>();

    static {
        mapping.put("PUB Beilage Waage", "Beilage");
        mapping.put("PUB Salatbeilage Waage", "Beilage");
        mapping.put("Stamm Beilage klein 0,50€", "Beilage");
        mapping.put("Stamm Beilagensalat 0,50€", "Beilage");
        mapping.put("Stamm Gemüsebeil. 1 0,50€", "Beilage");
        mapping.put("Stamm Sättigungbeil 0,50€", "Beilage");
        mapping.put("Aktionsdessert 1,20€", "Dessert");
        mapping.put("PUB Dessert", "Dessert");
        mapping.put("Stamm Dessert 0,55€", "Dessert");
        mapping.put("Aktionsessen", "Essen");
        mapping.put("Grill Fisch", "Essen");
        mapping.put("Grill Fleisch", "Essen");
        mapping.put("Pastabuffet","Essen");
        mapping.put("PUB Fladenbrot","Essen");
        mapping.put("PUB Hauptkompononte","Essen");
        mapping.put("PUB Mittags-Angebot", "Essen");
        mapping.put("Stamm HK Essen 1 1,05€", "Essen");
        mapping.put("Stamm HK Essen 2 1,60€", "Essen");
        mapping.put("Stamm HK Essen 3 2,00€", "Essen");
        mapping.put("Stamm Waage 100g/0,80€", "Essen");
        mapping.put("WOK-Buffet Mensa", "Essen");
        mapping.put("Stamm Eintopf 1 f 1,80€", "Suppe");
        mapping.put("Stamm Tagessuppe 0,55€", "Suppe");

    }

    private static final Logger LOGGER = LoggerFactory.getLogger(StwCategoryParser.class.getSimpleName());

    public static String getCategory(String key) {
        final String category = mapping.get(key);
        if (BuildConfig.DEBUG) LOGGER.debug("{} -> {}", key, category);
        return category;
    }
}