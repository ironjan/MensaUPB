package de.ironjan.mensaupb.library.stw.filters;

import android.os.*;
import android.text.*;

import com.j256.ormlite.logger.*;

import org.androidannotations.annotations.*;

import java.util.*;

import de.ironjan.mensaupb.library.stw.*;

/**
 * Filters and corrects the names of the given menus
 */
public class NameFilter implements Filter {
    Logger LOGGER = LoggerFactory.getLogger(NameFilter.class);

    @Override
    public List<RawMenu> filter(List<RawMenu> menus) {
        LOGGER.debug("filter(list)");
        List<RawMenu> cleanedMenus = new ArrayList<>(menus.size());
        for (RawMenu menu : menus) {
            RawMenu cleanedMenu = cleanNames(menu);
            cleanedMenus.add(cleanedMenu);
        }
        LOGGER.debug("filter(list) done");
        return cleanedMenus;
    }

    private RawMenu cleanNames(RawMenu menu) {
        LOGGER.debug("cleanNames({}) {de: {}, en: {}}", menu, menu.getName_de(), menu.getName_en());
        RawMenu copy = menu.copy();
        copy.setName_de(cleanName(copy.getName_de()));
        copy.setName_en(cleanName(copy.getName_en()));
        LOGGER.debug("cleanNames({}) done {de: {}, en: {}}", copy, copy.getName_de(), copy.getName_en());
        return copy;
    }


    private String cleanName(String name) {
        String potentialName = null;
        if (!TextUtils.isEmpty(name) && name.contains("|")) {
            String[] split = name.split("\\|");
            potentialName = split[0].trim();
        }

        if (TextUtils.isEmpty(potentialName)) {
            return name;
        }
        return potentialName;
    }
}
