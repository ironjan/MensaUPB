package de.ironjan.mensaupb.menus_ui;

/**
 * Interface to handle navigation from a menu listing
 */
public interface MenusNavigationCallback {
    /**
     * Navigated to the menu with the given id
     *
     * @param key the menu's id
     */
    void showMenu(String key);
}
