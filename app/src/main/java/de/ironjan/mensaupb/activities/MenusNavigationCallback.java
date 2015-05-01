package de.ironjan.mensaupb.activities;

/**
 * Interface to handle navigation from a menu listing
 */
public interface MenusNavigationCallback {
    /**
     * Navigated to the menu with the given id
     *
     * @param _id the menu's id
     */
    void showMenu(long _id);
}
