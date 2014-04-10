package de.ironjan.mensaupb.sync;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.text.*;

import org.androidannotations.annotations.*;

import de.ironjan.mensaupb.persistence.*;
import de.ironjan.mensaupb.stw.*;

@EProvider
public class MenuContentProvider extends ContentProvider {

    private static final String MENUS_PATH = "menus";
    private static final String SINGLE_MENUS_PATH = MENUS_PATH + "/#";

    private static final Uri ROOT = Uri.parse("content://" + ProviderContract.AUTHORITY + "/");
    public static final Uri MENU_URI = Uri.withAppendedPath(ROOT, MENUS_PATH);
    private static final int MENUS_MATCH = 1;
    private static final int SINGLE_MENUS_MATCH = 2;

    private static UriMatcher sUriMatcher = new UriMatcher(0);

    static {
        sUriMatcher.addURI(ProviderContract.AUTHORITY, MENUS_PATH, MENUS_MATCH);
        sUriMatcher.addURI(ProviderContract.AUTHORITY, SINGLE_MENUS_PATH, SINGLE_MENUS_MATCH);
    }

    private DatabaseHelper getHelper() {
        DatabaseManager dbManager = new DatabaseManager();
        return dbManager.getHelper(getContext());
    }


    @Override
    public boolean onCreate() {
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // TODO check projection

        queryBuilder.setTables(Menu.TABLE);

        SQLiteDatabase db = getHelper().getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
            case MENUS_MATCH:
                if (TextUtils.isEmpty(sortOrder)) {
                    sortOrder = String.format(" %s ASC", Menu.SORT);
                }
                break;
            case SINGLE_MENUS_MATCH:
                selection = Menu.ID + " = ?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                break;
            default:
                throw new IllegalArgumentException("Uri unknown.");
        }
        Cursor cursor = queryBuilder.query(db, projection, selection,
                selectionArgs, null, null, sortOrder);

        cursor.setNotificationUri(getContext().getContentResolver(), uri);

        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
        // TODO check projection

        queryBuilder.setTables(Menu.TABLE);

        SQLiteDatabase db = getHelper().getWritableDatabase();

        long _id = db.insert(Menu.TABLE, null, contentValues);
        return Uri.withAppendedPath(MENU_URI, "/" + _id);
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        // TODO check projection

        SQLiteDatabase db = getHelper().getWritableDatabase();

        return db.update(Menu.TABLE, contentValues, s, strings);
    }
}
