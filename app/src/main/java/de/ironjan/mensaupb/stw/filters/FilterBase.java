package de.ironjan.mensaupb.stw.filters;


import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.stw.*;

/**
 * Base class that implements the list based filter method
 */
abstract class FilterBase implements Filter {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FilterBase.class);

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

}
