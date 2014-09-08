package de.ironjan.mensaupb.sync;

import android.annotation.*;
import android.content.*;
import android.database.*;
import android.database.sqlite.*;
import android.net.*;
import android.provider.*;
import android.text.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import java.util.*;

import de.ironjan.mensaupb.*;
import de.ironjan.mensaupb.library.stw.*;
import de.ironjan.mensaupb.library.stw.deprecated.*;
import de.ironjan.mensaupb.persistence.*;

/**
 * A content provider for the downloaded menu data.
 */
@SuppressLint("Registered")
@EProvider
public class MenuContentProvider extends ContentProvider {

    private static final String MENUS_PATH = "menus";
    private static final String SINGLE_MENUS_PATH = MENUS_PATH + "/#";

    private static final Uri ROOT = Uri.parse("content://" + ProviderContract.AUTHORITY + "/");
    public static final Uri MENU_URI = Uri.withAppendedPath(ROOT, MENUS_PATH);
    private static final int MENUS_MATCH = 1;
    private static final int SINGLE_MENUS_MATCH = 2;

    private static UriMatcher sUriMatcher = new UriMatcher(0);

    private final Logger LOGGER = LoggerFactory.getLogger(MenuContentProvider.class.getSimpleName());

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
//        TODO checkProjection(uri, projection);

        queryBuilder.setTables(RawMenu.TABLE);

        SQLiteDatabase db = getHelper().getReadableDatabase();

        switch (sUriMatcher.match(uri)) {
            case MENUS_MATCH:
                if (TextUtils.isEmpty(sortOrder)) {
                    // TODO add sort orter
                }
                break;
            case SINGLE_MENUS_MATCH:
                selection = BaseColumns._ID + " = ?";
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

    private void checkProjection(Uri uri, String[] projection) {
        final String[] allowedColumns = buildAllowedColumns(uri);
        checkAllowedColumns(projection, allowedColumns);
    }

    private void checkAllowedColumns(String[] projection, String[] allowedColumns) {
        HashSet<String> allowedColumnsSet = new HashSet<String>(allowedColumns.length);
        for (String column : allowedColumns) {
            allowedColumnsSet.add(column);
        }

        for (String requestedColumn : projection) {
            boolean columnIsNotAllowed = !allowedColumnsSet.contains(requestedColumn);
            if (columnIsNotAllowed) {
                throw new IllegalArgumentException(requestedColumn + " is not an allowedColumnsSet column.");
            }
        }
    }

    private String[] buildAllowedColumns(Uri uri) {
        final String[] allowedColumns;
        switch (sUriMatcher.match(uri)) {
            case MENUS_MATCH:
            case SINGLE_MENUS_MATCH:
                allowedColumns = Menu.COLUMNS;
                break;
            default:
                throw new IllegalArgumentException("Uri unknown.");
        }
        return allowedColumns;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        switch (sUriMatcher.match(uri)) {
            case MENUS_MATCH:

                SQLiteQueryBuilder queryBuilder = new SQLiteQueryBuilder();
                // TODO check projection

                queryBuilder.setTables(RawMenu.TABLE);

                SQLiteDatabase db = getHelper().getWritableDatabase();

                long _id = db.insert(RawMenu.TABLE, null, contentValues);
                return Uri.withAppendedPath(MENU_URI, "/" + _id);
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        switch (sUriMatcher.match(uri)) {
            case MENUS_MATCH:
                final SQLiteDatabase db = getHelper().getWritableDatabase();
                final int delete = db.delete(RawMenu.TABLE, where, whereArgs);
                if (BuildConfig.DEBUG)
                    LOGGER.info("Deleted {} menus via delete({},{},{})", new Object[]{delete, uri, where, whereArgs});
                return delete;
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        switch (sUriMatcher.match(uri)) {
            case MENUS_MATCH:
                SQLiteDatabase db = getHelper().getWritableDatabase();
                // TODO check projection
                return db.update(RawMenu.TABLE, contentValues, s, strings);
            default:
                throw new IllegalArgumentException("Unknown Uri");
        }


    }
}
