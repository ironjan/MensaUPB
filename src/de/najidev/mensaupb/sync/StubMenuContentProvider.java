package de.najidev.mensaupb.sync;

import android.content.*;
import android.database.*;
import android.net.*;

/**
 * Created by ljan on 10.01.14.
 */
public class StubMenuContentProvider extends ContentProvider {
    public static final int CODE_ROOT = 0;
    private static final UriMatcher sUriMatcher = new UriMatcher(CODE_ROOT);

    public static final int CODE_RESTAURANTS = 1;

    public static final int CODE_SINGLE_RESTAURANT = 2;

    public static final int CODE_RESTAURANT_MENUS = 3;

    static {
        sUriMatcher.addURI("de.najidev.mensaupb.provider", "restaurants", CODE_RESTAURANTS);
        sUriMatcher.addURI("de.najidev.mensaupb.provider", "restaurants/#", CODE_SINGLE_RESTAURANT);
        sUriMatcher.addURI("de.najidev.mensaupb.provider", "restaurants/#/menus", CODE_RESTAURANT_MENUS);
    }

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings2, String s2) {
        switch (sUriMatcher.match(uri)) {
            case CODE_RESTAURANTS:
                // TODO fetch restaurants
            case CODE_SINGLE_RESTAURANT:
                // TODO fetch restaurant
            case CODE_RESTAURANT_MENUS:
                // TODO fetch restaurant menus
            case CODE_ROOT:
            default:
                throw new IllegalArgumentException("Unknown content uri");
        }
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}
