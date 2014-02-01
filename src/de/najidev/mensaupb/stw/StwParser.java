package de.najidev.mensaupb.stw;

import android.content.*;

import org.slf4j.*;

import java.io.*;
import java.util.*;

import de.najidev.mensaupb.*;

public class StwParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(StwParser.class.getSimpleName());

    public static List<ContentValues> parseInputStream(InputStream inputStream) {
        if(BuildConfig.DEBUG) LOGGER.debug("parseInputStream({})", inputStream);

        List<ContentValues> parsedMenus = new ArrayList<ContentValues>();

        Scanner sc = new Scanner(inputStream, "windows-1252");
        sc.useDelimiter(";");

        sc.nextLine(); // skip description line

        while (sc.hasNextLine()) {
            String[] parts = prepareNextLine(sc);
            ContentValues menu = parseLine(parts);
            parsedMenus.add(menu);
        }

        if(BuildConfig.DEBUG) LOGGER.debug("parseInputStream({}) done", inputStream);
        return parsedMenus;
    }

    private static String[] prepareNextLine(Scanner sc) {
        if(BuildConfig.DEBUG) LOGGER.debug("prepareNextLine(..)");

        String line = sc.nextLine();

        if(BuildConfig.DEBUG) LOGGER.debug("nextLine -> ({})", line);

        line = line.replaceAll("\"", "");

        if(BuildConfig.DEBUG) LOGGER.debug("prepareNextLine(..) done");
        return line.split(";");
    }

    private static ContentValues parseLine(String[] parts) {
        if(BuildConfig.DEBUG) LOGGER.debug("parseLine({}) {}", parts, "");

        ContentValues cv = new ContentValues();
        cv.put(Menu.LOCATION, parts[1]);
        cv.put(Menu.DATE, parts[2]);
        cv.put(Menu.CATEGORY, StwCategoryParser.getCategory(parts[3]));
        cv.put(Menu.SORT, StwCategoryParser.getSort(parts[3]));

        if ("a".equals(parts[5])) cv.put(Menu.LOCATION, "Abendmensa");

        cv.put(Menu.NAME_GERMAN, parts[6]);
        cv.put(Menu.NAME_ENGLISH, parts[7]);
        cv.put(Menu.ALLERGENES, Allergene.filterAllergenes(parts[8]));


        if (BuildConfig.DEBUG) LOGGER.debug("parseLine({}) -> {}", parts, cv);
        return cv;
    }

}