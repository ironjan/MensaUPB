package de.ironjan.mensaupb.persistence;

import android.content.*;
import android.database.sqlite.*;

import com.j256.ormlite.android.apptools.*;
import com.j256.ormlite.support.*;
import com.j256.ormlite.table.*;

import org.slf4j.*;

import java.sql.*;

import de.ironjan.mensaupb.library.stw.*;
import de.ironjan.mensaupb.sync.*;

/**
 * Class to manage the underlying database scheme.
 */
public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    private static final String DATABASE_NAME = "mensaupb.db";

    private static final int DATABASE_VERSION = 8;

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseHelper.class.getSimpleName());
    private final Context mContext;

    AccountCreator mAccountCreator;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.mContext = context;
        mAccountCreator = AccountCreator_.getInstance_(context);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource) {
        try {
            LOGGER.info("onCreate()");
            TableUtils.createTable(connectionSource, RawMenu.class);
            LOGGER.info("Created database.");
        } catch (SQLException e) {
            LOGGER.error("Can't create database", e);
            throw new RuntimeException(e);
        }
        LOGGER.info("onCreate() done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int old, int newVersion) {
        LOGGER.info("onUpgrade()");
        if (old <= 7) {
            mContext.deleteDatabase(DATABASE_NAME);
            onCreate(sqLiteDatabase, connectionSource);
        }
        requestSync();
        LOGGER.info("onUpgrade() done");
    }

    private void requestSync() {

    }

}
