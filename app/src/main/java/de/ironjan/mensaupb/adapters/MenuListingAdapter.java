package de.ironjan.mensaupb.adapters;


import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.BaseColumns;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.SimpleCursorAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.library.stw.RawMenu;
import de.ironjan.mensaupb.sync.MenuContentProvider;
import de.ironjan.mensaupb.views.*;
import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * An adapter to load the list of menus for a MenuListingFragment.
 */
public class MenuListingAdapter extends SimpleCursorAdapter implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, StickyListHeadersAdapter {
    public static final String[] LIST_PROJECTION = {RawMenu.NAME_GERMAN, RawMenu.STUDENTS_PRICE, RawMenu.PRICE_TYPE, RawMenu.BADGES, RawMenu.CATEGORY, BaseColumns._ID};
    private static final String SELECTION = RawMenu.DATE + " = ? AND " + RawMenu.RESTAURANT + " = ?";
    public static final int CATEGORY_INDEX = 4;
    static int[] BIND_TO = {R.id.textName, R.id.textPrice, R.id.textPricePer100g, R.id.textBadges};

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

    @Override
    public View getHeaderView(int pos, View convertView, ViewGroup parent) {
        String categoryOfPosition = getCategoryOfPosition(pos);
        MenuListingHeaderView view;
        if (convertView == null) {
            view = MenuListingHeaderView_.build(mContext);
        } else {
            view = (MenuListingHeaderView) convertView;
        }
        view.setText(categoryOfPosition);

        return view;
    }

    @Override
    public long getHeaderId(int pos) {
        String category = getCategoryOfPosition(pos);
        return category.hashCode();
    }

    private String getCategoryOfPosition(int pos) {
        Cursor cursor = getCursor();
        cursor.moveToPosition(pos);
        return cursor.getString(CATEGORY_INDEX);
    }
}
