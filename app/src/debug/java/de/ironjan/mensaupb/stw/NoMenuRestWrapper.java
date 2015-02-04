package de.ironjan.mensaupb.stw;

import org.androidannotations.annotations.*;

/**
 * Mock that always returns no menus.
 */
@EBean
public class NoMenuRestWrapper implements StwRest {
    @Override
    public RawMenu[] getMenus(String restaurant, String date) {
        return new RawMenu[0];
    }
}
