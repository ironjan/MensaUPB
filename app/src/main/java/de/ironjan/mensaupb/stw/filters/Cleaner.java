package de.ironjan.mensaupb.stw.filters;

import de.ironjan.mensaupb.stw.rest_api.StwMenu;

/**
 * Interface for cleaner classes
 */
interface Cleaner {

    /**
     * Cleans the given menu
     *
     * @param menu the menu to be cleaned
     * @return cleaned clone
     */
    StwMenu clean(StwMenu menu);
}
