package de.ironjan.mensaupb.stw.filters;


import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.List;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Base class that implements the list based filter method
 */
abstract class FilterBase implements Filter {
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger(FilterBase.class);

    @Override
    public List<StwMenu> filter(List<StwMenu> menus) {
        List<StwMenu> cleanedMenus = new ArrayList<>(menus.size());
        for (StwMenu menu : menus) {
            StwMenu cleanedMenu = filter(menu);
            cleanedMenus.add(cleanedMenu);
        }
        return cleanedMenus;
    }

}
