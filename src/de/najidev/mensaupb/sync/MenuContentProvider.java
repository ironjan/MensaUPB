package de.najidev.mensaupb.sync;

import android.content.*;
import android.database.*;
import android.net.*;

import com.j256.ormlite.android.*;
import com.j256.ormlite.dao.*;
import com.j256.ormlite.stmt.*;

import org.androidannotations.annotations.*;
import org.slf4j.*;

import java.sql.*;
import java.sql.SQLException;
import java.util.*;

import de.najidev.mensaupb.persistence.*;
import de.najidev.mensaupb.rest.*;

/**
 * Created by ljan on 10.01.14.
 */
@EProvider
public class MenuContentProvider extends ContentProvider {

    @Override
    public boolean onCreate() {
        return false;
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
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
