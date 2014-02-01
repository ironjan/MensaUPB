package de.najidev.mensaupb.stw;

import org.slf4j.*;

import java.io.*;
import java.util.*;

import de.najidev.mensaupb.*;

public class StwParser {

    private static final Logger LOGGER = LoggerFactory.getLogger(StwParser.class.getSimpleName());

    public static List<Menu> parseInputStream(InputStream inputStream) {
        if(BuildConfig.DEBUG) LOGGER.debug("parseInputStream({})", inputStream);

        List<Menu> parsedMenus = new ArrayList<Menu>();

        Scanner sc = new Scanner(inputStream, "windows-1252");
        sc.useDelimiter(";");

        sc.nextLine(); // skip description line

        while (sc.hasNextLine()) {
            String[] parts = prepareNextLine(sc);
            Menu menu = parseLine(parts);
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

    private static Menu parseLine(String[] parts) {
        if(BuildConfig.DEBUG) LOGGER.debug("parseLine({}) {}", parts, "");

        Menu menu = new Menu();
        menu.setLocation(parts[1]);
        menu.setDate(parts[2]);
        menu.setCategory(StwCategoryParser.getCategory(parts[3]));

        if ("a".equals(parts[5])) menu.setLocation("Abendmensa");

        menu.setNameGerman(parts[6]);
        menu.setNameEnglish(parts[7]);
        menu.setAllergenes(parts[8]);

        if(BuildConfig.DEBUG) LOGGER.debug("parseLine({}) -> {}", parts, menu);
        return menu;
    }

}