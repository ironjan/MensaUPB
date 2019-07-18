package de.ironjan.mensaupb.menus_ui;

import de.ironjan.mensaupb.api.model.Menu;

/**
 * Interface to handle navigation from a menu listing
 */
public interface MenusNavigationCallback {

    void showMenu(Menu m);
}
