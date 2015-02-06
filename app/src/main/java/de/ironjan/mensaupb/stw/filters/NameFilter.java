package de.ironjan.mensaupb.stw.filters;

import android.text.*;

import org.slf4j.*;

import de.ironjan.mensaupb.stw.*;

/**
 * Filters and corrects the names of the given menus
 */
class NameFilter extends FilterBase {
    Logger LOGGER = LoggerFactory.getLogger(NameFilter.class);

    @Override
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
            name = name.replaceAll("'", "''");
        }

        if (TextUtils.isEmpty(potentialName)) {
            return name;
        }
        return potentialName;
    }
}
