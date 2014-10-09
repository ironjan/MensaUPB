package de.ironjan.mensaupb.adapters;


import android.content.*;
import android.database.*;
import android.os.*;
import android.provider.*;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.library.stw.*;
import de.ironjan.mensaupb.sync.*;

/**
 * An adapter to load the list of menus for a MenuListingFragment.
 */
public class MenuListingAdapter extends SimpleCursorAdapter implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor> {
    public static final String[] LIST_PROJECTION = {RawMenu.NAME_GERMAN, RawMenu.CATEGORY, RawMenu.STUDENTS_PRICE, RawMenu.PRICE_TYPE, BaseColumns._ID};
    private static final String SELECTION = RawMenu.DATE + " = ? AND " + RawMenu.RESTAURANT + " = ?";
    static int[] BIND_TO = {R.id.textName, R.id.textCategory, R.id.textPrice, R.id.textPricePer100g};

    private final String mDate;
    private final String mLocation;

    public MenuListingAdapter(Context context, String argDate, String argLocation) {
        super(context, R.layout.view_menu_list_item,
                null, LIST_PROJECTION, BIND_TO, 0);
        this.mContext = context;
        this.mDate = argDate;
        this.mLocation = argLocation;
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = LIST_PROJECTION;

        final String[] selectionArgs = {mDate, mLocation};

        return new CursorLoader(mContext,
                MenuContentProvider.MENU_URI, projection, SELECTION, selectionArgs, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        swapCursor(cursor);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {
        swapCursor(null);
    }
}
