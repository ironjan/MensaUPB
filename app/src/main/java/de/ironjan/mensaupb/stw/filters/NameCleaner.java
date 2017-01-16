package de.ironjan.mensaupb.stw.filters;

import android.text.TextUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Filters and corrects the names of the given menus
 */
class NameCleaner implements Cleaner {
    Logger LOGGER = LoggerFactory.getLogger(NameCleaner.class);

    @Override
    public StwMenu clean(StwMenu menu) {
        StwMenu copy = menu.copy();
        copy.setName_de(cleanName(copy.getName_de()));
        copy.setName_en(cleanName(copy.getName_en()));
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
            name = name.replaceAll("'", "''");
        }

        if (TextUtils.isEmpty(potentialName)) {
            return name;
        }
        return potentialName;
    }
}
