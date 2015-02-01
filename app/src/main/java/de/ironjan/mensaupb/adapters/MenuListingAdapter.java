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

import java.util.*;

import de.ironjan.mensaupb.R;
import de.ironjan.mensaupb.stw.*;
import de.ironjan.mensaupb.sync.*;
import de.ironjan.mensaupb.views.*;
import se.emilsjolander.stickylistheaders.*;

/**
 * An adapter to load the list of menus for a MenuListingFragment.
 */
public class MenuListingAdapter extends SimpleCursorAdapter implements android.support.v4.app.LoaderManager.LoaderCallbacks<Cursor>, StickyListHeadersAdapter {
    public static final String[] ENGLISH_PROJECTION = {RawMenu.NAME_EN, RawMenu.STUDENTS_PRICE, RawMenu.PRICE_TYPE, RawMenu.BADGES, RawMenu.CATEGORY_EN, BaseColumns._ID};
    public static final int CATEGORY_INDEX = 4;
    private static final String[] GERMAN_PROJECTION = {RawMenu.NAME_GERMAN, RawMenu.STUDENTS_PRICE, RawMenu.PRICE_TYPE, RawMenu.BADGES, RawMenu.CATEGORY_DE, BaseColumns._ID};
    private String[] listProjection = GERMAN_PROJECTION;
    private static final String MENU_SELECTION = RawMenu.DATE + " = ? AND " + RawMenu.RESTAURANT + " LIKE ?";
    private static int[] BIND_TO = {R.id.textName, R.id.textPrice, R.id.textPricePer100g, R.id.textBadges};
    private final String mDate;
    private final String mLocation;

    public MenuListingAdapter(Context context, String argDate, String argLocation) {
        super(context, R.layout.view_menu_list_item,
                null, GERMAN_PROJECTION, BIND_TO, 0);
        this.mContext = context;
        this.mDate = argDate;
        this.mLocation = argLocation;
        boolean isEnglish = Locale.getDefault().getLanguage().startsWith(Locale.ENGLISH.toString());
        if (isEnglish) {
            listProjection = ENGLISH_PROJECTION;
        }
    }


    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = listProjection;

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
