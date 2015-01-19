package de.ironjan.mensaupb.library.stw.filters;

import java.util.*;

import de.ironjan.mensaupb.library.stw.*;

/**
 * Interface for filter classes
 */
interface Filter {
    /**
     * Filters the given menus
     *
     * @param menus the menus to be filtered
     * @return filtered clone
     */
    public List<RawMenu> filter(List<RawMenu> menus);
}
