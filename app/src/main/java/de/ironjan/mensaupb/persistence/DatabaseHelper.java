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

    private static final int DATABASE_VERSION = 9;

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
        if (LOGGER.isTraceEnabled()) LOGGER.trace("onCreate()");
        try {
            dropDatabase();
            TableUtils.createTable(connectionSource, RawMenu.class);
            if (LOGGER.isInfoEnabled()) LOGGER.info("Created database.");
        } catch (SQLException e) {
            LOGGER.error("Can't create database", e);
            throw new RuntimeException(e);
        }
        if (LOGGER.isDebugEnabled()) LOGGER.debug("onCreate() done");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, ConnectionSource connectionSource, int old, int newVersion) {
        if (LOGGER.isInfoEnabled()) LOGGER.info("onUpgrade()");
        if (old <= 8) {
            dropDatabase();
            onCreate(sqLiteDatabase, connectionSource);
        }
        requestSync();
        if (LOGGER.isDebugEnabled()) LOGGER.debug("onUpgrade() done");
    }

    private void dropDatabase() {
        mContext.deleteDatabase(DATABASE_NAME);
        if (LOGGER.isInfoEnabled()) LOGGER.info("Dropped database.");
    }

    private void requestSync() {
        // TODO implement

    }

}
