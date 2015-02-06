package de.ironjan.mensaupb.stw.filters;

import java.util.*;

import de.ironjan.mensaupb.stw.*;

/**
 * Interface for filter classes
 */
interface Filter {
    /**
     * Filters the given menus. No guarantee on order is given
     *
     * @param menus the menus to be filtered
     * @return filtered clone
     */
    public List<RawMenu> filter(List<RawMenu> menus);

    /**
     * Filters the given menus
     *
     * @param menu the menu to be filtered
     * @return filtered clone
     */
    public RawMenu filter(RawMenu menu);
}
