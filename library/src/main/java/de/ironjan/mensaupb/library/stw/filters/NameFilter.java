package de.ironjan.mensaupb.library.stw.filters;

import android.text.*;

import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.library.stw.*;

/**
 * Filters and corrects the names of the given menus
 */
class NameFilter implements Filter {
    Logger LOGGER = LoggerFactory.getLogger(NameFilter.class);

    @Override
    public List<RawMenu> filter(List<RawMenu> menus) {
        LOGGER.debug("filter(list)");
        List<RawMenu> cleanedMenus = new ArrayList<>(menus.size());
        for (RawMenu menu : menus) {
            RawMenu cleanedMenu = filter(menu);
            cleanedMenus.add(cleanedMenu);
        }
        LOGGER.debug("filter(list) done");
        return cleanedMenus;
    }

    public RawMenu filter(RawMenu menu) {
        LOGGER.debug("filter({}) {de: {}, en: {}}", menu, menu.getName_de(), menu.getName_en());
        RawMenu copy = menu.copy();
        copy.setName_de(cleanName(copy.getName_de()));
        copy.setName_en(cleanName(copy.getName_en()));
        LOGGER.debug("filter({}) done {de: {}, en: {}}", menu, copy.getName_de(), copy.getName_en());
        return copy;
    }


    private String cleanName(String name) {
        if (name == null) {
            return ""; // no cleaning necessary, but we replace null with empty string..
        }
        String potentialName = null;
        if (!TextUtils.isEmpty(name) && name.contains("|")) {
            String[] split = name.split("\\|");
            potentialName = split[0].trim();
        }

        if (name.contains("'")) {
            // replace single quotes with escaped single quotes -> sqlite fix
            name.replaceAll("'", "''");
        }

        if (TextUtils.isEmpty(potentialName)) {
            return name;
        }
        return potentialName;
    }
}
