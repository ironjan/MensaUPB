package de.najidev.mensaupb.stw;

import org.slf4j.*;

import java.util.*;

import de.najidev.mensaupb.*;

public class Allergene {
    private static final Logger LOGGER = LoggerFactory.getLogger(Allergene.class.getSimpleName());

    private static final HashMap<String, String> mapping = new HashMap<String, String>();
    private static final List<String> allowed = new ArrayList<String>();

    static {
        mapping.put("1"
                , "Farbstoff");
        mapping.put("2"
                , "konserviert");
        mapping.put("3"
                , "Antioxidationsmittel");
        mapping.put("4"
                , "Geschmacksverstärker");
        mapping.put("5"
                , "Phosphat");
        mapping.put("6"
                , "geschwefelt");
        mapping.put("7"
                , "gewachst");
        mapping.put("8"
                , "geschwärzt");
        mapping.put("9"
                , "Süßungsmittel");
        mapping.put("10"
                , "enthält eine Phenylalaninquelle");
        mapping.put("11"
                , "Taurin");
        mapping.put("12"
                , "Nitratpökelsalz");
        mapping.put("13"
                , "koffeinhaltig");
        mapping.put("14"
                , "chininhaltig");
        mapping.put("15"
                , "Milcheiweiß");
        mapping.put("A2"
                , "Krebstiere und Krebstiererzeugnisse");
        mapping.put("A3"
                , "Eier und Eiererzeugnisse");
        mapping.put("A4"
                , "Fisch");
        mapping.put("A6"
                , "Soja und Sojaerzeugnisse");
        mapping.put("A7"
                , "Milch und Milcherzeugnisse(einschließlich Lactose)");
        mapping.put("A8"
                , "Schalenfrüchte sowie daraus hergest. Erzeugnisse");
        mapping.put("A9"
                , "Sellerie und Sellerieerzeugnisse");
        mapping.put("A10"
                , "Senf und Senferzeugnisse");
        mapping.put("A11"
                , "Sesamsamen und Sesamsamenerzeugnisse");
        mapping.put("A12"
                , "Schwefeldioxid und Sulfite");
        mapping.put("A14"
                , "Weichtiere und Weichtiererzeugnisse");
        mapping.put("A13"
                , "Lupinen und Lupinenerzeugnisse");


        allowed.addAll(mapping.keySet());
    }

    public static final String ALLEGERNE_DELIMITER = ", ";

    public String descriptionFromKey(String key) {
        final String description = mapping.get(key);
        return description;
    }

    public static String filterAllergenes(String s) {
        if (BuildConfig.DEBUG) LOGGER.debug("filterAllergenes({})", s);


        StringBuffer filtered = new StringBuffer();

        Scanner sc = new Scanner(s);
        sc.useDelimiter(ALLEGERNE_DELIMITER);

        String next;
        if (sc.hasNext()) {
            next = sc.next();
            if (allowed.contains(next)) {
                filtered.append(next);
            }
        }
        while (sc.hasNext()) {
            next = sc.next();
            if (allowed.contains(next)) {
                filtered.append(", ").append(next);
            }
        }
        final String result = filtered.toString();

        if (BuildConfig.DEBUG) LOGGER.debug("filterAllergenes({}) -> {}", s, result);
        return result;
    }
}
