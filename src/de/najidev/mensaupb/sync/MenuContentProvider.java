package de.najidev.mensaupb.sync;

import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;

import org.androidannotations.annotations.*;

import de.najidev.mensaupb.persistence.*;
import de.najidev.mensaupb.stw.*;

/**
 * Created by ljan on 10.01.14.
 */
@EProvider
public class MenuContentProvider extends ContentProvider {

    private static final String MENUS_PATH = "menus";
    private static final Uri ROOT = Uri.parse("content://" + ProviderContract.AUTHORITY + "/");
    public static final Uri MENU_URI = ROOT.withAppendedPath(ROOT, MENUS_PATH);
    private static final int MENUS_MATCH = 1;

    private static UriMatcher sUriMatcher = new UriMatcher(0);

    static {
        sUriMatcher.addURI(ProviderContract.AUTHORITY, MENUS_PATH, MENUS_MATCH);
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
        return 0;
    }
}
