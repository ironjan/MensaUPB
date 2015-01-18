package de.ironjan.mensaupb.adapters;


import android.content.*;
import android.database.*;
import android.net.*;
import android.os.*;
import android.provider.*;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.widget.*;
import android.view.*;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.library.stw.*;
import de.ironjan.mensaupb.sync.*;
import de.ironjan.mensaupb.views.*;
import se.emilsjolander.stickylistheaders.*;

/**
 * An adapter to load the list of menus for a MenuListingFragment.
 */
public class MenuListingAdapter extends SimpleCursorAdapter implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, StickyListHeadersAdapter {
    public static final String[] LIST_PROJECTION = {RawMenu.NAME_GERMAN, RawMenu.STUDENTS_PRICE, RawMenu.PRICE_TYPE, RawMenu.BADGES, RawMenu.RESTAURANT, RawMenu.CATEGORY, BaseColumns._ID};
    public static final int NAME_GERMAN_INDEX = 0,
            STUDENTS_PRICE_INDEX = 1,
            PRICE_TYPE_INDEX = 2,
            BADGES_INDEX = 3,
            RESTAURANT_INDEX = 4,
            CATEGORY_INDEX = 5,
            ID_INDEX = 6;
    private static final String MENU_SELECTION = RawMenu.DATE + " = ? AND " + RawMenu.RESTAURANT + " LIKE ?";
    static int[] BIND_TO = {R.id.textName, R.id.textPrice, R.id.textPricePer100g, R.id.textBadges, R.id.textRestaurant};

    private final String mDate;
    private final String mLocation;
    private final String mensae;

    public MenuListingAdapter(Context context, String argDate, String argLocation) {
        super(context, R.layout.view_menu_list_item,
                null, LIST_PROJECTION, BIND_TO, 0);
        this.mContext = context;
        this.mDate = argDate;
        this.mLocation = argLocation;

        mensae = context.getResources().getString(R.string.mensae);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = LIST_PROJECTION;

        final String[] selectionArgs = {mDate, mLocation};

        final Uri menuUri;
        menuUri = MenuContentProvider.MENU_URI;
        return new CursorLoader(mContext,
                menuUri, projection, MENU_SELECTION, selectionArgs, null);
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
